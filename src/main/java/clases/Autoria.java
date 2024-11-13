package clases;

import java.io.Serializable;

public class Autoria implements Serializable {
    private int id;
    private String nombre;
    private String apellido;

    public Autoria(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getId(){
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