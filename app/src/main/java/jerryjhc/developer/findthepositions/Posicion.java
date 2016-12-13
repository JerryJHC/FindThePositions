package jerryjhc.developer.findthepositions;


public class Posicion {

    private String nombre;
    private int situacion;
    private Boolean enc;

    public Posicion() {
        enc = false;
    }

    public Posicion(String nombre, int situacion) {
        this.nombre = nombre;
        this.situacion = situacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSituacion(String situacion) {
        this.situacion = Integer.parseInt(situacion);
    }

    public String getNombre() {
        return nombre;
    }

    public int getSituacion() {
        return situacion;
    }

    public Boolean getEnc() {
        return enc;
    }

    public void setEnc(Boolean enc) {
        this.enc = enc;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "nombre='" + nombre + '\'' +
                ", situacion='" + situacion + '\'' +
                '}';
    }

}
