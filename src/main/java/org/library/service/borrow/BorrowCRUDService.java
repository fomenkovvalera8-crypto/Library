package org.library.service.borrow;

import lombok.RequiredArgsConstructor;
import org.library.exception.BorrowNotFoundException;
import org.library.model.Borrow;
import org.library.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления операциями CRUD с сущностью Borrow
 */
@Service
@RequiredArgsConstructor
public class BorrowCRUDService {

    private final BorrowRepository borrowRepository;

    /**
     * Получение всех записей о взятых книгах
     * @return список всех borrow
     */
    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    /**
     * Получение конкретной записи по её идентификатору
     * @param id идентификатор borrow
     * @return Optional с найденной записью или пустой, если не найдено
     */
    public Optional<Borrow> getBorrowById(Long id) {
        return borrowRepository.findById(id);
    }

    /**
     * Удаление записи о взятой книге по идентификатору.
     * @param id идентификатор borrow
     */
    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }

    /**
     * Создание новой записи о взятой книге или обновление существующей
     * @param borrow объект borrow с данными
     * @param id идентификатор для обновления или null для создания
     */
    public void saveOrUpdateBorrow(Borrow borrow, Long id) {
        if (id == null) {
            borrowRepository.save(borrow);
        } else {
            borrowRepository.findById(id)
                    .map(existingBorrow -> {
                        existingBorrow.setClient(borrow.getClient());
                        existingBorrow.setBook(borrow.getBook());
                        existingBorrow.setBorrowDate(borrow.getBorrowDate());
                        return borrowRepository.save(existingBorrow);
                    })
                    .orElseThrow(() -> new BorrowNotFoundException(id));
        }
    }

}