package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.dto.BookPageDTO;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookPaginationService {

    private final BookRepository bookRepository;

    public BookPageDTO getBooksPage(int page, int size) {
        Page<Book> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        boolean hasMore = booksPage.hasNext();
        return new BookPageDTO(booksPage.getContent(), hasMore, page, size);
    }

    public long countBooks() {
        return bookRepository.count();
    }


}
