package org.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое, если бронирование с указанным идентификатором не найдено.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(Long id) {
        super("Бронирование с ID " + id + " не найдено");
    }
}
