package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.library.service.abstraction.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения постраничного списка записей о взятых книгах
 */
@Service
@RequiredArgsConstructor
public class BorrowPaginationService implements PaginationService<Borrow> {

    private final BorrowRepository borrowRepository;

    @Override
    public JpaRepository<Borrow, Long> getRepository() {
        return borrowRepository;
    }
}