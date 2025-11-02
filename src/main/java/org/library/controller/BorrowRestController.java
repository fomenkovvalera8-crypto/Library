package org.library.controller;

import org.library.dto.BorrowDTO;
import org.library.service.BorrowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/borrows")
public class BorrowRestController {

    private final BorrowService borrowService;

    public BorrowRestController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

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
}
