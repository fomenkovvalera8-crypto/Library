package org.library.service.client;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Client;
import org.library.repository.ClientRepository;
import org.library.service.abstraction.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


/**
 * Сервис для работы с клиентами с поддержкой пагинации
 */
@Service
@RequiredArgsConstructor
public class ClientPaginationService implements PaginationService<Client> {
    private final ClientRepository clientRepository;

    /**
     * Получение страницы клиентов
     * @param page номер страницы (0 — первая страница)
     * @param size количество элементов на странице
     * @return объект PageDTO с клиентами, информацией о следующей странице и параметрами пагинации
     */
    public PageDTO<Client> getPage(int page, int size) {
        Page<Client> clientsPage = clientRepository.findAll(PageRequest.of(page, size));
        boolean hasMore = clientsPage.hasNext();
        return new PageDTO<>(clientsPage.getContent(), hasMore, page, size);
    }
}
