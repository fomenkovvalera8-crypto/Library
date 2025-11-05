package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.library.service.abstraction.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения постраничного списка записей о взятых книгах
 */
@Service
@RequiredArgsConstructor
public class BorrowPaginationService implements PaginationService<Borrow> {

    private final BorrowRepository borrowRepository;

    /**
     * Получение страницы записей о взятых книгах
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return PageDTO с содержимым страницы, информацией о наличии следующей страницы,
     *         текущей страницей и размером страницы
     */
    public PageDTO<Borrow> getPage(int page, int size) {
        Page<Borrow> p = borrowRepository.findAll(PageRequest.of(page, size));
        return new PageDTO<>(p.getContent(), p.hasNext(), page, size);
    }
}