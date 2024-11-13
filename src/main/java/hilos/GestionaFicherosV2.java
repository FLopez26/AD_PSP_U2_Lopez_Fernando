package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.File;
import java.util.ArrayList;

public class GestionaFicherosV2 {

    public void importarTexto(File f, ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        LectorTexto lt = new LectorTexto(f, autorias, libros);
        lt.start();
    }

    public void exportar(File f, ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        EscritorTexto et = new EscritorTexto(f,autorias,libros);
        et.start();
    }

    public void guardarBin(ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        EscritorBinario eb = new EscritorBinario(autorias, libros);
        eb.start();
    }

    public void importar(ArrayList<Autoria> autorias, ArrayList<Libro> libros){
        LectorBinario lb = new LectorBinario(autorias, libros);
        lb.start();
    }
}
