package org.library.repository;

import org.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Book.
 * Предоставляет стандартные CRUD-операции и методы для поиска книг.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Выполняет поиск книг по частичному совпадению с названием, автором или ISBN (регистронезависимо),
     * возвращая результат в виде страницы.
     * @param title часть названия книги для поиска
     * @param author часть имени автора для поиска
     * @param isbn часть ISBN для поиска
     * @param pageable объект Pageable для задания номера страницы и размера
     * @return страница с найденными книгами
     */
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(String title, String author, String isbn, Pageable pageable);
}
