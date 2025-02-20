package mx.uaemex.ExpresionesRegulares;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexx {

    public static void start() {
        // Expresion regular para poder validar una cadena de a-z
        String cadena = "ffFFGGggsdS3232434";
        String regexCadena = "[a-zA-Z0-9]+";
        boolean matchesCadena = cadena.matches(regexCadena);
        System.out.println("matches: " + matchesCadena);

        // Expresion regular para poder validar una fecha
        String fecha = "20-12-1900";
        String regexFecha = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19[0-9]{2}|20[01][0-9]|202[0-5])";
        boolean matchesFecha = fecha.matches(regexFecha);
        System.out.println("matches fecha: " + matchesFecha);

        // Expresion regular para validar un correo electronico
        String correo = "matthew.fco.14 @gmail.com";
        String regexCorreo = "[a-z._ñ0-9]+\s@[a-z.]+";
        boolean matchesCorreo = correo.matches(regexCorreo);
        System.out.println("matches correo: " + matchesCorreo);

        // Expresiones en numeros grandes
        String prueba = "20500";
        String regexPrueba = "0[0-9]{4}||20[0-4][0-9]{2}|20500";
        boolean matchesPrueba = prueba.matches(regexPrueba);
        System.out.println("matches prueba: " + matchesPrueba);

        // 
        Pattern regexPattern = Pattern.compile("(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19[0-9]{2}|20[01][0-9]|202[0-5])");
        Matcher matcher = regexPattern.matcher(fecha);
        System.out.println(matcher.matches());
    }
}
