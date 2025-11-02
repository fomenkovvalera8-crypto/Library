package org.library.service;

import org.library.model.Client;
import org.library.repository.ClientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public void updateClient(Long id, Client updatedClient) {
        clientRepository.findById(id)
                .map(client -> {
                    client.setFullName(updatedClient.getFullName());
                    client.setBirthDate(updatedClient.getBirthDate());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }

    public List<Client> searchByNamePaged(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return clientRepository.findByFullNameContainingIgnoreCase(query, pageable).getContent();
    }

    public List<Client> getClientsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        return clientRepository.findAll(pageable).getContent();
    }

    public long countClients() {
        return clientRepository.count();
    }

}
