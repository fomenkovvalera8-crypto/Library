package org.library.controller.book;

import lombok.RequiredArgsConstructor;
import org.library.dto.BookPageDTO;
import org.library.model.Book;
import org.library.service.book.BookCRUDService;
import org.library.service.book.BookPaginationService;
import org.library.service.book.BookSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookCRUDService bookCRUDService;
    private final BookSearchService bookSearchService;
    private final BookPaginationService bookPaginationService;

    /** * Список книг для указанной страницы
     * @param page номер страницы для отображения
     * @param size количество элементов на странице
     * @return список книг на указанной странице
     */
    @GetMapping("/page")
    public BookPageDTO getBooksPage(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return bookPaginationService.getBooksPage(page, size);
    }

    /**
     * Поиск книг через строку поиска на странице
     * @param query поисковая строка, передаваемая через параметр запроса
     * @param page номер страницы для отображения * @param size количество элементов на странице
     * @return список книг, которые удовлетворяют условию поиска - query,
     *         ограниченный указанной страницей - page и
     *         размером страницы - size
     */
    @GetMapping("/search")
    public BookPageDTO searchBooks(@RequestParam("q") String query,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        List<Book> books = bookSearchService.searchBooks(query, page, size);
        long total = bookSearchService.countSearchResults(query);
        boolean hasMore = (page + 1) * size < total;

        return new BookPageDTO(books, hasMore, page, size);
    }

    /**
     * Возвращает общее количество книг, соответствующих строке поиска
     * @param query поисковая строка, передаваемая через параметр запроса
     * @return количество книг, соответствующих поисковому запросу
     */
    @GetMapping("/search/count")
    public long countSearchResults(@RequestParam("q") String query) {
        return bookSearchService.countSearchResults(query);
    }

    /**
     * Удаление книги
     * @param id идентификатор удаляемой книги, извлекаемый из URL с помощью аннотации
     */
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookCRUDService.deleteBook(id);
    }
}
