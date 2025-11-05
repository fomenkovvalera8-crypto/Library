package org.library.exception;

/**
 * Исключение, выбрасываемое, если клиент с указанным идентификатором не найден.
 */
public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long id) {
        super("Клиент с ID " + id + " не найден");
    }
}
