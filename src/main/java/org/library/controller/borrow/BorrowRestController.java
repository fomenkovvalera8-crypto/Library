package org.library.controller.borrow;

import lombok.RequiredArgsConstructor;
import org.library.dto.BorrowDTO;
import org.library.model.Borrow;
import org.library.service.BorrowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowRestController {

    private final BorrowService borrowService;

    @GetMapping
    public List<BorrowDTO> getAllBorrows() {
        return borrowService.getAllBorrows().stream()
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

    @DeleteMapping("/{id}")
    public void deleteBorrowAjax(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
    }

    @GetMapping("/page")
    public List<Borrow> getBorrowsPage(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return borrowService.getBorrowsPage(page, size);
    }

    @GetMapping("/search")
    public List<Borrow> searchBorrows(@RequestParam("q") String query,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        if (query == null || query.trim().isEmpty()) {
            return borrowService.getBorrowsPage(page, size);
        }
        return borrowService.searchBorrows(query, page, size);
    }
}
