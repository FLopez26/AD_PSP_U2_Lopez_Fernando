package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.*;
import java.util.ArrayList;

public class EscritorBinario extends Thread{

    private static File file = new File("./files/libros.txt");
    private static ArrayList<Autoria> autorias;
    private static ArrayList<Libro> libros;

    public EscritorBinario(ArrayList<Autoria> autorias, ArrayList<Libro> libros) {
        this.autorias = autorias;
        this.libros = libros;
    }

    @Override
    public void run(){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){

            oos.writeObject(autorias);
            oos.writeObject(libros);

        } catch (FileNotFoundException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        } catch (IOException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }
    }
}