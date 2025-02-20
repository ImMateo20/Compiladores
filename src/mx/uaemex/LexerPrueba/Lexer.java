package mx.uaemex.LexerPrueba;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static void start() {
        String linea = "int entero = 5;";

        Pattern regex = Pattern.compile("(?<reservadas>int|double)|(?<segundo>li)");
        Matcher matcher = regex.matcher(linea);

        while (matcher.find()) {
            System.out.println("########################");
            System.out.println(matcher.group("reservadas"));
            System.out.println(matcher.group("segundo"));
            System.out.println("########################");
        }
    }
}
