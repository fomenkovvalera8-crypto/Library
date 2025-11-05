package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.library.service.abstraction.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения страниц книг с использованием пагинации
 */
@Service
@RequiredArgsConstructor
public class BookPaginationService implements PaginationService<Book> {

    private final BookRepository bookRepository;

    /**
     * Получает страницу книг
     * @param page номер страницы (начиная с 0)
     * @param size количество элементов на странице
     * @return объект PageDTO с книгами, информацией о следующей странице и параметрами запроса
     */
    public PageDTO<Book> getPage(int page, int size) {
        Page<Book> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        boolean hasMore = booksPage.hasNext();
        return new PageDTO<>(booksPage.getContent(), hasMore, page, size);
    }
}
