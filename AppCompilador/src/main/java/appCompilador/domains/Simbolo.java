package appCompilador.domains;

import java.util.ArrayList;
import java.util.List;

public class Simbolo {
    public String nombre;
    public String tipo;
    public String valor;
    public List<String> erroresSemanticos = new ArrayList<>();

    public Simbolo(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
