package appCompilador.domains;

public class Parametro {
    private String tipo;
    private String nombre;

    public Parametro(String tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("nombre:"+nombre+" ,tipo:"+tipo);
        return sb.toString();
    }
}
