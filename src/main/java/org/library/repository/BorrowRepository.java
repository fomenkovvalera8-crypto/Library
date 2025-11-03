package org.library.repository;

import org.library.model.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("SELECT b FROM Borrow b " +
            "WHERE LOWER(b.client.fullName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR CAST(b.client.birthDate AS string) LIKE CONCAT('%', :query, '%') " +
            "   OR LOWER(b.book.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.book.author) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(b.book.isbn) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR CAST(b.borrowDate AS string) LIKE CONCAT('%', :query, '%')")
    Page<Borrow> searchAllColumns(@Param("query") String query, Pageable pageable);
    Page<Borrow> findByClientFullNameContainingIgnoreCaseOrBookTitleContainingIgnoreCase(String clientName, String bookTitle, Pageable pageable);
}
