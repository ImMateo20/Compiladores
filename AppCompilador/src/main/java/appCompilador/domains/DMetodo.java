package appCompilador.domains;

import java.util.ArrayList;
import java.util.List;

public class DMetodo {
    public List<String> modificadores = new ArrayList<>();
    public StringBuilder retorno = new StringBuilder();
    public StringBuilder nombre = new StringBuilder();
    public Parametro parametro;
    public List<DVariable> variables = new ArrayList<>();

    public void crearMetodo(List<Token> datos) {
        int indice = 0;
        int contadorP = 0;
        for (indice = 0; indice < datos.size(); indice++) {
            Token t = datos.get(indice);
            if (t.token().equals("delimitador"))
                break;
            switch (t.token()) {
                case "reservada" -> {
                    contadorP++;
                    if (contadorP == 3)
                        retorno.append(t.lexema());
                    else
                        modificadores.add(t.lexema());
                }
                case "identificador" -> nombre.append(t.lexema());
            }
        }

        int cierre = buscaCierre(datos.subList(0, datos.size()));

        obtenerParametro(datos.subList(indice + 1, cierre));
        obtenerVariables(datos.subList(cierre + 2, datos.size()));
    }

    private int buscaCierre(List<Token> datos) {
        int indice = 0;
        while (!datos.get(indice++).lexema().equals(")"))
            ;
        return indice - 1;
    }

    private void obtenerParametro(List<Token> datos) {
        String nombre = datos.getLast().lexema();
        StringBuilder tipo = new StringBuilder();
        for (int i = 0; i < datos.size() - 1; i++) {
            tipo.append(datos.get(i).lexema());
        }
        this.parametro = new Parametro(tipo.toString(), nombre);
    }

    public void obtenerVariables(List<Token> datos) {
        int iniciador = 0;
        List<Token> expresion = new ArrayList<>();
        // String expresion = "";
        for (int i = 0; i < datos.size(); i++) {
            Token t = datos.get(i);
            if (t.lexema().equals(";")) {
                System.out.println(datos.get(iniciador).token());
                this.variables.add(new DVariable(
                        (datos.get(iniciador).token().equals("reservada")) ? datos.get(iniciador).lexema()
                                : "indefinido",
                        expresion));
                iniciador = i + 1;
                expresion = new ArrayList<>();
            } else if (!t.token().equals("reservada"))
                expresion.add(new Token(t.lexema(), t.token()));
            if (t.lexema().equals("{"))
                break;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Declaracion Metodo")
                .append("\n\t\t\t")
                .append("nombre=" + nombre)
                .append("\n\t\t\t")
                .append("retorno=" + retorno)
                .append("\n\t\t\t")
                .append("Modificadores=");
        modificadores.forEach(m -> sb.append(m).append(" "));
        sb.append("\n\t\t\t")
                .append("parametro=" + parametro)
                .append("\n\t\t\t")
                .append("Declaracion Variables:");
        variables.forEach(sb::append);
        return sb.toString();
    }
}
