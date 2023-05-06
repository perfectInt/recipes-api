package io.sultanov.recipes.config;

import io.sultanov.recipes.exceptions.ObjectAlreadyExistsException;
import io.sultanov.recipes.exceptions.ObjectNotFoundException;
import io.sultanov.recipes.exceptions.PasswordException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(ObjectNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(value = {ObjectAlreadyExistsException.class})
    public ResponseEntity<Object> handleAlreadyExists(ObjectAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(value = {PasswordException.class})
    public ResponseEntity<Object> handlePasswordException(PasswordException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }
}
