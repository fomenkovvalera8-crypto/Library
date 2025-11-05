package org.library.dto;

import lombok.Getter;
import lombok.Setter;
import org.library.model.Book;

import java.util.List;

@Getter
@Setter
public class BookPageDTO {
    private List<Book> books;
    private boolean hasMore;
    private int page;
    private int size;

    public BookPageDTO(List<Book> books, boolean hasMore, int page, int size) {
        this.books = books;
        this.hasMore = hasMore;
        this.page = page;
        this.size = size;
    }
}