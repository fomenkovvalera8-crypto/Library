package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.library.service.abstraction.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения страниц книг с использованием пагинации
 */
@Service
@RequiredArgsConstructor
public class BookPaginationService implements PaginationService<Book> {

    private final BookRepository bookRepository;

    @Override
    public JpaRepository<Book, Long> getRepository() {
        return bookRepository;
    }
}
