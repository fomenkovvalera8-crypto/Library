package org.library.controller.client;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.model.Client;
import org.library.service.client.ClientCRUDService;
import org.library.service.client.ClientPaginationService;
import org.library.service.client.ClientSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientCRUDService clientCRUDService;
    private final ClientSearchService clientSearchService;
    private final ClientPaginationService clientPaginationService;


    /**
     * Выполняет поиск клиентов с поддержкой пагинации
     * @param query поисковая строка
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return объект с найденными клиентами, информацией о странице и наличии следующей страницы
     */
    @GetMapping("/search")
    public PageDTO<Client> searchClients(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (query == null || query.trim().isEmpty()) {
            return clientPaginationService.getPage(page, size);
        }
        List<Client> clients = clientSearchService.search(query, page, size);
        long total = clientSearchService.count(query);
        boolean hasMore = (page + 1) * size < total;

        return new PageDTO<>(clients, hasMore, page, size);
    }

    /**
     * Удаляет клиента по идентификатору через AJAX-запрос.
     * @param id идентификатор клиента
     */
    @DeleteMapping("/{id}")
    public void deleteClientAjax(@PathVariable Long id) {
        clientCRUDService.delete(id);
    }

    /**
     * Возвращает страницу клиентов с указанными параметрами пагинации.
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return объект с клиентами и данными о пагинации
     */
    @GetMapping("/page")
    public PageDTO<Client> getClientsPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return clientPaginationService.getPage(page, size);
    }
}
