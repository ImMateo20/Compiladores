package mx.uaemex.ExpresionesRegulares;

public class SplitFor {
    // lineas.flatMap(s -> Stream.of(s.trim().split("
    // "))).forEach(System.out::println);
    // String cadena = "Me gusta mucho mi carrera de Ingenieria en Sistemas
    // Inteligentes";

    // String[] tokens = cadena.split(" ");

    // Arrays.stream(tokens)
    // .peek(System.out::println)
    // .map(token -> token.toLowerCase())
    // .forEach(System.out::println);

    // Pattern pattern = Pattern.compile(" ");
    // String []tokenss = pattern.split(cadena);
    /*
     * for (int i = 0; i < tokens.length; i++) {
     * System.out.println("tokens #"+(i+1)+": "+tokens[i]);
     * }
     * 
     * for (String string : tokens) {
     * System.out.println(string);
     * }
     */

    // File file = new File("C:\\Users\\HOLA\\Desktop\\New.txt");

    // Path ruta = Path.of("C:\\Users\\HOLA\\Desktop\\New.txt");

    /*
     * String[] cadena2 = Files.readString(ruta).split(" ");
     * Arrays.stream(cadena2).forEach(System.out::println);
     */

    // List<String> tokens = Files.readAllLines(ruta);

    // Stream<String> lineas = Files.lines(ruta);

    // lineas.forEach(System.out::println);

    // lineas.close();

    /*
     * try (Stream<String> lineas = Files.lines(ruta)) {
     * // List<String> linea = lineas.toList();
     * // for (String palabras : linea) {
     * // String[] palabra = palabras.split(" ");
     * // Arrays.stream(palabra).forEach(System.out::println);
     * // }
     * lineas.flatMap(s -> Stream.of(s.split(" "))).forEach(System.out::println);
     * }
     */

    // tokens.stream().forEach(System.out::println);//pasar una lista a un string

    // for (int i = 0; i < tokens.size(); i++) {
    // System.out.println(tokens.get(i));
    // }

    // tokens.forEach(System.out::println);

    // for (String linea : tokens) {
    // String[] palabras = linea.split(" ");
    // Arrays.stream(palabras).forEach(System.out::println);
    // }

}
