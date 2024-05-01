package pl.kurs.repository;

import pl.kurs.model.Transaction;
import pl.kurs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAmountBetweenAndDateBetweenAndUser(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate, User user);
}

