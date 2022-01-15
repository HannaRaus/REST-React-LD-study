package com.ld.services.validation;

import com.ld.services.UserService;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidateUserRequest;
import com.ld.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserValidationService validationService;

    @Test
    public void validate_happyPath() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+380631111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenUserIsAlreadyExists_shouldReturnUserAlreadyExistsError() {
        when(userService.isRegistered(any())).thenReturn(true);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+380631111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.USER_ALREADY_REGISTERED));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenUsernameIncorrect_shouldReturnWrongUsernameFormat() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "us", "+380631111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_USER_NAME_FORMAT));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
                "380631111111", "password", false);

        response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_USER_NAME_FORMAT));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPasswordIncorrect_shouldReturnWrongPasswordFormat() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+380631111111", "pass", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_PASSWORD_FORMAT));
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "380631111111",
                "userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr", false);

        response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.WRONG_PASSWORD_FORMAT));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneWithoutPlus_happyPath() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "30631111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneWithoutCountryCode_happyPath() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "0631111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneContainsSpaces_happyPath() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+38 063 111 11 11", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneWithCorrectOperators_happyPath() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "0631111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "0671111111", "password", false);

        response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "0681111111", "password", false);

        response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "0961111111", "password", false);

        response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "0971111111", "password", false);

        response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "0981111111", "password", false);

        response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());

        request = new ValidateUserRequest(
                "user", "0931111111", "password", false);

        response = validationService.validate(request);
        assertTrue(response.isSuccess());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneIsRussian_shouldReturnIncorrectPhoneNumber() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+791 1111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.INCORRECT_PHONE_NUMBER));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneIsNotMobile_shouldReturnIncorrectPhoneNumber() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+380441111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.INCORRECT_PHONE_NUMBER));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneIncorrectOperator_shouldReturnIncorrectPhoneNumber() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+380991111111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.INCORRECT_PHONE_NUMBER));
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    public void validate_whenPhoneContainsSymbols_shouldReturnIncorrectPhoneNumber() {
        when(userService.isRegistered(any())).thenReturn(false);
        ValidateUserRequest request = new ValidateUserRequest(
                "user", "+3fcv$8063z11//11111", "password", false);

        ValidateResponse response = validationService.validate(request);
        assertFalse(response.isSuccess());
        assertFalse(response.getErrors().isEmpty());
        assertTrue(response.getErrors().remove(ValidationError.INCORRECT_PHONE_NUMBER));
        assertTrue(response.getErrors().isEmpty());
    }

}