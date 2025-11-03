package org.library.repository;

import org.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c " +
            "WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR CAST(c.birthDate AS string) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Client> searchAllColumns(@Param("query") String query, Pageable pageable);
}
