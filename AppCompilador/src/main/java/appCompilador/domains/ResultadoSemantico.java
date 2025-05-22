package appCompilador.domains;

import java.util.HashMap;
import java.util.List;

public record ResultadoSemantico(HashMap<String, Simbolo> mapa, List<ErrorSemantico> errores) {

}
