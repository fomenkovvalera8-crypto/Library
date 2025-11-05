package org.library.controller.borrow;

import lombok.RequiredArgsConstructor;
import org.library.dto.BorrowDTO;
import org.library.dto.PageDTO;
import org.library.model.Borrow;
import org.library.service.borrow.BorrowCRUDService;
import org.library.service.borrow.BorrowPaginationService;
import org.library.service.borrow.BorrowSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST-контроллер для управления записями о выдачах книг (Borrow)
 */
@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowRestController {

    private final BorrowCRUDService borrowCRUDService;
    private final BorrowSearchService borrowSearchService;
    private final BorrowPaginationService borrowPaginationService;

    /**
     * Возвращает полный список всех выдач в виде DTO-объектов
     * @return список объектов, содержащих сводную информацию о выдачах
     */
    @GetMapping
    public List<BorrowDTO> getAllBorrows() {
        return borrowCRUDService.getAllBorrows().stream()
                .map(b -> new BorrowDTO(
                        b.getClient().getFullName(),
                        b.getClient().getBirthDate(),
                        b.getBook().getTitle(),
                        b.getBook().getAuthor(),
                        b.getBook().getIsbn(),
                        b.getBorrowDate()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Удаляет запись о выдаче по идентификатору
     * @param id идентификатор записи о выдаче
     */
    @DeleteMapping("/{id}")
    public void deleteBorrowAjax(@PathVariable Long id) {
        borrowCRUDService.deleteBorrow(id);
    }

    /**
     * Возвращает страницу записей о выдачах
     * @param page номер страницы
     * @param size количество элементов на странице
     * @return объект с содержимым, текущей страницей, размером и признаком наличия следующей
     */
    @GetMapping("/page")
    public PageDTO<Borrow> getBorrowsPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return borrowPaginationService.getBorrowsPage(page, size);
    }

    /**
     * Выполняет поиск выдач по строке запроса с поддержкой пагинации
     * @param query поисковый запрос
     * @param page  номер страницы
     * @param size  количество элементов на странице
     * @return объект с найденными результатами и данными пагинации
     */
    @GetMapping("/search")
    public PageDTO<Borrow> searchBorrows(@RequestParam("q") String query,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        if (query == null || query.trim().isEmpty()) {
            return borrowPaginationService.getBorrowsPage(page, size);
        }

        List<Borrow> borrows = borrowSearchService.searchBorrows(query, page, size);
        long total = borrowSearchService.countSearchResults(query);
        boolean hasMore = (page + 1) * size < total;

        return new PageDTO<>(borrows, hasMore, page, size);
    }
}
