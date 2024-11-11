package clases;

import java.io.Serializable;

public class Libro implements Serializable {
    private String isbn;
    private String titulo;
    private Autoria autoria;

    public Libro(String isbn, String titulo, Autoria autoria) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autoria = autoria;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autoria getAutoria() {
        return autoria;
    }

    @Override
    public String toString() {
        return  "ISBN = " + isbn +
                ", Titulo = " + titulo +
                ", ID de Autoria = " + autoria.getId();
    }
}