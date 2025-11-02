package org.library.dto;

import java.time.LocalDate;

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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getClientBirthDate() {
        return clientBirthDate;
    }

    public void setClientBirthDate(LocalDate clientBirthDate) {
        this.clientBirthDate = clientBirthDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}