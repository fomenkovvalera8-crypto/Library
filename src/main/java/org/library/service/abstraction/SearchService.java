package org.library.service.abstraction;

import java.util.List;

/**
 * Интерфейс для сервисов поиска сущностей с поддержкой пагинации и подсчёта
 * @param <T> тип сущности
 */
public interface SearchService<T> {

    /**
     * Поиск сущностей по строке запроса с пагинацией
     * @param query поисковая строка
     * @param page номер страницы (начиная с 0)
     * @param size количество элементов на странице
     * @return список найденных сущностей
     */
    List<T> search(String query, int page, int size);

    /**
     * Подсчёт количества сущностей, соответствующих запросу
     * @param query поисковая строка
     * @return количество найденных сущностей
     */
    long count(String query);

}
