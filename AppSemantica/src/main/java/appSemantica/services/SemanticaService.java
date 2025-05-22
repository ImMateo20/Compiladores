package appSemantica.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.springframework.stereotype.Service;

import appSemantica.domains.Arbol;
import appSemantica.domains.DVariable;
import appSemantica.domains.ErrorSemantico;
import appSemantica.domains.Nodo;
import appSemantica.domains.Simbolo;
import appSemantica.domains.Token;

@Service
public class SemanticaService {
    public List<ErrorSemantico> erroresGenerales;

    public List<ErrorSemantico> getErroresGenerales() {
        return erroresGenerales;
    }

    public HashMap<String, Simbolo> analizarSemanticaService(List<DVariable> variables) {
        erroresGenerales = new ArrayList<>();
        HashMap<String, Simbolo> tablaSimbolos = new HashMap<>();
        for (DVariable variable : variables) {
            Arbol arbol = variable.arbol;

            if (arbol != null) {
                if (variable.tipo.equals("indefinido")) {
                    verificarTipoIndefinido(variable, tablaSimbolos);
                } else {
                    verificarVariable(variable, tablaSimbolos);
                }
            }
        }
        return tablaSimbolos;
    }

    private void verificarTipoIndefinido(DVariable variable, HashMap<String, Simbolo> tablaSimbolos) {
        Arbol arbol = variable.arbol;

        if (contarNodosVariable(arbol.getRaiz()) != 2) {
            erroresGenerales.add(new ErrorSemantico(variable.expresionToString(),
                    "SOLO SE PUEDE ASIGNAR A VARIABLES DE UNA SOLA PALABRA"));
            return;
        }

        Token token = arbol.getRaiz().getIzquierdo().getToken();
        String nombre = token.lexema();

        if (!tablaSimbolos.containsKey(nombre)) {
            erroresGenerales.add(new ErrorSemantico(variable.expresionToString(),
                    "LA VARIABLE " + nombre + " NO HA SIDO DECLARADA"));
            return;
        }

        if (contarNodosAsignacion(arbol.getRaiz().getDerecho()) > 0) {
            verificarAsignacion(arbol, tablaSimbolos.get(nombre), tablaSimbolos);
        }

    }

    private void verificarVariable(DVariable variable, HashMap<String, Simbolo> tablaSimbolos) {
        Arbol arbol = variable.arbol;

        if (contarNodosVariable(arbol.getRaiz()) != 2) {
            erroresGenerales.add(new ErrorSemantico(variable.expresionToString(),
                    "NO SE PUEDE DECLARAR VARIABLES DE MAS DE UNA PALABRA"));
            return;
        }

        Token token = arbol.getRaiz().getIzquierdo().getToken();
        String nombre = token.lexema();

        if (token.token().equals("reservada")) {
            erroresGenerales.add(new ErrorSemantico(variable.expresionToString(),
                    "NO SE PUEDEN DECLARAR VARIABLES CON NOMBRE RESERVADO"));
            return;
        }

        if (tablaSimbolos.containsKey(nombre)) {
            erroresGenerales.add(new ErrorSemantico(variable.tipo + " " + variable.expresionToString(),
                    "LA VARIABLE " + nombre + " YA HA SIDO DECLARADA"));
            return;
        }

        tablaSimbolos.put(nombre, new Simbolo(nombre, variable.getTipo()));

        if (contarNodosAsignacion(arbol.getRaiz().getDerecho()) > 0) {
            verificarAsignacion(arbol, tablaSimbolos.get(nombre), tablaSimbolos);
        }
    }

    private void verificarAsignacion(Arbol arbol, Simbolo variable, HashMap<String, Simbolo> tablaSimbolos) {
        List<Token> tokensPostorden = new ArrayList<>();
        obtenerTokensPostorden(arbol.getRaiz().getDerecho(), tokensPostorden);

        List<Boolean> tiposCompatible = new ArrayList<>();

        for (Token token : tokensPostorden) {
            if (!token.token().equals("operador"))
                tiposCompatible.add(verificarTipoDeToken(token, variable, tablaSimbolos));
        }

        // if (!tiposCompatible.contains(false)) {
        // valor = asignarValorVariable(variable, operandos, valor);
        // variable.valor = valor;
        // }

        System.out.println("ESTOS SON LOS FALSOS DE: " + variable.nombre);
        for (Boolean ttt : tiposCompatible) {
            System.out.println(ttt);
        }

        if (!tiposCompatible.contains(false)) {
            System.out.println("AQUI ENTRO EN LA VARIABLE: " + variable.nombre);
            String resultado = evaluarExpresion(variable.tipo, tokensPostorden, tablaSimbolos);
            if (resultado != null) {
                variable.valor = resultado;
            }
        }

    }

    private String evaluarExpresion(String tipo, List<Token> tokensPostorden, HashMap<String, Simbolo> tablaSimbolos) {
        Stack<String> pila = new Stack<>();

        for (Token token : tokensPostorden) {
            String lexema = token.lexema();
            String categoria = token.token();

            if (!categoria.equals("operador")) {
                System.out.println("////////////EN LA PILAAAAAAAAAAAAAAAAAAAAAAAA");
                System.out.println(lexema);
                if (categoria.equals("identificador")) {
                    pila.push(tablaSimbolos.get(lexema).valor);
                } else {
                    pila.push(lexema);
                }
            } else {
                String op2 = pila.pop();
                String op1 = pila.pop();

                try {
                    switch (tipo) {
                        case "int" -> {
                            int a = Integer.parseInt(op1);
                            int b = Integer.parseInt(op2);
                            int res = valorEntero(a, b, lexema);
                            pila.push(Integer.toString(res));
                        }
                        case "double" -> {
                            double a = Double.parseDouble(op1);
                            double b = Double.parseDouble(op2);
                            double res = valorDouble(a, b, lexema);
                            pila.push(Double.toString(res));
                        }
                        case "String" -> {
                            if (lexema.equals("+")) {
                                op1 = (op1.contains("\"")) ? op1.substring(1, op1.length() - 1) : op1;
                                op2 = (op2.contains("\"")) ? op2.substring(1, op2.length() - 1) : op2;
                                pila.push("\"" + op1 + op2 + "\"");
                            } else {
                                return "ERROR: Operación no válida para string: " + lexema;
                            }
                        }
                    }
                } catch (Exception e) {
                    return "ERROR: " + e.getMessage();
                }
            }
        }

        return pila.isEmpty() ? null : pila.pop();

    }

    private int valorEntero(int a, int b, String operador) {
        return switch (operador) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b != 0 ? a / b : 0;
            default -> 0;
        };
    }

    private double valorDouble(double a, double b, String operador) {
        return switch (operador) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b != 0 ? a / b : 0.0;
            default -> 0.0;
        };
    }

    private Boolean verificarTipoDeToken(Token token, Simbolo variable, HashMap<String, Simbolo> tablaSimbolos) {
        return switch (variable.tipo) {
            case "int" -> verificarAsignacionEntero(token, tablaSimbolos, variable);
            case "double" -> verificarAsignacionDouble(token, tablaSimbolos, variable);
            case "String" -> verificarAsignacionString(token, tablaSimbolos, variable);
            default -> true;
        };

    }

    private Boolean verificarAsignacionEntero(Token token, HashMap<String, Simbolo> tabla, Simbolo variable) {
        String tipoToken = token.token();
        String lexema = token.lexema();

        return switch (tipoToken) {
            case "literal" -> {
                if (lexema.contains(".")) {
                    variable.erroresSemanticos.add("NO SE PUEDE ASIGNAR " + lexema + " A UN ENTERO SIN CASTEO");
                    yield false;
                }
                yield true;
            }
            case "cadena" -> {
                variable.erroresSemanticos.add("NO SE PUEDE ASIGNAR LA CADENA " + lexema + " A UN ENTERO");
                yield false;
            }
            case "identificador" -> {

                yield verificarIdentificadorDeclarado(token, tabla, variable);
            }
            default -> true;

        };
    }

    private boolean verificarAsignacionDouble(Token token, HashMap<String, Simbolo> tabla, Simbolo variable) {
        String lexema = token.lexema();

        return switch (token.token()) {
            case "cadena" -> {
                variable.erroresSemanticos.add("NO SE PUEDE ASIGNAR LA CADENA " + lexema + " A UN DOUBLE");
                yield false;
            }
            case "identificador" -> {

                yield verificarIdentificadorDeclarado(token, tabla, variable);

            }
            default -> true;

        };
    }

    private boolean verificarAsignacionString(Token token, HashMap<String, Simbolo> tabla, Simbolo variable) {
        String tipoToken = token.token();
        // if (tipoToken.equals("literal") && !lexema.matches("\".*\"")) {
        // variable.erroresSemanticos.add("ADVERTENCIA: El valor " + lexema + " no
        // parece ser una cadena válida");
        // return false;
        // }

        if (tipoToken.equals("identificador")) {
            return verificarIdentificadorDeclarado(token, tabla, variable);
        }
        return true;
    }

    private boolean verificarIdentificadorDeclarado(Token token, HashMap<String, Simbolo> tablaSimbolos,
            Simbolo variable) {
        String nombre = token.lexema();
        if (!tablaSimbolos.containsKey(nombre)) {
            variable.erroresSemanticos.add("LA VARIABLE " + nombre + " NO HA SIDO DECLARADA AÚN");
            return false;
        }
        String tipoDeDatoYaDeclarado = tablaSimbolos.get(nombre).tipo;

        if (tablaSimbolos.get(nombre).valor == null) {
            variable.erroresSemanticos
                    .add("LA VARIABLE " + nombre + " ESTA DECLARADA PERO NO TIENE ASIGNADO UN VALOR");
            return false;
        }

        if (!tipoDeDatoYaDeclarado.equals(variable.tipo)) {

            return switch (variable.tipo) {
                case "int" -> {
                    if (tipoDeDatoYaDeclarado.equals("double")) {
                        variable.erroresSemanticos.add(
                                "LA VARIABLE " + variable.nombre + " ES DE TIPO ENTERO Y " + nombre
                                        + "ES UN DOUBLE (NECESITA CASTEO)");
                    } else if (tipoDeDatoYaDeclarado.equals("String")) {
                        variable.erroresSemanticos.add(
                                "LA VARIABLE " + variable.nombre + " ES DE TIPO ENTERO Y " + nombre + "ES UN STRING");
                    }
                    yield false;
                }
                case "double" -> {
                    System.out.println("AQUI ENTRO Y LO MANDO A LA GOMA");

                    System.out.println(tipoDeDatoYaDeclarado);
                    System.out.println(variable.tipo);
                    if (tipoDeDatoYaDeclarado.equals("int")) {
                        yield true;
                    } else if (tipoDeDatoYaDeclarado.equals("String")) {
                        variable.erroresSemanticos.add(
                                "LA VARIABLE " + variable.nombre + " ES DE TIPO DOUBLE Y " + nombre + "ES UN STRING");
                    }
                    yield false;
                }
                case "String" -> {
                    yield true;
                }
                default -> false;
            };
        }
        return true;

    }

    private void obtenerTokensPostorden(Nodo n, List<Token> tI) {
        if (n != null) {
            obtenerTokensPostorden(n.getIzquierdo(), tI);
            obtenerTokensPostorden(n.getDerecho(), tI);
            tI.add(n.getToken());
        }
    }

    private int contarNodosVariable(Nodo n) {
        return (n == null) ? 0 : 1 + contarNodosVariable(n.getIzquierdo());
    }

    private int contarNodosAsignacion(Nodo n) {
        return (n == null) ? 0 : 1 + contarNodosAsignacion(n.getDerecho());
    }

}