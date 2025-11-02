package org.library.repository;

import org.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByFullNameContainingIgnoreCase(String fullName);
    Page<Client> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
}
