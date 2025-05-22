package appCompilador.domains;

import java.util.List;

public record ResultadoParser(String compilador, List<String> errores, ResultadoSemantico semantico) {

}
