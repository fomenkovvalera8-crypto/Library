package org.library.controller.client;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.exception.ClientNotFoundException;
import org.library.model.Client;
import org.library.service.client.ClientCRUDService;
import org.library.service.client.ClientPaginationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientPaginationService clientPaginationService;
    private final ClientCRUDService clientCRUDService;

    private static final String VIEW_CLIENT = "clients";
    private static final String VIEW_FORM = "client-form";
    private static final String REDIRECT_CLIENT = "redirect:/clients";

    /**
     * Отображает страницу со списком клиентов с поддержкой пагинации
     * @param page номер страницы
     * @param size количество элементов на странице
     * @param model модель для передачи данных в представление
     * @return имя шаблона для отображения
     */
    @GetMapping
    public String listClients(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        PageDTO<Client> clientPage = clientPaginationService.getClientsPage(page, size);

        model.addAttribute("clients", clientPage.getContent());
        model.addAttribute("hasMore", clientPage.isHasMore());
        model.addAttribute("page", clientPage.getPage());
        model.addAttribute("size", clientPage.getSize());

        return VIEW_CLIENT;
    }

    /**
     * Отображает форму добавления нового клиента
     * @param model модель для передачи данных в представление
     * @return имя шаблона для отображения
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return VIEW_FORM;
    }

    /**
     * Добавление клиента
     * @param client объект клиента из формы
     * @param bindingResult результат валидации
     * @return перенаправление на страницу с клиентами, если ошибок нет; иначе возвращает форму добавления
     */
    @PostMapping
    public String addClient(@ModelAttribute @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }
        clientCRUDService.saveOrUpdateClient(client, null);
        return REDIRECT_CLIENT;
    }

    /**
     * Отображает форму редактирования существующего клиента.
     * @param id идентификатор клиента для редактирования
     * @param model модель для передачи данных в представление
     * @return имя шаблона для формы клиента
     * @throws ClientNotFoundException если клиент с указанным id не найден
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientCRUDService.getClientById(id).orElseThrow(() -> new ClientNotFoundException(id));
        model.addAttribute("client", client);
        return VIEW_FORM;
    }

    /**
     * Редактирование клиента
     * @param id идентификатор редактируемого клиента
     * @param client объект клиента из формы
     * @param bindingResult результат валидации
     * @return перенаправление на страницу с клиентами, если ошибок нет; иначе возвращает форму
     */
    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id, @ModelAttribute @Valid Client client, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            client.setId(id);
            return VIEW_FORM;
        }

        clientCRUDService.saveOrUpdateClient(client, id);
        return REDIRECT_CLIENT;
    }


    /**
     * Удаляет клиента по указанному идентификатору
     * @param id идентификатор клиента для удаления
     * @return перенаправление на страницу с клиентами
     */
    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id) {
        clientCRUDService.deleteClient(id);
        return REDIRECT_CLIENT;
    }

}