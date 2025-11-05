package org.library.service.client;

import lombok.RequiredArgsConstructor;
import org.library.exception.ClientNotFoundException;
import org.library.model.Client;
import org.library.repository.ClientRepository;
import org.library.service.abstraction.CRUDService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для управления клиентами: создание, обновление, удаление и получение по ID
 */
@Service
@RequiredArgsConstructor
public class ClientCRUDService implements CRUDService<Client> {

    private final ClientRepository clientRepository;

    /**
     * Получение клиента по ID
     * @param id идентификатор клиента
     * @return Optional с найденным клиентом или пустой, если не найден
     */
    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    /**
     * Удаление клиента по ID.
     * @param id идентификатор клиента
     */
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    /**
     * Создание нового клиента или обновление существующего
     * @param client объект клиента с актуальными данными
     * @param id идентификатор клиента для обновления (null для создания)
     */
    public void saveOrUpdate(Client client, Long id) {
        if (id == null) {
            clientRepository.save(client);
        } else {
            clientRepository.findById(id)
                    .map(existingClient -> {
                        existingClient.setFullName(client.getFullName());
                        existingClient.setBirthDate(client.getBirthDate());
                        return clientRepository.save(existingClient);
                    })
                    .orElseThrow(() -> new ClientNotFoundException(id));
        }
    }
}
