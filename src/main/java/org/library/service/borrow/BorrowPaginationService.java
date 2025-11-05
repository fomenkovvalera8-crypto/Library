package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowPaginationService {

    private final BorrowRepository borrowRepository;

    public PageDTO<Borrow> getBorrowsPage(int page, int size) {
        Page<Borrow> p = borrowRepository.findAll(PageRequest.of(page, size));
        return new PageDTO<>(p.getContent(), p.hasNext(), page, size);
    }
}