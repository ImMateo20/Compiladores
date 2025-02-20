package mx.uaemex.AutomataFinito;

public class AutomataFinito {
    public static void start() {

        String cadena = "aB";

        boolean validate = validaraB5Int(cadena);
        System.out.println(validate);

    }


    public static boolean validaraB5(String text) {
        int status = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            status = switch (status) {
                case 0 ->
                    (c == 'a') ? 1 : -1;
                case 1 ->
                    (c == 'B' || c == '5') ? 1 : -1;
                default -> -1;
            };
        }
        return status == 1;
    }

    public static boolean validaraB5Int(String text) {
        int status = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            status = switch (status) {
                case 0 ->
                    (c == 'a') ? 1 : -1;
                case 1 ->
                    (c == 'B' || c == '5') ? 2 : -1;
                default -> -1;
            };
        }
        return status == 1 || status == 2;
    }

    // Valida la expresión regular abc
    public static boolean validarABC(String text) {
        int status = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            status = switch (status) {
                case 0 -> (c != 'a') ? -1 : 1;
                case 1 -> (c != 'b') ? -1 : 2;
                case 2 -> (c != 'c') ? -1 : 3;
                default -> -1;
            };
        }
        return status == 3;
    }
}
