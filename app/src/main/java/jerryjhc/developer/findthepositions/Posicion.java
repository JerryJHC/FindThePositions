package jerryjhc.developer.findthepositions;


public class Posicion {

    private String nombre;
    private String situacion;
    private Boolean enc;

    public Posicion() {
        enc = false;
    }

    public Posicion(String nombre, String situacion) {
        this.nombre = nombre;
        this.situacion = situacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSituacion() {
        return situacion;
    }

    public Boolean getEnc(){
        return enc;
    }
    @Override
    public String toString() {
        return "Posicion{" +
                "nombre='" + nombre + '\'' +
                ", situacion='" + situacion + '\'' +
                '}';
    }

}
