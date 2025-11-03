package org.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @NotBlank(message = "ФИО автора обязательно")
    @Size(max = 100, message = "ФИО автора не должно превышать 100 символов")
    @Pattern(regexp = "^[А-Яа-яЁёA-Za-z\\s]+$", message = "ФИО должно содержать только буквы")
    private String author;
    @NotBlank(message = "ISBN обязательно")
    @Pattern(regexp = "\\d{3}-\\d-\\d{5}-\\d{3}-\\d", message = "ISBN должен быть в формате xxx-x-xxxxx-xxx-x")
    private String isbn;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Borrow> borrows = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<Borrow> borrows) {
        this.borrows = borrows;
    }
}
