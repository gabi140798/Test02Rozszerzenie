package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.exceptions.InsufficientFundsException;
import pl.kurs.model.Transaction;
import pl.kurs.model.TransactionCriteria;
import pl.kurs.model.TransactionType;
import pl.kurs.model.User;
import pl.kurs.repository.TransactionRepository;
import pl.kurs.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void transferMoney_SuccessfulTransfer() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        double amount = 100.0;

        User sender = new User(fromUserId, "Kamil", "Nowak", 200.0);
        User receiver = new User(toUserId, "Anna", "Kowalska", 100.0);

        when(userRepository.findByIdForUpdate(fromUserId)).thenReturn(Optional.of(sender));
        when(userRepository.findByIdForUpdate(toUserId)).thenReturn(Optional.of(receiver));

        assertDoesNotThrow(() -> transactionService.transferMoney(fromUserId, toUserId, amount));

        assertEquals(100.0, sender.getBalance());
        assertEquals(200.0, receiver.getBalance());
    }

    @Test
    void transferMoney_InsufficientFunds() {
        Long fromUserId = 1L;
        Long toUserId = 2L;
        double amount = 300.0;

        User sender = new User(fromUserId, "Kamil", "Nowak", 200.0);
        User receiver = new User(toUserId, "Anna", "Kowalska", 100.0);

        when(userRepository.findByIdForUpdate(fromUserId)).thenReturn(Optional.of(sender));
        when(userRepository.findByIdForUpdate(toUserId)).thenReturn(Optional.of(receiver));

        assertThrows(InsufficientFundsException.class, () -> transactionService.transferMoney(fromUserId, toUserId, amount));

        assertEquals(200.0, sender.getBalance());
        assertEquals(100.0, receiver.getBalance());
    }

    @Test
    public void testFindTransactions() {
        User user = new User("Kamil", "Nowak", 1000);
        Transaction transaction = new Transaction(300, LocalDate.now(), TransactionType.TRANSFER, user);
        List<Transaction> expectedTransactions = Collections.singletonList(transaction);

         TransactionCriteria criteria = new TransactionCriteria(200.0, 400.0, LocalDate.now(), LocalDate.now(), user.getId());

        when(transactionRepository.findByAmountBetweenAndTransactionDateBetweenAndUser(criteria.getMinAmount(), criteria.getMaxAmount(), criteria.getStart(), criteria.getFinish(), user))
                .thenReturn(expectedTransactions);

        List<Transaction> result = transactionService.findTransactions(criteria, user);

        assertFalse(result.isEmpty(), "The result should not be empty");
        assertEquals(1, result.size(),"Expected 1 transaction in the result");
        assertEquals(300.0, result.get(0).getAmount(), 0.001,"The transaction amount should match");
    }
}