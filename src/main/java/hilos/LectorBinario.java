package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.*;
import java.util.ArrayList;

public class LectorBinario extends Thread {

    private static File file = new File("./files/libros.bin");
    private static ArrayList<Autoria> autorias;
    private static ArrayList<Libro> libros;

    public LectorBinario(ArrayList<Autoria> autorias, ArrayList<Libro> libros) {
        this.autorias = autorias;
        this.libros = libros;
    }

    /**
     * Le un fichero binario y lo carga en las ArrayLists correspondientes.
     * Se tratan las excepciones dentro, no se lanzan
     */
    @Override
    public void run() {

        //Sincronizo con el file para evitar que dos hilos lean al mismo tiempo al fichero.
        synchronized (file){

            //Uso el .clear() en los ArrayList para evitar que se dupliquen los datos al importar el fichero
            autorias.clear();
            libros.clear();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                autorias.addAll((ArrayList<Autoria>) ois.readObject());
                libros.addAll((ArrayList<Libro>) ois.readObject());

            } catch (FileNotFoundException e) {
                System.err.println("Fichero no encontrado: " + e.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}