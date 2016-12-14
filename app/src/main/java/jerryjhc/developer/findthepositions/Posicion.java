package jerryjhc.developer.findthepositions;


import android.os.Parcel;
import android.os.Parcelable;

public class Posicion implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(situacion);
        dest.writeString(enc.toString());
    }

    public static final Parcelable.Creator<Posicion> CREATOR
            = new Parcelable.Creator<Posicion>() {
        public Posicion createFromParcel(Parcel in) {
            return new Posicion(in);
        }

        public Posicion[] newArray(int size) {
            return new Posicion[size];
        }
    };

    private Posicion(Parcel in) {
        nombre = in.readString();
        situacion = in.readInt();
        enc = Boolean.parseBoolean(in.readString());
    }
}
