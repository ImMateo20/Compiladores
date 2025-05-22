package appLexer.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorLexicoException.class)
    public ResponseEntity<ErrorValidationResponse> handleValidationException(ErrorLexicoException ex) {
        return new ResponseEntity<>(new ErrorValidationResponse(LocalDateTime.now(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}