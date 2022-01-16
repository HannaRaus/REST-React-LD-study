package com.ld.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ld.services.UserService;
import com.ld.services.validation.UserValidationService;
import com.ld.validation.UserRequest;
import com.ld.validation.ValidateResponse;
import com.ld.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserValidationService validationService;

    @InjectMocks
    private UserController target;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
    }

    @Test
    public void registration_happyPath() throws Exception {
        registration_performAndVerify_whenAllDataIsOk(
                new UserRequest("name", "+380631111111", "password", true),
                new ValidateResponse(true, Collections.emptyList()));
    }


    @Test
    public void registration_whenPhoneIsCorrect_happyPath() throws Exception {
        registration_performAndVerify_whenAllDataIsOk(
                new UserRequest("name", "80631111111", "password", true),
                new ValidateResponse(true, Collections.emptyList()));
        registration_performAndVerify_whenAllDataIsOk(
                new UserRequest("name", "0631111111", "password", true),
                new ValidateResponse(true, Collections.emptyList()));
        registration_performAndVerify_whenAllDataIsOk(
                new UserRequest("name", "0971111111", "password", true),
                new ValidateResponse(true, Collections.emptyList()));
        registration_performAndVerify_whenAllDataIsOk(
                new UserRequest("name", "0931111111", "password", true),
                new ValidateResponse(true, Collections.emptyList()));

    }

    @Test
    public void registration_whenNameIsInvalid_shouldReturnSingleError() throws Exception {
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("na", "+380631111111", "password", true),
                new ValidateResponse(false, List.of(ValidationError.WRONG_USER_NAME_FORMAT)));
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
                        "+380631111111", "password", true),
                new ValidateResponse(false, List.of(ValidationError.WRONG_USER_NAME_FORMAT)));
    }

    @Test
    public void registration_whenPhoneIsInvalid_shouldReturnSingleError() throws Exception {
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("name", "+38063111 11 11", "password", true),
                new ValidateResponse(false, List.of(ValidationError.INCORRECT_PHONE_NUMBER)));
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("name", "0991111111", "password", true),
                new ValidateResponse(false, List.of(ValidationError.INCORRECT_PHONE_NUMBER)));
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("name", "099fgjiiii", "password", true),
                new ValidateResponse(false, List.of(ValidationError.INCORRECT_PHONE_NUMBER)));
    }

    @Test
    public void registration_whenPasswordIsInvalid_shouldReturnSingleError() throws Exception {
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("name", "+380631111111", "short", true),
                new ValidateResponse(false, List.of(ValidationError.WRONG_PASSWORD_FORMAT)));
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("name", "+380631111111",
                        "looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong",
                        true),
                new ValidateResponse(false, List.of(ValidationError.WRONG_PASSWORD_FORMAT)));
    }

    @Test
    public void registration_whenUserIsRegistered_shouldReturnSingleError() throws Exception {
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("name", "+380631111111", "password", true),
                new ValidateResponse(false, List.of(ValidationError.USER_ALREADY_REGISTERED)));
    }

    @Test
    public void registration_whenAllFieldsAreInvalid_shouldReturnListOfErrors() throws Exception {
        registration_performAndVerify_whenSomeDataIsIncorrect(
                new UserRequest("na", "+380991111111", "pass", true),
                new ValidateResponse(false, List.of(
                        ValidationError.USER_ALREADY_REGISTERED,
                        ValidationError.WRONG_USER_NAME_FORMAT,
                        ValidationError.WRONG_PASSWORD_FORMAT,
                        ValidationError.INCORRECT_PHONE_NUMBER)));
    }

    private void registration_performAndVerify_whenAllDataIsOk(UserRequest request, ValidateResponse response) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/registration")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
        verify(userService, times(1)).register(request);
    }

    private void registration_performAndVerify_whenSomeDataIsIncorrect(UserRequest request, ValidateResponse response) throws Exception {
        when(validationService.validate(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/registration")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(validationService, times(1)).validate(request);
        verify(userService, times(0)).register(request);
    }

}