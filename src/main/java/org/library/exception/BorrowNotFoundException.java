package org.library.exception;

/**
 * Исключение, выбрасываемое, если бронирование с указанным идентификатором не найдено.
 */
public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(Long id) {
        super("Бронирование с ID " + id + " не найдено");
    }
}
