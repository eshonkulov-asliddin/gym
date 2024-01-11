package dev.gym.service.exception;

import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now().toString());
        return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<Object> handleInvalidUsernameOrPasswordException(InvalidUsernameOrPasswordException ex) {
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now().toString());
        return new ResponseEntity<>(exceptionPayload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Object> handleNoResultException(NoResultException ex) {
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now().toString());
        return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
    }
}
