package org.library.service.abstraction;

import org.library.dto.PageDTO;

/**
 * Интерфейс для сервисов, поддерживающих пагинацию
 * @param <T> тип сущности
 */
public interface PaginationService<T> {

    /**
     * Получение страницы сущностей
     * @param page номер страницы (начиная с 0)
     * @param size количество элементов на странице
     * @return объект PageDTO с сущностями, информацией о следующей странице и параметрами запроса
     */
    PageDTO<T> getPage(int page, int size);
}
