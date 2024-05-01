package service;

import exceptions.InsufficientFundsException;
import exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Transaction;
import model.TransactionCriteria;
import model.User;
import org.springframework.stereotype.Service;
import repository.TransactionRepository;
import repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void transferMoney(Long fromUserId, Long toUserId, double amount) throws InsufficientFundsException {
        Long lowerId = Math.min(fromUserId, toUserId);
        Long higherId = Math.max(fromUserId, toUserId);

        User user1 = userRepository.findByIdForUpdate(lowerId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + lowerId));
        User user2 = userRepository.findByIdForUpdate(higherId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + higherId));

        User sender = user1.getId().equals(fromUserId) ? user1 : user2;
        User receiver = user1.getId().equals(toUserId) ? user1 : user2;

        if (sender.getBalance() >= amount) {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
        } else {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }


    public List<Transaction> findTransactions(TransactionCriteria criteria, User user) {
        return transactionRepository.findByAmountBetweenAndDateBetweenAndUser(
                criteria.getMinAmount(),
                criteria.getMaxAmount(),
                criteria.getStart(),
                criteria.getFinish(),
                user);
    }
}
