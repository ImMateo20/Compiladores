package appParser.controllers;

import java.util.List;

// import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import appParser.domains.Arbol;
import appParser.domains.Token;
// import appParser.domains.Arbol;
import appParser.services.ArbolService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(value = "*", origins = "*")
public class ParserControllerHttp {
    private ArbolService arbolService;

    public ParserControllerHttp() {
        arbolService = new ArbolService();
    }

    @PostMapping("/recorridos")
    public Arbol obtenerRecorridos(@RequestBody List<Token> expresion) {
        // public List<String> obtenerRecorridos(@RequestBody String expresion) {
        // System.out.println(expresion);
        return this.arbolService.obtenerTokens(expresion);
    }

}
