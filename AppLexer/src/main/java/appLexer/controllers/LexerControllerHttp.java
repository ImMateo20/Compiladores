package appLexer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appLexer.domains.ResultadoLexico;
import appLexer.domains.Token;
import appLexer.services.TokenService;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/lexer")
public class LexerControllerHttp {
    private final TokenService tokenService;

    public LexerControllerHttp() {
        this.tokenService = new TokenService();
    }

    @PostMapping(path = "/lista")
    public ResultadoLexico metodoLista(@RequestBody String texto) {
        System.out.println(texto);
        List<Token> tokens = this.tokenService.obtenerTokens(texto);
        List<String> errrores = this.tokenService.obtenerErroresRegistrados();
        return new ResultadoLexico(tokens, errrores);
    }

}
