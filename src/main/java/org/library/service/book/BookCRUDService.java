package org.library.service.book;

import lombok.RequiredArgsConstructor;
import org.library.model.Book;
import org.library.repository.BookRepository;
import org.library.service.abstraction.CRUDService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для выполнения CRUD-операций с сущностью Book
 */
@Service
@RequiredArgsConstructor
public class BookCRUDService implements CRUDService<Book> {

    private final BookRepository bookRepository;

    /**
     * Получает книгу по её идентификатору
     * @param id идентификатор книги
     * @return Optional с найденной книгой или пустой, если книга не найдена
     */
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Удаляет книгу по идентификатору.
     * @param id идентификатор книги для удаления
     */
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Создание или обновление книги
     * @param book объект книги
     * @param id если null — создаём новую книгу, иначе обновляем существующую
     */
    public void saveOrUpdate(Book book, Long id) {
        if (id == null) {
            bookRepository.save(book);
        } else {
            bookRepository.findById(id)
                    .map(existingBook -> {
                        existingBook.setTitle(book.getTitle());
                        existingBook.setAuthor(book.getAuthor());
                        existingBook.setIsbn(book.getIsbn());
                        return bookRepository.save(existingBook);
                    })
                    .orElseThrow(() -> new RuntimeException("Книга с id " + id + " не найдена"));
        }
    }

}