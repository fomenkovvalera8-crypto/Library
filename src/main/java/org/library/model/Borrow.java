package org.library.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Сущность выдачи книги клиенту.
 */
@Setter
@Getter
@Entity
public class Borrow {
    /** Уникальный идентификатор выдачи */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Клиент, который взял книгу (связь многие-к-одному с Client) */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /** Книга, которая была взята (связь многие-к-одному с Book) */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    /** Дата взятия книги, формат yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;
}