package mx.uaemex.LexerYO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LexerYO {

    public static void start() throws IOException {

        Token[] tokens = {
                new Token("reservada", List.of(
                        "public",
                        "class",
                        "static",
                        "void",
                        "if",
                        "else",
                        "while",
                        "byte",
                        "short",
                        "char")),
                new Token("operador", List.of(
                        "+",
                        "-",
                        "/",
                        "*",
                        "=",
                        "<",
                        ">")),
                new Token("delimitador", List.of(
                        "{",
                        "}",
                        "[",
                        "]",
                        "(",
                        ")",
                        ";",
                        "]")),
                new Token("Literal", List.of("10", "5", "hola")),
                new Token("identificador",
                        List.of("Principal", "main", "String", "args", "int", "entero", "cadena", "suma"))
        };

        Path ruta = Path.of("C:\\Users\\HOLA\\Desktop\\Asig\\Compiladoress\\New.txt");
        try (Stream<String> lineasStream = Files.lines(ruta)) {
            List<String> lineas = lineasStream.toList();
            for (String linea : lineas) {
                String[] palabras = linea.trim().split(" ");
                Arrays.stream(palabras).forEach(palabra -> {
                    for (Token token : tokens) {
                        if (token.contenido.contains(palabra)) {
                            System.out.println(token.nombreToken + ": " + palabra);
                            break;
                        }
                    }
                });
            }
        }

    }
}