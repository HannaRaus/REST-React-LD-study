package com.ld.services;

import com.ld.enums.UserRole;
import com.ld.exceptions.UserAlreadyExistsException;
import com.ld.model.User;
import com.ld.repositories.UserRepository;
import com.ld.validation.ValidateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public void register(ValidateUserRequest request) {
        save(User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .password(request.getPassword())
                .sendNotification(request.isSendNotifications())
                .isActive(true)
                .userRole(UserRole.ROLE_USER)
                .build());
    }

    @Override
    public void save(User user) {
        if (isRegistered(user.getPhone())) {
            log.error("UserService.save - User with specified phone number '{}' already exists", user.getPhone());
            throw new UserAlreadyExistsException(
                    String.format("User with specified phone number %s already exists", user.getPhone()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        super.save(user);
    }

    public User findByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) {
            log.error("UserService.save - User with phone '{}' doesn't exist", phone);
            throw new UsernameNotFoundException(String.format("User with phone [%s] doesn't exist", phone));
        }
        return user.get();
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            log.error("UserService.save - User with name '{}' doesn't exist", name);
            throw new UsernameNotFoundException(String.format("User with name [%s] doesn't exist", name));
        }
        return user.get();
    }

    public boolean isRegistered(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }

    public User getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByName(name);
    }
}
