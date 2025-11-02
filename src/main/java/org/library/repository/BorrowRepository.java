package org.library.repository;

import org.library.model.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Page<Borrow> findByClientFullNameContainingIgnoreCaseOrBookTitleContainingIgnoreCase(String clientName, String bookTitle, Pageable pageable);
}
