package appParser.services;

import java.util.List;

import appParser.domains.Arbol;
import appParser.domains.Token;
import appParser.exception.ErrorSintacticoException;
import org.springframework.stereotype.Service;

@Service
public class ArbolService {

    public Arbol obtenerTokens(List<Token> tokens) {
        List<String> lexemas = tokens.stream()
                .map(Token::lexema)
                .toList();

        if (!lexemas.contains("="))
            throw new ErrorSintacticoException("La expresion no tiene operador de asignacion");

        Arbol arbol = new Arbol();
        arbol.agrega(tokens);

        // System.out.println(arbol.obtenerRPre(arbol.getRaiz()));

        // return arbol.obtenerRecorridos();
        return arbol;
    }

}