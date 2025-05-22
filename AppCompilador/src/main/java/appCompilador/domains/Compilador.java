package appCompilador.domains;

import java.util.List;

public class Compilador {
    public DClase clase = new DClase();

    public Compilador(List<Token> datos) {
        clase.crearClase(datos);
    }

    @Override
    public String toString() {
        StringBuilder db = new StringBuilder();
        db.append("Compilador");
        db.append("\n\t");
        db.append(clase);
        return db.toString();
    }
}
