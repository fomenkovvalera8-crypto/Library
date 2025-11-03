package org.library.controller;

import org.library.model.Borrow;
import org.library.service.BookService;
import org.library.service.BorrowService;
import org.library.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/borrows")
public class BorrowController {

    private final BorrowService borrowService;
    private final BookService bookService;
    private final ClientService clientService;

    public BorrowController(BorrowService borrowService, BookService bookService, ClientService clientService) {
        this.borrowService = borrowService;
        this.bookService = bookService;
        this.clientService = clientService;
    }

    @GetMapping
    public String listBorrows(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {

        List<Borrow> borrowsPage = borrowService.getBorrowsPage(page, size);
        long total = borrowService.countBorrows();

        model.addAttribute("borrows", borrowsPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("hasMore", (page + 1) * size < total);
        return "borrows";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("borrow", new Borrow());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("clients", clientService.getAllClients());
        return "borrow-form";
    }

    @PostMapping
    public String addBorrow(@ModelAttribute Borrow borrow) {
        if (borrow.getClient() == null || !clientService.getClientById(borrow.getClient().getId()).isPresent()) {
            throw new IllegalArgumentException("Клиент не найден");
        }
        if (borrow.getBook() == null || !bookService.getBookById(borrow.getBook().getId()).isPresent()) {
            throw new IllegalArgumentException("Книга не найдена");
        }
        borrowService.saveBorrow(borrow);
        return "redirect:/borrows";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Borrow borrow = borrowService.getBorrowById(id)
                .orElseThrow(() -> new RuntimeException("Borrow not found"));
        model.addAttribute("borrow", borrow);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("clients", clientService.getAllClients());
        return "borrow-form";
    }

    @PostMapping("/update/{id}")
    public String updateBorrow(@PathVariable Long id, @ModelAttribute Borrow borrow) {
        if (borrow.getClient() == null || !clientService.getClientById(borrow.getClient().getId()).isPresent()) {
            throw new IllegalArgumentException("Клиент не найден");
        }
        if (borrow.getBook() == null || !bookService.getBookById(borrow.getBook().getId()).isPresent()) {
            throw new IllegalArgumentException("Книга не найдена");
        }
        borrowService.updateBorrow(id, borrow);
        return "redirect:/borrows";
    }

    @GetMapping("/delete/{id}")
    public String deleteBorrow(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
        return "redirect:/borrows";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteBorrowAjax(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
    }

    @GetMapping("/page")
    @ResponseBody
    public List<Borrow> getBorrowsPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return borrowService.getBorrowsPage(page, size);
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Borrow> searchBorrows(@RequestParam("q") String query,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        if (query == null || query.trim().isEmpty()) {
            return borrowService.getBorrowsPage(page, size);
        }
        return borrowService.searchBorrows(query, page, size);
    }
}