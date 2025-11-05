package org.library.service.book;

import org.library.model.Book;
import org.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookCRUDService {

    private final BookRepository bookRepository;

    public BookCRUDService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteBook(Long id) {
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