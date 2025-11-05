package org.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO для отображения информации о взятой книге
 */
@Setter
@Getter
public class BorrowDTO {
    /** Полное имя клиента, который взял книгу */
    private String clientName;

    /** Дата рождения клиента */
    private LocalDate clientBirthDate;

    /** Название книги */
    private String bookTitle;

    /** Автор книги */
    private String bookAuthor;

    /** ISBN книги */
    private String isbn;

    /** Дата взятия книги */
    private LocalDate borrowDate;

    /**
     * Конструктор для создания DTO с полной информацией.
     * @param clientName полное имя клиента
     * @param clientBirthDate дата рождения клиента
     * @param bookTitle название книги
     * @param bookAuthor автор книги
     * @param isbn ISBN книги
     * @param borrowDate дата взятия книги
     */
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