package org.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BorrowDTO {
    private String clientName;
    private LocalDate clientBirthDate;
    private String bookTitle;
    private String bookAuthor;
    private String isbn;
    private LocalDate borrowDate;

    public BorrowDTO(String clientName, LocalDate clientBirthDate, String bookTitle,
                     String bookAuthor, String isbn, LocalDate borrowDate) {
        this.clientName = clientName;
        this.clientBirthDate = clientBirthDate;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.isbn = isbn;
        this.borrowDate = borrowDate;
    }

}