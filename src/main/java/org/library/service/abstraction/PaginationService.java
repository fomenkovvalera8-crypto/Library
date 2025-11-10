package org.library.service.abstraction;

import org.library.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс для сервисов, поддерживающих пагинацию
 * @param <T> тип сущности
 */
public interface PaginationService<T> {

    /**
     * Возвращает репозиторий, связанный с сущностью.
     * @return JpaRepository для текущей сущности
     */
    JpaRepository<T, Long> getRepository();

    /**
     * Получение страницы сущностей
     * @param page номер страницы (начиная с 0)
     * @param size количество элементов на странице
     * @return объект PageDTO с сущностями, информацией о следующей странице и параметрами запроса
     */
    default PageDTO<T> getPage(int page, int size) {
        Page<T> p = getRepository().findAll(PageRequest.of(page, size));
        return new PageDTO<>(p.getContent(), p.hasNext(), page, size);
    }

}
