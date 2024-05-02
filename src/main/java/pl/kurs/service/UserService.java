package pl.kurs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kurs.dto.UserDto;
import pl.kurs.model.User;
import pl.kurs.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kurs.repository.TransactionRepository;
import pl.kurs.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
    }

    public Map<UserDto, Integer> getUsersWithTransactionsCount(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<Object[]> transactionsCount = transactionRepository.countTransactionsByUser();

        Map<Long, Integer> transactionCountMap = transactionsCount.stream()
                .collect(Collectors.toMap(
                        entry -> (Long) entry[0],
                        entry -> ((Long) entry[1]).intValue()
                ));

        Map<UserDto, Integer> map = new HashMap<>();
        usersPage.forEach(user -> map.put(
                UserDto.from(user),
                transactionCountMap.getOrDefault(user.getId(), 0)
        ));

        return map;
    }
}
