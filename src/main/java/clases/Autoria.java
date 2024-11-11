package clases;

import java.io.Serializable;

public class Autoria implements Serializable {
    private String id;
    private String nombre;
    private String apellido;

    public Autoria(String id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getId(){
        return this.id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    @Override
    public String toString() {
        return  "ID = " + id +
                ", Nombre = " + nombre +
                ", Apellido = " + apellido;
    }
}