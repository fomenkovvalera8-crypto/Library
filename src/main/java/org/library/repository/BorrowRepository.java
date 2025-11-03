package org.library.repository;

import org.library.model.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query(value = "SELECT * FROM borrow b " +
            "LEFT JOIN client c ON b.client_id = c.id " +
            "LEFT JOIN book bk ON b.book_id = bk.id " +
            "WHERE LOWER(c.full_name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR TO_CHAR(c.birth_date, 'DD.MM.YYYY') LIKE CONCAT('%', :query, '%') " +
            "   OR LOWER(bk.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(bk.author) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(bk.isbn) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR TO_CHAR(b.borrow_date, 'DD.MM.YYYY') LIKE CONCAT('%', :query, '%')",
            nativeQuery = true)
    Page<Borrow> searchAllColumns(@Param("query") String query, Pageable pageable);
}
