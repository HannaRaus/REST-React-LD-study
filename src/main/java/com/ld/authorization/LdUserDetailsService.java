package com.ld.authorization;

import com.ld.model.User;
import com.ld.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class LdUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public LdUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with phone number %s doesn't exists", phone)));
        return new UserPrincipal(user);
    }
}
