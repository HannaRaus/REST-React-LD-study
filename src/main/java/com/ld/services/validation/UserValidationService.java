package com.ld.services.validation;

import com.ld.services.UserService;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidateUserRequest;
import com.ld.validation.ValidationError;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserService service;

    public ValidateResponse validate(@NonNull ValidateUserRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        String name = request.getName();
        String phone = request.getPhone();
        String password = request.getPassword();

        if (service.isRegistered(phone)) {
            log.error("UserValidationService.validate - User with phone:'{}' is already registered", phone);
            errors.add(ValidationError.USER_ALREADY_REGISTERED);
        }
        if (name.length() < 3 || name.length() > 50) {
            log.error("UserValidationService.validate - name:'{}' must be in range of 3-50 symbols", name);
            errors.add(ValidationError.WRONG_USER_NAME_FORMAT);
        }
        if (password.length() < 5 || password.length() > 50) {
            log.error("UserValidationService.validate - password:'{}' must be in range of 5-50 symbols", password);
            errors.add(ValidationError.WRONG_PASSWORD_FORMAT);
        }
        if (!isCorrectPhoneNumber(phone)) {
            log.error("UserValidationService.validate - phone:'{}' Incorrect phone number", phone);
            errors.add(ValidationError.INCORRECT_PHONE_NUMBER);
        }

        return new ValidateResponse(errors.isEmpty(), errors);
    }

    private boolean isCorrectPhoneNumber(String phone) {
        Matcher matcher = Pattern.compile("^\\+?3?8?(0(67|68|96|97|98|63|93)\\d{7})$").matcher(phone);
        return matcher.matches();
    }

}
