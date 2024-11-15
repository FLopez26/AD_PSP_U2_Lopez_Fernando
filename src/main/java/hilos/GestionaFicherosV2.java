package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.File;
import java.util.ArrayList;

public class GestionaFicherosV2 {

    /**
     * Importa los Libros y Autorias de un fichero de texto creando un hilo de la clase LectorTexto
     * @param f
     * @param autorias
     * @param libros
     */
    public void importarTexto(File f, ArrayList<Autoria> autorias, ArrayList<Libro> libros){

        LectorTexto lt = new LectorTexto(f, autorias, libros);
        lt.start();

        // Con .join() espera a que termine la lectura para continuar con la ejecución
        try {
            lt.join();
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Exporta los Libros y Autorias a un fichero de texto creando un hilo de la clase EscritorTexto
     * @param f
     * @param autorias
     * @param libros
     */
    public void exportar(File f, ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        EscritorTexto et = new EscritorTexto(f,autorias,libros);
        et.start();
    }

    /**
     * Importa los Libros y Autorias en un fichero binario creando un hilo de la clase LectorBinario
     * @param autorias
     * @param libros
     */
    public void importar(ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        LectorBinario lb = new LectorBinario(autorias, libros);
        lb.start();

        // Con .join() espera a que termine la lectura para continuar con la ejecución
        try {
            lb.join();
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Exporta los Libros y Autorias en un fichero binario creando un hilo de la clase EscritorBinario
     * @param autorias
     * @param libros
     */
    public void guardarBin(ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        EscritorBinario eb = new EscritorBinario(autorias, libros);
        eb.start();
    }
}
