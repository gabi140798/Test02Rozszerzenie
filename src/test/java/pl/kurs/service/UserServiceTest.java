package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.kurs.dto.UserDto;
import pl.kurs.exceptions.UserNotFoundException;
import pl.kurs.model.User;
import pl.kurs.repository.TransactionRepository;
import pl.kurs.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findUserById_UserExists() {
        Long userId = 1L;
        User expected = new User(userId,"Kamil","Nowak",500);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expected));

        User result = userService.findUserById(userId);

        assertEquals(expected, result);
        verify(userRepository).findById(userId);
    }

    @Test
    void findUserById_UserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void getUsersWithTransactionsCount_ShouldReturnCorrectData() {
        User user1 = new User(1L, "Jan", "Kowalski", 100.0);
        User user2 = new User(2L, "Anna", "Nowak", 150.0);
        List<User> users = Arrays.asList(user1, user2);
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(users, pageable, users.size()));

        List<Object[]> transactionCounts = Arrays.asList(
                new Object[] {1L, 5L},
                new Object[] {2L, 3L}
        );
        when(transactionRepository.countTransactionsByUser()).thenReturn(transactionCounts);

        Map<UserDto, Integer> result = userService.getUsersWithTransactionsCount(pageable);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5, result.get(UserDto.from(user1)).intValue());
        assertEquals(3, result.get(UserDto.from(user2)).intValue());

        verify(userRepository).findAll(pageable);
        verify(transactionRepository).countTransactionsByUser();
    }
}