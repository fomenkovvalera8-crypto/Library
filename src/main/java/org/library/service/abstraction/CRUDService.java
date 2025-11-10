package org.library.service.abstraction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

/**
 * Универсальный интерфейс для сервисов, реализующих базовые CRUD-операции.
 * @param <T> тип сущности, с которой работает сервис
 */
public interface CRUDService<T, ID> {

    /**
     * Получение репозитория для конкретного класса
     * @return репозиторий
     */
    JpaRepository<T, ID> getRepository();

    /**
     * Получение сущности по её идентификатору
     * @param id идентификатор сущности
     * @return Optional с найденной сущностью или пустой, если сущность не найдена
     */
    default Optional<T> getById(ID id){
        return getRepository().findById(id);
    }

    /**
     * Создание новой сущности или обновление существующей.
     * @param entity объект сущности для сохранения или обновления
     * @param id идентификатор существующей сущности; если null — создаётся новая сущность
     */
    default void saveOrUpdate(T entity, ID id, Function<ID, RuntimeException> notFoundException){
        if (id == null) {
            getRepository().save(entity);
        } else {
            getRepository().findById(id)
                    .map(existing -> updateEntity(existing, entity))
                    .orElseThrow(() -> notFoundException.apply(id));
        }
    }

    /**
     * Удаление сущности по идентификатору
     * @param id идентификатор сущности для удаления
     */
    default void delete(ID id){
        getRepository().deleteById(id);
    }

    /**
     * Метод обновления используемый при маппинге
     * @param existing существующая сущность из базы данных
     * @param incoming объект с обновлёнными данными
     * @return сущность после обновления, готовая к сохранению
     */
    T updateEntity(T existing, T incoming);
}