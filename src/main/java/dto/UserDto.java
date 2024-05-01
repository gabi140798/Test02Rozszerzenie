package dto;

import model.Transaction;
import model.User;

import java.util.Set;

public record UserDto(Long id, String name, String surname, double balance, Set<Transaction> numerOfTransfers) {

    public static UserDto from (User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getBalance(), user.getTransactions());
    }
}
