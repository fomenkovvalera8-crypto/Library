package org.library.controller;

import org.library.model.Client;
import org.library.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String listClients(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        List<Client> clients = clientService.getClientsPage(page, size);
        long total = clientService.countClients();

        model.addAttribute("clients", clients);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("hasMore", (page + 1) * size < total);

        return "clients";
    }
    @GetMapping("/page")
    @ResponseBody
    public List<Client> getClientsPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return clientService.getClientsPage(page, size);
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping
    public String addClient(@ModelAttribute Client client) {
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        return "client-form";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute Client client) {
        clientService.updateClient(id, client);
        return "redirect:/clients";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteClientAjax(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Client> searchClients(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clientService.searchByNamePaged(query, page, size);
    }
}