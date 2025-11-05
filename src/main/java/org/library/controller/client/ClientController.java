package org.library.controller.client;

import lombok.RequiredArgsConstructor;
import org.library.model.Client;
import org.library.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    private static final String VIEW_CLIENT = "clients";
    private static final String VIEW_FORM = "clients-form";
    private static final String REDIRECT_CLIENT = "redirect:/clients";

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

        return VIEW_CLIENT;
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return VIEW_FORM;
    }

    @PostMapping
    public String addClient(@ModelAttribute @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }
        clientService.saveClient(client);
        return REDIRECT_CLIENT;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        model.addAttribute("client", client);
        return VIEW_FORM;
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            client.setId(id);
            return VIEW_FORM;
        }

        clientService.updateClient(id, client);
        return REDIRECT_CLIENT;
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return REDIRECT_CLIENT;
    }

}