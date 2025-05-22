package appCompilador.client;

// import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import appCompilador.domains.ResultadoLexico;
// import appCompilador.domains.Token;

// @FeignClient(url = "http://localhost:8081", name = "app-lexer")
@FeignClient(name = "app-lexer")
@Component
public interface AppLexerClient {

    @GetMapping(path = "/lexer/obtenerNombre")
    String metodoUno();

    @PostMapping(path = "/lexer/lista")
    ResultadoLexico listaTokensClient(@RequestBody String texto);

}
