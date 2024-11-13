package hilos;

import clases.Autoria;
import clases.Libro;

import java.io.*;
import java.util.ArrayList;

public class LectorTexto extends Thread{

    private static File file;
    private static ArrayList<Autoria> autorias;
    private static ArrayList<Libro> libros;

    public LectorTexto(File file, ArrayList<Autoria> autorias, ArrayList<Libro> libros) {
        this.file = file;
        this.autorias = autorias;
        this.libros = libros;
    }

    @Override
    public void run(){

        synchronized (file){
            autorias.clear();
            libros.clear();

            try(BufferedReader br = new BufferedReader(new FileReader(file))){

                String line = br.readLine();
                //Salto la primera línea porque siempre va a ser '***AUT***'
                while(!(line = br.readLine()).equals("***LIB***")){

                    //Atributos necesarios para crear el objeto 'Autoria'
                    int id = Integer.parseInt(line);
                    line = br.readLine();
                    String nombre = line;
                    line = br.readLine();
                    String apellido = line;

                    //Creación de 'Autoria' dentro del ArrayList
                    autorias.add(new Autoria(id,nombre,apellido));
                }
                while(!((line = br.readLine()) == null)){

                    //Atributos necesarios para crear el objeto 'Libro'
                    String isbn = line;
                    line = br.readLine();
                    String titulo = line;
                    line = br.readLine();

                    //Recuperar del ArrayList 'autorias' el objeto 'Autoria' que coincide con el ID del documento.
                    Autoria autoria = null;
                    int idAutoria = Integer.parseInt(line);
                    for(Autoria a : autorias){
                        if(a.getId() == idAutoria) autoria = new Autoria(a.getId(),a.getNombre(),a.getApellido());
                    }

                    //Creación de 'Libro' dentro del ArrayList
                    libros.add(new Libro(isbn,titulo,autoria));
                }

            } catch (FileNotFoundException e) {
                System.err.println("Fichero no encontrado: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (NullPointerException e){
                System.err.println("Fichero vacio: " + e.getMessage());
            }
        }
    }
}
