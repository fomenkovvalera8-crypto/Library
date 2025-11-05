package org.library.repository;

import org.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Репозиторий для работы с сущностью Client.
 * Предоставляет стандартные CRUD-операции и методы для поиска клиентов.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Выполняет поиск клиентов по полному имени и дате рождения. Поиск регистронезависимый
     * @param query поисковая строка
     * @param pageable объект Pageable для пагинации
     * @return страница с найденными клиентами
     */
    @Query("SELECT c FROM Client c " +
            "WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR (c.birthDate IS NOT NULL AND FUNCTION('TO_CHAR', c.birthDate, 'DD.MM.YYYY') LIKE CONCAT('%', :query, '%'))")
    Page<Client> searchAllColumns(@Param("query") String query, Pageable pageable);
}
