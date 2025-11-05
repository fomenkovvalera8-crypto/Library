package org.library.controller.client;

import lombok.RequiredArgsConstructor;
import org.library.model.Client;
import org.library.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping("/search")
    @ResponseBody
    public List<Client> searchClients(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clientService.searchByNamePaged(query, page, size);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteClientAjax(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/page")
    @ResponseBody
    public List<Client> getClientsPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return clientService.getClientsPage(page, size);
    }
}
