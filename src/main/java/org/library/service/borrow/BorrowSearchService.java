package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для поиска записей о взятых книгах с поддержкой постраничного вывода
 */
@Service
@RequiredArgsConstructor
public class BorrowSearchService {
    private final BorrowRepository borrowRepository;

    /**
     * Поиск записей о взятых книгах по всем колонкам (клиент, книга, даты) с пагинацией
     * @param query поисковая строка
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return список Borrow, удовлетворяющих поисковому запросу
     */
    public List<Borrow> searchBorrows(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return borrowRepository.searchAllColumns(query, pageable).getContent();
    }

    /**
     * Подсчёт общего количества записей о взятых книгах, удовлетворяющих поисковому запросу
     * @param query поисковая строка
     * @return количество найденных записей
     */
    public long countSearchResults(String query) {
        Pageable singleItem = PageRequest.of(0, 1);
        return borrowRepository.searchAllColumns(query, singleItem).getTotalElements();
    }
}
