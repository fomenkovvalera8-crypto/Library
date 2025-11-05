package org.library.service.borrow;

import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowCRUDService {

    private final BorrowRepository borrowRepository;

    public BorrowCRUDService(BorrowRepository borrowRepository) {
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

}