package appLexer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import appLexer.domains.Token;
import appLexer.exception.ErrorLexicoException;

@Service
public class TokenService {
    private List<String> errores;

    public List<Token> obtenerTokens(String cadena) {

        // System.out.println(cadena);
        List<Token> tokens = new ArrayList<>();
        errores = new ArrayList<>();
        Matcher matcher = patron().matcher(cadena.trim());
        while (matcher.find()) {

            try {
                if (matcher.group("reservada") != null)
                    tokens.add(new Token(matcher.group("reservada"), "reservada"));
                if (matcher.group("cadena") != null)
                    tokens.add(new Token(matcher.group("cadena"), "cadena"));
                if (matcher.group("literal") != null)
                    tokens.add(new Token(matcher.group("literal"), "literal"));
                if (matcher.group("operador") != null)
                    tokens.add(new Token(matcher.group("operador"), "operador"));
                if (matcher.group("delimitador") != null)
                    tokens.add(new Token(matcher.group("delimitador"), "delimitador"));
                if (matcher.group("identificador") != null)
                    tokens.add(new Token(matcher.group("identificador"), "identificador"));
                if (matcher.group("invalido") != null)
                    throw new ErrorLexicoException("El token " + matcher.group("invalido") + " es invalido");

            } catch (Exception e) {
                errores.add(e.getMessage());
                while (matcher.find()) {
                    if (matcher.group("delimitador") != null) {
                        tokens.add(new Token(matcher.group("delimitador"), "delimitador"));
                        break;
                    }
                }
            }
        }

        // System.out.println("Aqui entro solamente esta vez: " + e.getMessage());
        // tokens.sort(Comparator.comparing(p -> p.getToken())); // Ordena por nombre de
        // Token
        // System.out.println("ORDENADO POR NOMBRE DE TOKEN");
        // tokens.forEach(System.out::println); // Imprime

        return tokens;

    }

    public List<String> obtenerErroresRegistrados() {
        return errores;
    }

    private Pattern patron() {
        return Pattern.compile(""
                + "(?<reservada>public|class|static|void|if|else|while|byte|short|char|int|float|long|double|boolean|String)|"
                + "(?<cadena>\"(\\\\.|[^\"\\\\])*?\")|" // <-- este es nuevo
                + "(?<literal>-?[0-9]+(\\.?[0-9]*))|"
                + "(?<operador>[*+\\-=/<>]+)|"
                + "(?<delimitador>[{}()\\[\\];])|"
                + "(?<identificador>\\b[a-zA-Z_][a-zA-Z0-9_]*\\b)|"
                + "(?<invalido>[^\\sA-Za-z0-9_{}()\\[\\];*+/\\-=<>\"\\\\]+)");
    }

}

// if (flag == 0) {
// if (matcher.group("reservada") != null)
// tokens.add(new Token(matcher.group("reservada"), "reservada"));
// if (matcher.group("literal") != null)
// tokens.add(new Token(matcher.group("literal"), "literal"));
// if (matcher.group("operador") != null)
// tokens.add(new Token(matcher.group("operador"), "operador"));
// if (matcher.group("delimitador") != null)
// tokens.add(new Token(matcher.group("delimitador"), "delimitador"));
// if (matcher.group("identificador") != null)
// tokens.add(new Token(matcher.group("identificador"), "identificador"));
// if (matcher.group("invalido") != null) {
// errores.add("El token " + matcher.group("invalido") + " es invalido");
// flag = 1;
// }
// // throw new ErrorLexicoException("El token " + matcher.group("invalido") + "
// es
// // invalido");
// } else {

// if (matcher.group("delimitador") != null) {
// tokens.add(new Token(matcher.group("delimitador"), "delimitador"));
// flag = 0;
// }
// }