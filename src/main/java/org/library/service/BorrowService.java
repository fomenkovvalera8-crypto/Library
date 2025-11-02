package org.library.service;

import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public Optional<Borrow> getBorrowById(Long id) {
        return borrowRepository.findById(id);
    }

    public void saveBorrow(Borrow borrow) {

        borrowRepository.save(borrow);
    }

    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }

    public void updateBorrow(Long id, Borrow borrow) {
        borrow.setId(id);
        borrowRepository.save(borrow);
    }

    public List<Borrow> getBorrowsPage(int page, int size) {
        Page<Borrow> p = borrowRepository.findAll(PageRequest.of(page, size));
        return p.getContent();
    }

    // Общее количество записей
    public long countBorrows() {
        return borrowRepository.count();
    }
    public List<Borrow> searchBorrows(String query, int page, int size) {
        Page<Borrow> p = borrowRepository.findByClientFullNameContainingIgnoreCaseOrBookTitleContainingIgnoreCase(
                query, query, PageRequest.of(page, size));
        return p.getContent();
    }
}