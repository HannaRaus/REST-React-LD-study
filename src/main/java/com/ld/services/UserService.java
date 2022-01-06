package com.ld.services;

import com.ld.exceptions.UserAlreadyExistsException;
import com.ld.model.User;
import com.ld.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService extends CrudService<User> {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public void save(User user) {
        if (isRegistered(user.getPhone())) {
            throw new UserAlreadyExistsException(
                    String.format("User with specified phone number %s already exists", user.getPhone()));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        super.save(user);
    }

    public User findByPhone(String phone) {
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with phone [%s] doesn't exist", phone));
        }
        return user.get();
    }

    public boolean isRegistered(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }
}
