package org.library.controller.borrow;

import lombok.RequiredArgsConstructor;
import org.library.model.Borrow;
import org.library.service.book.BookCRUDService;
import org.library.service.BorrowService;
import org.library.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;
    private final BookCRUDService bookCRUDService;
    private final ClientService clientService;

    private static final String VIEW_BORROWS = "borrows";
    private static final String VIEW_FORM = "borrow-form";
    private static final String REDIRECT_BORROWS = "redirect:/borrows";

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
        return VIEW_BORROWS;
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("borrow", new Borrow());
        model.addAttribute("books", bookCRUDService.getAllBooks());
        model.addAttribute("clients", clientService.getAllClients());
        return VIEW_FORM;
    }

    @PostMapping
    public String addBorrow(@ModelAttribute Borrow borrow) {
        validateBorrowEntities(borrow);
        borrowService.saveBorrow(borrow);
        return REDIRECT_BORROWS;
    }

    private void validateBorrowEntities(@ModelAttribute Borrow borrow) {
        if (borrow.getClient() == null || !clientService.getClientById(borrow.getClient().getId()).isPresent()) {
            throw new IllegalArgumentException("Клиент не найден");
        }
        if (borrow.getBook() == null || !bookCRUDService.getBookById(borrow.getBook().getId()).isPresent()) {
            throw new IllegalArgumentException("Книга не найдена");
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Borrow borrow = borrowService.getBorrowById(id)
                .orElseThrow(() -> new RuntimeException("Borrow not found"));
        model.addAttribute("borrow", borrow);
        model.addAttribute("books", bookCRUDService.getAllBooks());
        model.addAttribute("clients", clientService.getAllClients());
        return VIEW_FORM;
    }

    @PostMapping("/update/{id}")
    public String updateBorrow(@PathVariable Long id, @ModelAttribute Borrow borrow) {
        validateBorrowEntities(borrow);
        borrowService.updateBorrow(id, borrow);
        return REDIRECT_BORROWS;
    }

    @GetMapping("/delete/{id}")
    public String deleteBorrow(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
        return REDIRECT_BORROWS;
    }


}