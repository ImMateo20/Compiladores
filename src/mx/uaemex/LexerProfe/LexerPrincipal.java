package mx.uaemex.LexerProfe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerPrincipal {

    public static void start() throws IOException {

        Path ruta = Path.of("C:\\Users\\HOLA\\Desktop\\Asig\\Compiladoress\\New.txt");

        List<String> lineas = Files.readAllLines(ruta);
        List<TokenP> tokens = new ArrayList<>();

        Pattern patron = Pattern.compile("" +
                "(?<reservada>public|class|static|void|if|else|while|byte|short|char)|"
                + "(?<operador>[+\\-/*=<>])|" + "(?<delimitador>[{}\\[\\]();])|" + "(?<literal>(-?[0-9])+(\\.[0-9]+)?)|"
                + "(?<identificador>[a-zA-Z][a-zA-Z0-9]+)");

        for (String linea : lineas) {
            Matcher matcher = patron.matcher(linea);
            while (matcher.find()) {
                if (matcher.group("reservada") != null)
                    tokens.add(new TokenP(matcher.group("reservada"), "reservada"));
                if (matcher.group("delimitador") != null)
                    tokens.add(new TokenP(matcher.group("delimitador"), "delimitador"));
                if (matcher.group("literal") != null)
                    tokens.add(new TokenP(matcher.group("literal"), "literal"));
                if (matcher.group("operador") != null)
                    tokens.add(new TokenP(matcher.group("operador"), "operador"));
                if (matcher.group("identificador") != null)
                    tokens.add(new TokenP(matcher.group("identificador"), "identificador"));
            }
        }

        tokens.sort(Comparator.comparing(p -> p.getToken())); // Ordena por nombre de Token

        System.out.println("ORDENADO POR NOMBRE DE TOKEN");

        tokens.forEach(System.out::println); // Imprime
    }

}
