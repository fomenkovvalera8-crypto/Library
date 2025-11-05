package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowSearchService {
    private final BorrowRepository borrowRepository;

    public List<Borrow> searchBorrows(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return borrowRepository.searchAllColumns(query, pageable).getContent();
    }

    public long countSearchResults(String query) {
        Pageable singleItem = PageRequest.of(0, 1);
        return borrowRepository.searchAllColumns(query, singleItem).getTotalElements();
    }
}
