package org.library.controller;


import org.library.model.Book;
import org.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        List<Book> booksPage = bookService.getBooksPage(page, size);
        long total = bookService.countBooks();

        model.addAttribute("books", booksPage);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("hasMore", (page + 1) * size < total); // true, если есть следующая страница
        return "books";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @PostMapping
    public String addBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        return "book-form";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteBookAjax(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
    @GetMapping("/search")
    @ResponseBody
    public List<Book> searchBooks(@RequestParam("q") String query,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return bookService.searchByTitleOrAuthorPaged(query, page, size);
    }

    @GetMapping("/page")
    @ResponseBody
    public List<Book> getBooksPage(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return bookService.getBooksPage(page, size);
    }
    @GetMapping("/search/count")
    @ResponseBody
    public long countSearchResults(@RequestParam("q") String query) {
        return bookService.countSearchResults(query);
    }
}