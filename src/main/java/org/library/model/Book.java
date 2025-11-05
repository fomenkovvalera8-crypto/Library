package org.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность книги.
 */
@Setter
@Getter
@Entity
public class Book {
    /** Уникальный идентификатор книги */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название книги */
    private String title;

    /** ФИО автора книги */
    @NotBlank(message = "ФИО автора обязательно")
    @Size(max = 100, message = "ФИО автора не должно превышать 100 символов")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z\\s]+$", message = "ФИО должно содержать только буквы")
    private String author;

    /** ISBN книги, формат xxx-x-xxxxx-xxx-x */
    @NotBlank(message = "ISBN обязательно")
    @Pattern(regexp = "\\d{3}-\\d-\\d{5}-\\d{3}-\\d", message = "ISBN должен быть в формате xxx-x-xxxxx-xxx-x")
    private String isbn;

    /** Список всех выдач этой книги, связь один-ко-многим с сущностью Borrow */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Borrow> borrows = new ArrayList<>();
}
