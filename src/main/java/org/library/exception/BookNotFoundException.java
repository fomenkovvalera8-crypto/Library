package org.library.exception;

/**
 * Исключение, выбрасываемое, если книга с указанным идентификатором не найдена.
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Книга с ID " + id + " не найдена");
    }

}
