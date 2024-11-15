package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.*;
import java.util.ArrayList;

public class EscritorBinario extends Thread{

    private static File file = new File("./files/biblioteca.bin");
    private static ArrayList<Autoria> autorias;
    private static ArrayList<Libro> libros;

    public EscritorBinario(ArrayList<Autoria> autorias, ArrayList<Libro> libros) {
        this.autorias = autorias;
        this.libros = libros;
    }

    @Override
    /**
     * Escribe los ArrayLists de Autorias y Libros en un fichero binario.
     * Se tratan las excepciones dentro, no se lanzan
     */
    public void run(){

        //Sincronizo con el file para evitar que dos hilos escriban al mismo tiempo al fichero binario.
        synchronized (file){

            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){

                oos.writeObject(autorias);
                oos.writeObject(libros);

            } catch (FileNotFoundException e) {
                System.err.println("Fichero no encontrado: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }

        }
    }
}