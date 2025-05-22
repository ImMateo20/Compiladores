package appCompilador.controllers;

// import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import appCompilador.domains.ResultadoPaginacion;
import appCompilador.domains.ResultadoParser;
// import appCompilador.domains.Token;
import appCompilador.exception.ErrorCompiladorException;
import appCompilador.services.CompiladorService;
import feign.FeignException;

@RestController
@CrossOrigin(value = "*", origins = "*")
public class CompiladorController {
    private final CompiladorService compiladorService;

    public CompiladorController(CompiladorService compiladorService) {
        this.compiladorService = compiladorService;
    }

    @PostMapping(path = "/recorridos")
    public ResultadoParser generarParserService(@RequestBody String cadena) {
        try {
            return this.compiladorService.generarParserService(cadena);
        } catch (FeignException.BadRequest e) {
            String mensaje = e.contentUTF8();

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(mensaje);
                String soloMensaje = root.path("mensaje").asText();
                throw new ErrorCompiladorException(soloMensaje);
            } catch (JsonProcessingException parserEx) {
                throw new ErrorCompiladorException("Error al interpretar el mensaje de error");
            }

        } catch (Exception e) {
            throw new ErrorCompiladorException("Error en la creacion del parser: " + e.getMessage());
        }
    }

    @PostMapping(path = "/listar-tokens")
    public ResultadoPaginacion listaTokensController(@RequestParam("cadena") String texto, @RequestParam int indice) {
        System.out.println(texto);
        try {
            return this.compiladorService.paginacionService(texto, indice);
        } catch (FeignException.BadRequest e) {
            String mensaje = e.contentUTF8();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(mensaje);
                String soloMensaje = root.path("mensaje").asText();
                throw new ErrorCompiladorException(soloMensaje);
            } catch (JsonProcessingException parserEx) {
                throw new ErrorCompiladorException("Error al interpretar el mensaje de error");
            }
        } catch (Exception e) {
            throw new ErrorCompiladorException("Error al intentar listar tokens: " + e.getMessage());
        }
    }

}
