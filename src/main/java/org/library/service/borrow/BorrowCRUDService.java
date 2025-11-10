package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.exception.BorrowNotFoundException;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.library.service.abstraction.CRUDService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления операциями CRUD с сущностью Borrow
 */
@Service
@RequiredArgsConstructor
public class BorrowCRUDService implements CRUDService<Borrow, Long> {

    private final BorrowRepository borrowRepository;

    /**
     * Получение всех записей о взятых книгах
     * @return список всех borrow
     */
    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    /**
     * Возвращает репозиторий, связанный с сущностью Borrow
     */
    @Override
    public JpaRepository<Borrow, Long> getRepository() {
        return borrowRepository;
    }

    /**
     * Обновляет существующую запись Borrow данными из incoming
     * @param existing существующая запись Borrow из базы
     * @param incoming объект с новыми данными
     * @return обновлённая сущность, готовая к сохранению
     */
    @Override
    public Borrow updateEntity(Borrow existing, Borrow incoming) {
        existing.setClient(incoming.getClient());
        existing.setBook(incoming.getBook());
        existing.setBorrowDate(incoming.getBorrowDate());
        return borrowRepository.save(existing);
    }
}