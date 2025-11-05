package org.library.controller.borrow;

import lombok.RequiredArgsConstructor;
import org.library.dto.PageDTO;
import org.library.exception.BorrowNotFoundException;
import org.library.model.Borrow;
import org.library.service.borrow.BorrowPaginationService;
import org.library.service.borrow.BorrowCRUDService;
import org.library.service.client.ClientCRUDService;
import org.library.service.book.BookCRUDService;
import org.library.service.book.BookSearchService;
import org.library.service.client.ClientSearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для управления записями о выдачах книг
 */
@Controller
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowCRUDService borrowCRUDService;
    private final BorrowPaginationService borrowPaginationService;
    private final BookCRUDService bookCRUDService;
    private final BookSearchService bookSearchService;
    private final ClientCRUDService clientCRUDService;
    private final ClientSearchService clientSearchService;

    private static final String VIEW_BORROWS = "borrows";
    private static final String VIEW_FORM = "borrow-form";
    private static final String REDIRECT_BORROWS = "redirect:/borrows";

    /**
     * Отображает список всех выдач с пагинацией
     * @param model объект для передачи данных на страницу
     * @param page  номер страницы
     * @param size  количество элементов на странице
     * @return имя шаблона отображения
     */
    @GetMapping
    public String listBorrows(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {

        PageDTO<Borrow> borrowPage = borrowPaginationService.getBorrowsPage(page, size);

        model.addAttribute("borrows", borrowPage.getContent());
        model.addAttribute("hasMore", borrowPage.isHasMore());
        model.addAttribute("page", borrowPage.getPage());
        model.addAttribute("size", borrowPage.getSize());

        return VIEW_BORROWS;
    }

    /**
     * Отображает форму добавления новой выдачи
     * @param model объект для передачи в шаблон новой записи со списком всех доступных книг и клиентов
     * @return имя шаблона отображения
     */
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("borrow", new Borrow());
        model.addAttribute("books", bookSearchService.getAllBooks());
        model.addAttribute("clients", clientSearchService.getAllClients());
        return VIEW_FORM;
    }

    /**
     * Обрабатывает создание новой выдачи книги
     * @param borrow объект бронирования заполненный из формы
     * @return перенаправление на страницу списка выдач
     * @throws IllegalArgumentException если указанный клиент или книга не найдены
     */
    @PostMapping
    public String addBorrow(@ModelAttribute Borrow borrow) {
        validateBorrowEntities(borrow);
        borrowCRUDService.saveOrUpdateBorrow(borrow, null);
        return REDIRECT_BORROWS;
    }

    /**
     * Отображает форму редактирования существующей выдачи
     * @param id идентификатор выдачи
     * @param model объект с текущими данными выдачи, книг и клиентов
     * @return имя шаблона отображения
     * @throws BorrowNotFoundException если выдача с указанным ID не найдена
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Borrow borrow = borrowCRUDService.getBorrowById(id)
                .orElseThrow(() -> new BorrowNotFoundException(id));
        model.addAttribute("borrow", borrow);
        model.addAttribute("books", bookSearchService.getAllBooks());
        model.addAttribute("clients", clientSearchService.getAllClients());
        return VIEW_FORM;
    }

    /**
     * Обрабатывает обновление существующей записи выдачи
     * @param id идентификатор редактируемой выдачи
     * @param borrow объект выдачи с обновлёнными данными
     * @return перенаправление на страницу списка выдач
     * @throws IllegalArgumentException если указанный клиент или книга не найдены
     */
    @PostMapping("/update/{id}")
    public String updateBorrow(@PathVariable Long id, @ModelAttribute Borrow borrow) {
        validateBorrowEntities(borrow);
        borrowCRUDService.saveOrUpdateBorrow(borrow, id);
        return REDIRECT_BORROWS;
    }

    /**
     * Удаляет запись о выдаче по идентификатору
     * @param id идентификатор удаляемой выдачи
     * @return перенаправление на список выдач
     */
    @GetMapping("/delete/{id}")
    public String deleteBorrow(@PathVariable Long id) {
        borrowCRUDService.deleteBorrow(id);
        return REDIRECT_BORROWS;
    }

    /**
     * Проверяет корректность связанных сущностей перед сохранением или обновлением выдачи
     * @param borrow объект выдачи для проверки
     * @throws IllegalArgumentException если клиент или книга не найдены в базе
     */
    private void validateBorrowEntities(@ModelAttribute Borrow borrow) {
        if (borrow.getClient() == null || !clientCRUDService.getClientById(borrow.getClient().getId()).isPresent()) {
            throw new IllegalArgumentException("Клиент не найден");
        }
        if (borrow.getBook() == null || !bookCRUDService.getBookById(borrow.getBook().getId()).isPresent()) {
            throw new IllegalArgumentException("Книга не найдена");
        }
    }
}