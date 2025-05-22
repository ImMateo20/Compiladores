package appCompilador.domains;

import java.util.ArrayList;
import java.util.List;

public class DClase {
    public List<String> modificadores = new ArrayList<>();
    public StringBuilder nombre = new StringBuilder();
    public DMetodo metodo = new DMetodo();

    public void crearClase(List<Token> datos) {
        int indice = 0;
        for (indice = 0; indice < datos.size(); indice++) {
            Token t = datos.get(indice);
            if (t.token().equals("delimitador"))
                break;
            switch (t.token()) {
                case ("reservada") -> modificadores.add(t.lexema());
                case ("identificador") -> nombre.append(t.lexema());
            }
        }

        metodo.crearMetodo(datos.subList(indice + 1, datos.size()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DeclaracionClase:");
        sb.append("\n\t\t");
        sb.append("nombre= " + nombre);
        sb.append("\n\t\t");
        sb.append("Modificadores=");
        modificadores.forEach(m -> sb.append(m).append(" "));
        sb.append("\n\t\t");
        sb.append(metodo);
        return sb.toString();
    }
}
