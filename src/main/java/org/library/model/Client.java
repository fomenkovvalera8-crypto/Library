package org.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность клиента библиотеки
 */
@Setter
@Getter
@Entity
public class Client {
    /** Уникальный идентификатор клиента */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Полное имя клиента, обязательное поле, только буквы, максимум 100 символов */
    @NotBlank(message = "ФИО клиента обязательно")
    @Size(max = 100, message = "ФИО клиента не должно превышать 100 символов")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z\\s]+$", message = "ФИО должно содержать только буквы")
    private String fullName;

    /** Дата рождения клиента, формат yyyy-MM-dd */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /** Список всех выдач, связанных с этим клиентом (связь один-ко-многим с Borrow) */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Borrow> borrows = new ArrayList<>();
}
