package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookPaginationService {

    private final BookRepository bookRepository;

    public PageDTO<Book> getBooksPage(int page, int size) {
        Page<Book> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        boolean hasMore = booksPage.hasNext();
        return new PageDTO<>(booksPage.getContent(), hasMore, page, size);
    }
}
