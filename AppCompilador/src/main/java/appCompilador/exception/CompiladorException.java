package appCompilador.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CompiladorException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorCompiladorException.class)
    public ResponseEntity<ErrorCompiladorResponse> handleErrorCompiladorException(ErrorCompiladorException ex) {
        return new ResponseEntity<>(new ErrorCompiladorResponse(LocalDateTime.now(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
