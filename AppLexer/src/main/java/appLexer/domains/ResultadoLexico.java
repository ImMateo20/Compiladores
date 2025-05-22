package appLexer.domains;

import java.util.List;

public record ResultadoLexico(List<Token> tokens, List<String> errores) {
}
