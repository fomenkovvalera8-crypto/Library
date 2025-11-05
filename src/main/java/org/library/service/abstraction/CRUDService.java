package org.library.service.abstraction;

import java.util.Optional;

/**
 * Универсальный интерфейс для сервисов, реализующих базовые CRUD-операции.
 * @param <T> тип сущности, с которой работает сервис
 */
public interface CRUDService<T> {
    /**
     * Получение сущности по её идентификатору
     * @param id идентификатор сущности
     * @return Optional с найденной сущностью или пустой, если сущность не найдена
     */
    Optional<T> getById(Long id);

    /**
     * Создание новой сущности или обновление существующей.
     * @param entity объект сущности для сохранения или обновления
     * @param id идентификатор существующей сущности; если null — создаётся новая сущность
     */
    void saveOrUpdate(T entity, Long id);

    /**
     * Удаление сущности по идентификатору
     * @param id идентификатор сущности для удаления
     */
    void delete(Long id);
}