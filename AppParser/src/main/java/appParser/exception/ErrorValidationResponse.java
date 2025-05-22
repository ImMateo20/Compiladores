package appParser.exception;

import java.time.LocalDateTime;

public record ErrorValidationResponse(LocalDateTime timestamp, String mensaje) {

}
