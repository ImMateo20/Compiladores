package mx.uaemex.LexerYO;

import java.util.List;

public class Token {
    String nombreToken;
    List<String> contenido;

    public Token(String nombre, List<String> contenido) {
        this.nombreToken = nombre;
        this.contenido = contenido;
    }
}
