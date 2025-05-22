package appSemantica.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import appSemantica.domains.DVariable;
import appSemantica.domains.ErrorSemantico;
import appSemantica.domains.ResultadoSemantico;
import appSemantica.domains.Simbolo;
import appSemantica.services.SemanticaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(value = "*", origins = "*")
public class SemanticaControllerHttp {
    private final SemanticaService semanticaService;

    public SemanticaControllerHttp(SemanticaService semanticaService) {
        this.semanticaService = semanticaService;
    }

    @PostMapping(path = "/semantica")
    public ResultadoSemantico analizarSemanticaController(@RequestBody List<DVariable> variables) {

        HashMap<String, Simbolo> mapa = this.semanticaService.analizarSemanticaService(variables);

        List<ErrorSemantico> erroresG = this.semanticaService.getErroresGenerales();

        for (ErrorSemantico errorSemantico : erroresG) {
            System.out.println("////////////////////GENERALES///////////////////");
            System.out.println(errorSemantico.expresion());
            System.out.println(errorSemantico.error());
        }

        mapa.forEach((clave, valor) -> {
            System.out.println("///////////////INDIVIDUALES///////////////");
            System.out.println(valor.tipo);
            System.out.println(valor.nombre);
            System.out.println(valor.valor);
            System.out.println("Errores:");
            for (String error : valor.erroresSemanticos) {
                System.out.println(error);
            }
        });

        return new ResultadoSemantico(mapa, erroresG);
    }

}
