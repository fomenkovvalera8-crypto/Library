package org.library.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDTO<T> {
    private List<T> content;
    private boolean hasMore;
    private int page;
    private int size;

    public PageDTO(List<T> content, boolean hasMore, int page, int size) {
        this.content = content;
        this.hasMore = hasMore;
        this.page = page;
        this.size = size;
    }
}
