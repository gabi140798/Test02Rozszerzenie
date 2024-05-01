package pl.kurs.dto;

import pl.kurs.model.Transaction;
import pl.kurs.model.User;

import java.util.Set;

public record UserDto(Long id, String name, String surname, double balance, Set<Transaction> numerOfTransfers) {

    public static UserDto from (User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getBalance(), user.getTransactions());
    }
}
