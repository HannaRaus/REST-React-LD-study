package com.ld.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private boolean success = true;
    private String errorCode = "";
    private String errorMessage = "";
    private List<ValidationError> validationErrors = new ArrayList<>();
    private Map<String, Object> results = new TreeMap<>();

    public static Response ok() {
        return new Response();
    }

    public static Response error(String errorMessage) {
        Response response = new Response();
        response.success = false;
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static Response error(HttpStatus errorCode, String errorMessage) {
        Response response = new Response();
        response.success = false;
        response.setErrorCode(errorCode.toString());
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static Response result(String key, Object value) {
        Response response = new Response();
        response.getResults().put(key, value);
        return response;
    }

    public static Response result(String key1, Object value1, String key2, Object value2) {
        Response response = new Response();
        response.getResults().put(key1, value1);
        response.getResults().put(key2, value2);
        return response;
    }

    public static Response result(Map<String, Object> results) {
        Response response = new Response();
        response.setResults(results);
        return response;
    }

    public static Response validationErrors(List<ValidationError> validationErrors) {
        Response response = new Response();
        response.success = validationErrors.isEmpty();
        response.setValidationErrors(validationErrors);
        return response;
    }
}
