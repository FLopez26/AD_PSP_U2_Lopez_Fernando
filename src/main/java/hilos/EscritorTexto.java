package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EscritorTexto extends Thread{

    private static File file;
    private static ArrayList<Autoria> autorias;
    private static ArrayList<Libro> libros;

    public EscritorTexto(File file, ArrayList<Autoria> autorias, ArrayList<Libro> libros) {
        this.file = file;
        this.autorias = autorias;
        this.libros = libros;
    }

    @Override
    public void run(){

        synchronized (file){


            try(PrintWriter pw = new PrintWriter(new FileWriter(file))){

                //Escritura de los objetos 'Autoria' de su ArrayList
                pw.println("***AUT***");
                for(Autoria a : autorias){
                    pw.println(a.getId());
                    pw.println(a.getNombre());
                    pw.println(a.getApellido());
                }

                //Escritura de los objetos 'Libro' de su ArrayList
                pw.println("***LIB***");
                for(Libro l : libros){
                    pw.println(l.getIsbn());
                    pw.println(l.getTitulo());
                    pw.println(l.getAutoria().getId());
                }

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
