package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для поиска книг с поддержкой пагинации и сортировки
 */
@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;

    /**
     * Поиск книг с пагинацией
     * @param query поисковая строка
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return список книг, соответствующих запросу
     */
    public List<Book> searchBooks(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(query, query, query, pageable)
                .getContent();
    }

    /**
     * Подсчёт количества книг, соответствующих поисковому запросу
     * @param query поисковая строка
     * @return количество книг
     */
    public long countSearchResults(String query) {
        Pageable singleItem = PageRequest.of(0, 1);
        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(query, query, query, singleItem)
                .getTotalElements();
    }

    /**
     * Получение всех книг без фильтров
     * @return список всех книг
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
