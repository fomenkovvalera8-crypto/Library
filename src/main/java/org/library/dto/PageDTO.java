package org.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Универсальный объект передачи данных для пагинации.
 * @param <T> тип содержимого страницы
 */
@Getter
@Setter
public class PageDTO<T> {
    /** Список элементов на текущей странице */
    private List<T> content;

    /** Флаг, указывающий, есть ли следующая страница */
    private boolean hasMore;

    /** Номер текущей страницы (0-based) */
    private int page;

    /** Размер страницы (количество элементов на странице) */
    private int size;

    /**
     * Конструктор для создания объекта PageDTO
     * @param content список элементов на текущей странице
     * @param hasMore true, если есть следующая страница, иначе false
     * @param page номер текущей страницы (0-based)
     * @param size размер страницы
     */
    public PageDTO(List<T> content, boolean hasMore, int page, int size) {
        this.content = content;
        this.hasMore = hasMore;
        this.page = page;
        this.size = size;
    }
}
