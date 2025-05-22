package appCompilador.exception;

import java.time.LocalDateTime;

public record ErrorCompiladorResponse(LocalDateTime timestamp, String mensaje) {

}