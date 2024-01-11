package dev.gym.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionPayload {
    private final String message;
    private final HttpStatus httpStatus;
    private final String timestamp;
}
