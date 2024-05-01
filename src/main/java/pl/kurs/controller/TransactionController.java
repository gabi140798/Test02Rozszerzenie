package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kurs.model.Transaction;
import pl.kurs.model.TransactionCriteria;
import pl.kurs.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.service.TransactionService;
import pl.kurs.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam Long fromUserId, @RequestParam Long toUserId, @RequestParam double amount) {
        transactionService.transferMoney(fromUserId, toUserId, amount);
        return ResponseEntity.ok("Transfer completed successfully.");
    }

    @GetMapping("/search")
    public List<Transaction> searchTransactions(@ModelAttribute TransactionCriteria criteria) {
        User user = userService.findUserById(criteria.getUserId());
        return transactionService.findTransactions(criteria, user);
    }
}
