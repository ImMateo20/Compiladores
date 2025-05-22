package appCompilador.client;

import java.util.List;

// import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import appCompilador.domains.Arbol;
import appCompilador.domains.Token;

// import appCompilador.domains.Arbol;

// @FeignClient(url = "http://localhost:8082", name = "app-parser")
@FeignClient(name = "app-parser")
@Component
public interface AppParserClient {
    @GetMapping(path = "/helloworld")
    String obtenerHolaMundo();

    @PostMapping(path = "/recorridos")
    Arbol obtenerRecorridosClient(@RequestBody List<Token> expresion);
}
