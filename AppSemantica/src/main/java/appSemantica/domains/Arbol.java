package appSemantica.domains;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import appSemantica.exception.ErrorSemanticoException ;

public class Arbol {
    private Nodo raiz;

    public void setRaiz(Nodo n) {
        raiz = n;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void agrega(List<Token> tokens) {
        raiz = crearArbol(tokens);
    }

    public Nodo crearArbol(List<Token> tokens) {
        if (tokens.size() == 1)
            return new Nodo(tokens.get(0));

        int ini = 0;
        int fin = tokens.size();

        while (esParentesis(tokens, ini, fin)) {
            ini++;
            fin--;
        }

        int indice = obtenIndice(tokens, ini, fin);

        if (indice == -1)
            throw new ErrorSemanticoException ("No se encontro operador aritmetico");

        Nodo nodo = new Nodo(tokens.get(indice));

        nodo.setIzquierdo(crearArbol(tokens.subList(ini, indice)));
        nodo.setDerecho(crearArbol(tokens.subList(indice + 1, fin)));
        ;
        return nodo;
    }

    private boolean esParentesis(List<Token> tokens, int ini, int fin) {
        if (!tokens.get(0).lexema().equals("("))
            return false;

        Deque<Integer> stack = new ArrayDeque<>();

        int inicio = -1;
        int termina = -1;

        for (int i = ini; i < fin; i++) {
            String lexema = tokens.get(i).lexema();

            if (lexema.equals("("))
                stack.push(i);

            if (lexema.equals(")")) {
                inicio = stack.pop();
                termina = i;
            }

        }

        if (!stack.isEmpty())
            throw new ErrorSemanticoException ("No se encontro el parentesis de cierre");

        return inicio == ini && termina == fin - 1;
    }

    public int obtenIndice(List<Token> tokens, int ini, int fin) {
        int peso = 3;
        int indice = -1;
        for (int i = ini; i < fin; i++) {
            String lexema = tokens.get(i).lexema();

            if (lexema.equals("(")) {
                i = recorrerParentesis(tokens, i, fin);
                if (i == -1)
                    throw new ErrorSemanticoException ("No se encontro el parentesis de cierre");
                continue;
            } else if (lexema.equals(")")) {
                throw new ErrorSemanticoException ("No se encontro el parentesis de apertura");
            }

            int valor = switch (lexema) {
                case "=" -> 0;
                case "+", "-" -> 1;
                case "*", "/" -> 2;
                default -> -1;
            };

            if (valor == -1)
                continue;

            if (valor <= peso) {
                peso = valor;
                indice = i;
            }

        }
        return indice;
    }

    private int recorrerParentesis(List<Token> tokens, int ini, int fin) {
        Deque<String> stack = new ArrayDeque<>();
        for (int i = ini; i < fin; i++) {
            String lexema = tokens.get(i).lexema();

            if (lexema.equals("("))
                stack.push(lexema);
            if (lexema.equals(")"))
                stack.pop();

            if (stack.isEmpty())
                return i;
        }
        return -1;
    }

    public List<String> obtenerRecorridos() {
        List<String> recorridos = new ArrayList<>();
        recorridos.add(obtenerRPre(raiz));
        recorridos.add(obtenerRIn(raiz));
        recorridos.add(obtenerRPost(raiz));
        return recorridos;

    }

    public String obtenerRPre(Nodo n) {
        StringBuilder cadena = new StringBuilder();
        if (n != null) {
            cadena.append(n.getToken().lexema());
            cadena.append(obtenerRPre(n.getIzquierdo()));
            cadena.append(obtenerRPre(n.getDerecho()));
        }
        return cadena.toString();
    }

    public String obtenerRIn(Nodo n) {
        StringBuilder cadena = new StringBuilder();
        if (n != null) {
            cadena.append(obtenerRIn(n.getIzquierdo()));
            cadena.append(n.getToken().lexema());
            cadena.append(obtenerRIn(n.getDerecho()));
        }
        return cadena.toString();
    }

    public String obtenerRPost(Nodo n) {
        StringBuilder cadena = new StringBuilder();
        if (n != null) {
            cadena.append(obtenerRPost(n.getIzquierdo()));
            cadena.append(obtenerRPost(n.getDerecho()));
            cadena.append(n.getToken().lexema());
        }
        return cadena.toString();
    }
}