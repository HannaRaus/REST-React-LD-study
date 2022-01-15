package com.ld.services;

import com.ld.error_handling.exceptions.UserAlreadyExistsException;
import com.ld.error_handling.exceptions.UserNotFoundException;
import com.ld.model.User;
import com.ld.model.enums.UserRole;
import com.ld.repositories.UserRepository;
import com.ld.validation.UserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService target;

    @Test
    public void register_happyPath() {
        when(repository.findByPhone(any())).thenReturn(Optional.empty());
        UserRequest request = new UserRequest(
                "name", "+380631111111", "password", true);
        target.register(request);
        verify(repository, times(1)).findByPhone("+380631111111");
    }

    @Test
    public void register_whenUserWithPhoneAlreadyExists_shouldThrowUserAlreadyExistsException() {
        when(repository.findByPhone(any())).thenReturn(Optional.of(user()));
        UserRequest request = new UserRequest(
                "name", "+380631111111", "password", true);

        UserAlreadyExistsException result = assertThrows(UserAlreadyExistsException.class, () ->
                target.register(request));
        assertEquals("User with specified phone number +380631111111 already exists", result.getMessage());
        verify(repository, times(1)).findByPhone("+380631111111");
    }

    @Test
    public void save_happyPath() {
        when(repository.findByPhone(any())).thenReturn(Optional.empty());
        target.save(user());
        verify(repository, times(1)).findByPhone("+380631111111");
    }

    @Test
    public void save_whenUserWithPhoneAlreadyExists_shouldThrowUserAlreadyExistsException() {
        when(repository.findByPhone(any())).thenReturn(Optional.of(user()));

        UserAlreadyExistsException result = assertThrows(UserAlreadyExistsException.class, () ->
                target.save(user()));
        assertEquals("User with specified phone number +380631111111 already exists", result.getMessage());
        verify(repository, times(1)).findByPhone("+380631111111");
    }

    @Test
    public void findByPhone_happyPath() {
        when(repository.findByPhone(any())).thenReturn(Optional.of(user()));

        User result = target.findByPhone("+380631111111");

        assertFalse(isNull(user()));
        assertEquals("+380631111111", result.getPhone());
    }

    @Test
    public void findByPhone_whenUserIsNotPresent_shouldThrowUserNotFoundException() {
        when(repository.findByPhone(any())).thenReturn(Optional.empty());

        UserNotFoundException result = assertThrows(UserNotFoundException.class, () ->
                target.findByPhone("+380631111111"));
        assertEquals("User with phone [+380631111111] doesn't exist", result.getMessage());
        verify(repository, times(1)).findByPhone("+380631111111");
    }

    @Test
    public void findByName_happyPath() {
        when(repository.findByName(any())).thenReturn(Optional.of(user()));

        User result = target.findByName("name");

        assertFalse(isNull(user()));
        assertEquals("name", result.getName());
    }

    @Test
    public void findByName_whenUserIsNotPresent_shouldThrowUserNotFoundException() {
        when(repository.findByName(any())).thenReturn(Optional.empty());

        UserNotFoundException result = assertThrows(UserNotFoundException.class, () ->
                target.findByName("name"));
        assertEquals("User with name [name] doesn't exist", result.getMessage());
        verify(repository, times(1)).findByName("name");
    }

    @Test
    public void isRegistered_whenUserIsAlreadyRegistered_shouldReturnTrue() {
        when(repository.findByPhone(any())).thenReturn(Optional.of(user()));

        boolean result = target.isRegistered("+380631111111");

        assertTrue(result);
    }

    @Test
    public void isRegistered_whenUserIsNotRegistered_shouldReturnFalse() {
        when(repository.findByPhone(any())).thenReturn(Optional.empty());

        boolean result = target.isRegistered("+380631111111");

        assertFalse(result);
    }

    private User user() {
        return User.builder()
                .name("name")
                .phone("+380631111111")
                .password("password")
                .isActive(true)
                .sendNotification(true)
                .userRole(UserRole.ROLE_USER)
                .favoriteLessons(null)
                .build();
    }
}