package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.exception.BookNotFoundException;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.library.service.abstraction.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для выполнения CRUD-операций с сущностью Book
 */
@Service
@RequiredArgsConstructor
public class BookCRUDService implements CRUDService<Book, Long> {

    private final BookRepository bookRepository;

    /**
     * Получение репозитория для работы с книгами
     * @return репозиторий
     */
    @Override
    public JpaRepository<Book, Long> getRepository() {
        return bookRepository;
    }

    /**
     * Метод обновления используемый при маппинге
     * @param existing существующая сущность из базы данных
     * @param incoming объект с обновлёнными данными
     * @return сущность после обновления, готовая к сохранению
     */
    @Override
    public Book updateEntity(Book existing, Book incoming) {
        existing.setTitle(incoming.getTitle());
        existing.setAuthor(incoming.getAuthor());
        existing.setIsbn(incoming.getIsbn());
        return bookRepository.save(existing);
    }
}