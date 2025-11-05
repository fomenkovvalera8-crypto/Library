package org.library.controller.book;

import lombok.RequiredArgsConstructor;
import org.library.dto.BookPageDTO;
import org.library.exception.BookNotFoundException;
import org.library.model.Book;
import org.library.service.book.BookPaginationService;
import org.library.service.book.BookCRUDService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookCRUDService bookCRUDService;
    private final BookPaginationService bookPaginationService;

    private static final String BOOKS = "books";
    private static final String BOOK_FORM = "book-form";
    private static final String REDIRECT_BOOKS = "redirect:/books";

    /**
     * Показывает страницу со списком книг
     * @param model объект Model, в который кладутся атрибуты для отображения в books.html
     * @param page  индекс страницы. Если параметр не передан — по умолчанию 0
     * @param size  количество элементов на страницу. По умолчанию — DEFAULT_PAGE_SIZE
     * @return имя шаблона для отображения — "books"
     */
    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        BookPageDTO bookPage = bookPaginationService.getBooksPage(page, size);
        model.addAttribute("books", bookPage.getBooks());
        model.addAttribute("hasMore", bookPage.isHasMore());
        model.addAttribute("page", bookPage.getPage());
        model.addAttribute("size", bookPage.getSize());
        return BOOKS;
    }

    /**
     * Показывает страницу дял добавления новой книги
     * @param model объект Model, в который кладутся атрибуты для отображения в books-form.html
     * @return имя шаблона для отображения — "books-form"
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return BOOK_FORM;
    }

    /**
     * Показывает страницу для редактирования книги
     * @param id идентификатор редактируемой книги, извлекаемый из URL с помощью аннотации
     * @param model объект Model, в который кладутся атрибуты для отображения в books-form.html
     * @return имя шаблона для отображения — "books-form"
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookCRUDService.getBookById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        model.addAttribute("book", book);
        return BOOK_FORM;
    }

    /**
     * Добавление новой книги
     * @param book объект книги, заполненный данными из HTML-формы book-form.html
     * @param bindingResult объект с результатом валидации
     * @param model объект модели для передачи ошибок на форму
     * @return если форма содержит ошибки — возвращается шаблон для добавления книги для повторного ввода данных;
     *         если ошибок нет — выполняется сохранение книги
     */
    @PostMapping
    public String addBook(@ModelAttribute @Valid Book book, BindingResult bindingResult, Model model) {
        return saveBook(book, bindingResult, model,null);
    }

    /**
     * Обновление данных книги
     * @param id идентификатор обновляемой книги, извлекаемый из URL с помощью аннотации
     * @param book объект книги, заполненный данными из HTML-формы book-form.html
     * @param bindingResult объект с результатом валидации
     * @param model объект модели для передачи ошибок на форму
     * @return если форма содержит ошибки — возвращается шаблон для добавления книги для повторного ввода данных;
     *         если ошибок нет — выполняется сохранение книги
     */
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute @Valid Book book, BindingResult bindingResult, Model model) {
        return saveBook(book, bindingResult, model, id);
    }

    /**
     * Удаление книги
     * @param id идентификатор удаляемой книги, извлекаемый из URL с помощью аннотации
     * @return перенаправление на страницу с книгами после удаления
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookCRUDService.deleteBook(id);
        return REDIRECT_BOOKS;
    }

    /**
     * Сохранение книги
     * @param id идентификатор обновляемой книги, извлекаемый из URL с помощью аннотации
     * @param book объект книги, заполненный данными из HTML-формы book-form.html
     * @param bindingResult объект с результатом валидации
     * @param model объект модели для передачи ошибок на форму
     * @return если форма содержит ошибки — возвращается шаблон для добавления книги для повторного ввода данных;
     *         если ошибок нет — выполняется сохранение книги
     */
    private String saveBook(Book book, BindingResult bindingResult, Model model, Long id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            return BOOK_FORM;
        }
        bookCRUDService.saveOrUpdate(book, id);
        return REDIRECT_BOOKS;
    }
}