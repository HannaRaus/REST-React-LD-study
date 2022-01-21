package com.ld.services;

import com.ld.authorization.UserRole;
import com.ld.error_handling.exceptions.UserAlreadyExistsException;
import com.ld.error_handling.exceptions.UserNotFoundException;
import com.ld.model.User;
import com.ld.repositories.UserRepository;
import com.ld.validation.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService extends CrudService<User> {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    public void register(UserRequest request) {
        save(new User()
                .setName(request.getName())
                .setPhone(request.getPhone())
                .setPassword(request.getPassword())
                .setSendNotification(request.isSendNotifications())
                .setActive(true)
                .setUserRole(UserRole.ROLE_USER));
    }

    @Override
    public void save(User user) {
        log.info("UserService.save - Saving user '{}", user);
        if (isRegistered(user.getPhone())) {
            log.error("UserService.save - User with specified phone number '{}' already exists", user.getPhone());
            throw new UserAlreadyExistsException(
                    String.format("User with specified phone number %s already exists", user.getPhone()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        super.save(user);
    }

    public User findByPhone(String phone) {
        log.info("UserService.findByPhone - Searching user by phone '{}", phone);
        return userRepository.findByPhone(phone).orElseThrow(() ->
                new UserNotFoundException(String.format("User with phone [%s] doesn't exist", phone)));
    }

    public User findByName(String name) {
        log.info("UserService.findByName - Searching user by name '{}", name);
        return userRepository.findByName(name).orElseThrow(() ->
                new UserNotFoundException(String.format("User with name [%s] doesn't exist", name)));
    }

    public boolean isRegistered(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }
}
