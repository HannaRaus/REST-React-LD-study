package com.ld.configuration;

import com.ld.enums.UserRole;
import com.ld.model.User;
import com.ld.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppReadyToUseListener {

    private final UserService userService;

    @Value("${default.admin.name}")
    private String defaultAdminName;

    @Value("${default.admin.phone}")
    private String defaultAdminPhone;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {
        try {
            userService.findByPhone(defaultAdminPhone);
        } catch (UsernameNotFoundException ex) {
            log.info("AppReadyToUseListener.appReady - '{}", ex.getMessage());
            addDefaultAdminUser();
        }
    }

    private void addDefaultAdminUser() {
        User admin = new User();
        admin.setName(defaultAdminName);
        admin.setPhone(defaultAdminPhone);
        admin.setPassword(defaultAdminPassword);
        admin.setActive(true);
        admin.setSendNotification(false);
        admin.setUserRole(UserRole.ROLE_MASTER);

        userService.save(admin);
    }
}