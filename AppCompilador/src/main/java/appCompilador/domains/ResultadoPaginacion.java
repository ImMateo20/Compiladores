package appCompilador.domains;

import java.util.List;

import org.springframework.data.domain.Page;

public record ResultadoPaginacion(Page<Token> tokens, List<String> errores) {
    
}
