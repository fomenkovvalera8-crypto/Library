package org.library.service.client;

import lombok.RequiredArgsConstructor;
import org.library.exception.ClientNotFoundException;
import org.library.model.Client;
import org.library.repository.ClientRepository;
import org.library.service.abstraction.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для управления клиентами: создание, обновление, удаление и получение по ID
 */
@Service
@RequiredArgsConstructor
public class ClientCRUDService implements CRUDService<Client, Long> {

    private final ClientRepository clientRepository;

    /**
     * Возвращает репозиторий, связанный с сущностью Client
     */
    @Override
    public JpaRepository<Client, Long> getRepository() {
        return clientRepository;
    }

    /**
     * Обновляет данные клиента, используя новые значения из incoming
     * @param existing существующий клиент из базы данных
     * @param incoming клиент с обновлёнными данными
     * @return обновлённая сущность клиента
     */
    @Override
    public Client updateEntity(Client existing, Client incoming) {
        existing.setFullName(incoming.getFullName());
        existing.setBirthDate(incoming.getBirthDate());
        return clientRepository.save(existing);
    }
}
