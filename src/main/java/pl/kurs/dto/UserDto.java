package pl.kurs.dto;

import pl.kurs.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDto(Long id, String name, String surname, double balance, Set<TransactionDto> numerOfTransfers) {

    public static UserDto from(User user) {
        Set<TransactionDto> transactions = user.getTransactions().stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTransactionDate(),
                        user.getId()))
                .collect(Collectors.toSet());

        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getBalance(), transactions);
    }
}
