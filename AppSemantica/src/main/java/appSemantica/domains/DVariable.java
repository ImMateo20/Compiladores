package appSemantica.domains;

import java.util.List;

public class DVariable {
    public String tipo;
    public List<Token> expresion;
    public Arbol arbol;

    public DVariable(String tipo, List<Token> expresion) {
        this.tipo = tipo;
        this.expresion = expresion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\t\t\t\t")
                .append("tipo=")
                .append(tipo)
                .append("\n\t\t\t\t")
                .append("arbol=")
                .append(arbol == null ? expresionToString() : arbol.obtenerRIn(arbol.getRaiz()));
        return sb.toString();
    }

    public String expresionToString() {
        StringBuilder sb = new StringBuilder();
        for (Token token : expresion) {
            sb.append(token.lexema());
        }
        return sb.toString();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Token> getExpresion() {
        return expresion;
    }

    public void setExpresion(List<Token> expresion) {
        this.expresion = expresion;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }
}
