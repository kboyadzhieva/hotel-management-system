package com.moonlighthotel.hotelmanagementsystem.exception.handler;

import com.moonlighthotel.hotelmanagementsystem.exception.AuthenticationFailException;
import com.moonlighthotel.hotelmanagementsystem.exception.DuplicateRecordException;
import com.moonlighthotel.hotelmanagementsystem.exception.InvalidRequestException;
import com.moonlighthotel.hotelmanagementsystem.exception.RecordNotFoundException;
import com.moonlighthotel.hotelmanagementsystem.exception.model.*;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders httpHeaders, HttpStatus httpStatus,
                                                                  WebRequest webRequest) {
        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        ArrayList<FieldError> fieldErrors = new ArrayList<>(e.getFieldErrors());
        String key = "";

        for (FieldError fieldError : fieldErrors) {
            if (!fieldError.getField().equalsIgnoreCase(key)) {
                key = fieldError.getField();
                messages = e.getFieldErrors(key)
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toSet());
                errors.put(key, messages);
            }
        }

        ValidationFailErrorModel errorModel = ValidationFailErrorModel.builder()
                .message("Some fields are filled in incorrectly. Please check and try again.")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailException.class)
    private ResponseEntity<AuthenticationFailErrorModel> handleAuthenticationFailException(AuthenticationFailException e) {
        Set<String> messages = new HashSet<>();
        Map<String, Set<String>> errors = new HashMap<>();

        messages.add(e.getMessage());
        errors.put(e.getField(), messages);

        AuthenticationFailErrorModel errorModel = AuthenticationFailErrorModel.builder()
                .message("Invalid Authentication")
                .errors(errors)
                .build();
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    private ResponseEntity<DuplicateRecordErrorModel> handleDuplicateRecordException(DuplicateRecordException e) {
        Set<String> messages = new HashSet<>();
        Map<String, Set<String>> errors = new HashMap<>();

        messages.add(e.getMessage());
        errors.put(e.getField(), messages);

        DuplicateRecordErrorModel errorModel = DuplicateRecordErrorModel.builder()
                .message("Duplicate record found")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    private ResponseEntity<RecordNotFoundErrorModel> handleRecordNotFoundException(RecordNotFoundException e) {
        RecordNotFoundErrorModel errorModel = RecordNotFoundErrorModel.builder()
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    private ResponseEntity<InvalidRequestErrorModel> handleInvalidRequestException(InvalidRequestException e) {
        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(e.getMessage());
        errors.put(e.getRequestField(), messages);

        InvalidRequestErrorModel errorModel = InvalidRequestErrorModel.builder()
                .message("Invalid Request")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {

        String message = String.format("Required request parameter '%s' is missing.", e.getParameterName());

        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(message);
        errors.put(e.getParameterName(), messages);

        InvalidRequestErrorModel error = InvalidRequestErrorModel.builder()
                .message("Invalid Request")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
