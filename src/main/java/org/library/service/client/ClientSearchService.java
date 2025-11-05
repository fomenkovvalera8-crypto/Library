package org.library.service.client;

import lombok.RequiredArgsConstructor;
import org.library.model.Client;
import org.library.repository.ClientRepository;
import org.library.service.abstraction.SearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для поиска клиентов с поддержкой пагинации.
 */
@Service
@RequiredArgsConstructor
public class ClientSearchService implements SearchService<Client> {
    private final ClientRepository clientRepository;

    /**
     * Поиск клиентов по имени и дате рождения с пагинацией
     * @param query поисковая строка
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return список клиентов, соответствующих запросу
     */
    public List<Client> search(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return clientRepository.searchAllColumns(query, pageable).getContent();
    }

    /**
     * Подсчёт общего количества клиентов, соответствующих поисковому запросу
     * @param query поисковая строка
     * @return количество клиентов
     */
    public long count(String query) {
        Pageable singleItem = PageRequest.of(0, 1);
        return clientRepository
                .searchAllColumns(query, singleItem)
                .getTotalElements();
    }

    /**
     * Получение всех клиентов без фильтров и пагинации
     * @return список всех клиентов
     */
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
