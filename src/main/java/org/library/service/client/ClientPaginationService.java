package org.library.service.client;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Client;
import org.library.repository.ClientRepository;
import org.library.service.abstraction.PaginationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


/**
 * Сервис для работы с клиентами с поддержкой пагинации
 */
@Service
@RequiredArgsConstructor
public class ClientPaginationService implements PaginationService<Client> {
    private final ClientRepository clientRepository;

    @Override
    public JpaRepository<Client, Long> getRepository() {
        return clientRepository;
    }
}
