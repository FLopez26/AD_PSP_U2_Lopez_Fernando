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

    @Override
    public void run() {

        synchronized (file){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

                autorias.addAll((ArrayList<Autoria>) ois.readObject());
                libros.addAll((ArrayList<Libro>) ois.readObject());

            } catch (FileNotFoundException e) {
                e.printStackTrace(); //TODO: buen mensaje de error
            } catch (IOException e) {
                e.printStackTrace(); //TODO: buen mensaje de error
            } catch (ClassNotFoundException e) {
                e.printStackTrace(); //TODO: buen mensaje de error
            }
        }
    }
}