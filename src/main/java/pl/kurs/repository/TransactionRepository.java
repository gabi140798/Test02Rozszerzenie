package pl.kurs.repository;

import org.springframework.data.jpa.repository.Query;
import pl.kurs.model.Transaction;
import pl.kurs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t.user.id, COUNT(t) FROM Transaction t GROUP BY t.user.id")
    List<Object[]> countTransactionsByUser();

    List<Transaction> findByAmountBetweenAndDateBetweenAndUser(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate, User user);
}

