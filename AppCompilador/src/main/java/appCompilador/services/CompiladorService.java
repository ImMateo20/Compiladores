package appCompilador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import appCompilador.client.AppLexerClient;
import appCompilador.client.AppParserClient;
import appCompilador.client.AppSemanticaClient;
import appCompilador.domains.Arbol;
import appCompilador.domains.Compilador;
import appCompilador.domains.DVariable;
import appCompilador.domains.ResultadoLexico;
import appCompilador.domains.ResultadoPaginacion;
import appCompilador.domains.ResultadoParser;
import appCompilador.domains.ResultadoSemantico;
import appCompilador.domains.Token;
import appCompilador.exception.ErrorCompiladorException;
import feign.FeignException;

@Service
public class CompiladorService {
    private final AppParserClient appParserClient;
    private final AppLexerClient appLexerClient;
    private final AppSemanticaClient appSemanticaClient;

    public CompiladorService(AppParserClient appParserClient, AppLexerClient appLexerClient,
            AppSemanticaClient appSemanticaClient) {
        this.appParserClient = appParserClient;
        this.appLexerClient = appLexerClient;
        this.appSemanticaClient = appSemanticaClient;
    }

    public ResultadoParser generarParserService(String cadena) {
        // List<Token> tokens = this.appLexerClient.listaTokensClient(cadena);
        ResultadoLexico resultadoLexico = this.appLexerClient.listaTokensClient(cadena);
        Compilador compilador = new Compilador(resultadoLexico.tokens());
        compilador.clase.metodo.variables.forEach(v -> {
            try {
                // v.arbol = this.appParserClient.obtenerRecorridosClient(v.expresion).get(1);
                // v.arbol = this.appParserClient.obtenerArbolClient(v.expresion);
                Arbol arbol = this.appParserClient.obtenerRecorridosClient(v.expresion);
                v.arbol = arbol;

            } catch (FeignException.BadRequest e) {
                String mensaje = e.contentUTF8();
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(mensaje);
                    String soloMensaje = root.path("mensaje").asText();
                    System.out.println("EL ARBOL VACIO ESTA AQUI");
                    System.out.println(v.arbol);
                    // v.arbol = soloMensaje;
                    resultadoLexico.errores().add(soloMensaje);
                    // throw new ErrorCompiladorException(soloMensaje);
                } catch (JsonProcessingException parserEx) {
                    throw new ErrorCompiladorException("Error al interpretar el mensaje de error");
                }

            } catch (Exception e) {
                throw new ErrorCompiladorException("Error en la creacion del parser: " + e.getMessage());
            }

        });

        List<DVariable> variables = new ArrayList<>(compilador.clase.metodo.variables);
        ResultadoSemantico resultadoSemantico = this.appSemanticaClient.analizarSemanticaClient(variables);

        // System.out.println(compilador.toString());
        return new ResultadoParser(compilador.toString(), resultadoLexico.errores(), resultadoSemantico);
    }

    public ResultadoPaginacion paginacionService(String texto, int indice) {
        // List<Token> tokens = this.appLexerClient.listaTokensClient(texto);
        ResultadoLexico resultadoLexico = this.appLexerClient.listaTokensClient(texto);
        PageRequest pageRequest = PageRequest.of(indice, 10);
        Page<Token> pagina = paginarTokens(resultadoLexico.tokens(), pageRequest);
        return new ResultadoPaginacion(pagina, resultadoLexico.errores());
    }

    private Page<Token> paginarTokens(List<Token> lista, Pageable pageable) {
        int inicio = (int) pageable.getOffset();
        int fin = Math.min(inicio + pageable.getPageSize(), lista.size());

        if (inicio >= lista.size()) {
            return Page.empty(pageable);
        }

        List<Token> subLista = lista.subList(inicio, fin);

        return new PageImpl<>(subLista, pageable, lista.size());
    }

}
