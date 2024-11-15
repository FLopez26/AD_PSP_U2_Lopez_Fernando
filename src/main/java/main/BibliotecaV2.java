package main;

import clases.Autoria;
import clases.Libro;
import dao.AutoriaDAO;
import dao.Conexion;
import dao.LibroDAO;
import hilos.GestionaFicherosV2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BibliotecaV2 {
    private static ArrayList<Autoria> autorias = new ArrayList<Autoria>();
    private static ArrayList<Libro> libros = new ArrayList<Libro>();

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int opc = -1;

        //
        System.out.println("---------------------------------");
        Conexion.crearTablas();
        System.out.println("Tablas de la BBDD creadas en caso de que no estuvieran.");
        autorias = AutoriaDAO.readAll();
        libros = LibroDAO.readAll();
        System.out.println("Información recogida de la BBDD en caso de que hubiera.");

        while(opc!=0){
            System.out.println("---------------------------------");
            System.out.println("Bienvenido a la Práctica 2. Seleccione una de las siguientes opciones:");
            System.out.println(" 1.- Crear autor");
            System.out.println(" 2.- Ver autores");
            System.out.println(" 3.- Crear libro");
            System.out.println(" 4.- Mostrar libros");
            System.out.println(" 5.- Eliminar libro");
            System.out.println(" 6.- Exportar a fichero de texto");
            System.out.println(" 7.- Importar fichero de texto");
            System.out.println(" 8.- Guardar el fichero binario");
            System.out.println(" 9.- Leer fichero binario");
            System.out.println(" 0.- Salir");
            System.out.println("---------------------------------");
            try{
                opc = sc.nextInt();
                switch (opc){
                    case 1:
                        crearAutor();
                        break;
                    case 2:
                        verAutores();
                        break;
                    case 3:
                        crearLibro();
                        break;
                    case 4:
                        mostrarLibros();
                        break;
                    case 5:
                        eliminarLibro();
                        break;
                    case 6:
                        exportarTxt();
                        break;
                    case 7:
                        importarTxt();
                        break;
                    case 8:
                        escribirBin();
                        break;
                    case 9:
                        leerBin();
                        break;
                    case 0:
                        AutoriaDAO.createOrUpdateAll(autorias);
                        LibroDAO.createOrUpdateAll(libros);
                        System.out.println("Datos guardados en la BBDD.");
                        System.out.println("Saliendo del programa.");
                        break;
                    default:
                        System.err.println("Introduzca una opción válida");
                }
            } catch(InputMismatchException e){
                System.err.println("Introduzca un número.");
                String error = sc.nextLine();
            }
        }
    }

    /*
     ----------- MÉTODOS GENERALES QUE SE USARÁN DURANTE EL PROGRAMA -----------
     */

    /**
     * Comprueba si el 'id' pasado por parámetro existe o no.
     * Devuelve 'true' si existe o 'false' si no existe.
     * @param id
     * @return
     */
    public static boolean checkId(int id){
        boolean existe = false;
        for (Autoria a : autorias){
            if (a.getId() == id) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    /**
     * Comprueba si el 'isbn' pasado por parámetro existe o no.
     * Devuelve 'true' si existe o 'false' si no existe.
     * @param isbn
     * @return
     */
    public static boolean checkIsbn(String isbn){
        boolean existe = false;
        for (Libro l : libros){
            if (l.getIsbn().equals(isbn)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    /**
     * Elimina el libro con el 'isbn' pasado por parámetro de su ArrayList.
     * @param isbn
     */
    public static void deleteLibro(String isbn){
        for(Libro l : libros){
            if(l.getIsbn().equals(isbn)) {
                libros.remove(l);
                return;
            }
        }
    }

    /*
    ----------- FIN DE MÉTODOS GENERALES -----------
     */

    //CASO DEL MENÚ 1
    /**
     * Crea un autor recibiendo los datos (id, nombre y apellido) del usuario por pantalla.
     * Se comprueba que no exista un autor con su mismo ID y que la entrada sea un dato tipo int.
     * En caso de que surja algún error, se volverá al menú del programa.
     *
     */
    public static void crearAutor(){
        Scanner sc = new Scanner(System.in);
        System.out.println(" - CREACIÓN DE AUTOR - ");

        //Iniciación de las variables
        int id = -1;
        String nombre = "";
        String apellido = "";

        try{
            System.out.print("Introduzca el ID: ");
            id = sc.nextInt();
            if(checkId(id)){
                System.err.println("Existe un autor con el mismo ID.");
                return;
            }
            Scanner sc2 = new Scanner(System.in);
            System.out.println("Introduzca el nombre:");
            nombre = sc2.nextLine();

            System.out.println("Introduzca el apellido:");
            apellido = sc2.nextLine();

            autorias.add(new Autoria(id,nombre,apellido));
            AutoriaDAO.create(new Autoria(id,nombre,apellido));
        } catch (InputMismatchException e){
            System.err.println("No es un número");
            String error = sc.nextLine();
        }
    }

    //CASO DEL MENÚ 2
    /**
     * Muestra por pantalla todos los autores guardados en el ArrayList 'autorias'.
     */
    public static void verAutores(){
        for(Autoria a: autorias){
            System.out.println(a);
        }
    }

    //CASO DEL MENÚ 3
    /**
     * Crea un libro recibiendo los datos (isbn, titulo y autoria) del usuario por pantalla.
     * Se comprueba que exista ya el autor con su ID y que la entrada sea un dato tipo int.
     * En caso de que surja algún error, se volverá al menú del programa.
     *
     */
    public static void crearLibro(){
        Scanner sc = new Scanner(System.in);
        System.out.println(" - CREACIÓN DE LIBRO - ");

        //Iniciación de las variables
        String isbn = "";
        String nombre = "";
        Autoria autor = null;

        try{
            System.out.println("Introduzca el ISBN:");
            isbn = sc.nextLine();
            if(checkIsbn(isbn)){
                System.err.println("Existe un libro con el mismo ISBN.");
                return;
            }

            System.out.println("Introduzca el nombre:");
            nombre = sc.nextLine();

            System.out.println("Introduzca el ID del autor");
            int id = sc.nextInt();
            if(!checkId(id)){
                System.err.println("No existe un autor con ese ID.");
                return;
            }
            for (Autoria a : autorias){
                if(a.getId() == id) autor = a;
            }

            libros.add(new Libro(isbn,nombre,autor));
            LibroDAO.create(new Libro(isbn,nombre,autor));
        } catch (InputMismatchException e){
            System.err.println("No es un número");
            String error = sc.nextLine();
        }
    }

    //CASO DEL MENÚ 4
    /**
     * Muestra por pantalla todos los libros guardados en el ArrayList 'libros'.
     */
    public static void mostrarLibros(){
        for(Libro l : libros){
            System.out.println(l);
        }
    }

    //CASO DEL MENÚ 5
    /**
     * Elimina un libro introduciendo por pantalla el ISBN.
     * En caso de que el ISBN no coincida con el de ningún libro se ofrecen 3 opciones:
     * Crear un libro desde 0 / Escribir otro ISBN sin salir del método / Volver al menú de inicio
     */
    public static void eliminarLibro(){
        boolean sigue = true;
        while(sigue){
            Scanner sc = new Scanner(System.in);
            System.out.println("introduzca el ISBN del libro que quiere eliminar:");

            String isbn = sc.nextLine();
            if(checkIsbn(isbn)){
                deleteLibro(isbn);
                LibroDAO.delete(isbn);
                sigue = false;
            } else{
                System.out.println("El ISBN no pertenece a ningún libro, puede:");
                System.out.println(" 1.- Crear el libro.");
                System.out.println(" 2.- Escribir otro ISBN");
                System.out.println(" 3.- Volver al menú");
                int opc = sc.nextInt();
                switch (opc){
                    case 1:
                        crearLibro();
                        sigue = false;
                        break;
                    //Salto el case 2 porque si el usuario desea continuar el boolean 'sigue' debe seguir siendo 'true'
                    case 3:
                        sigue = false;
                        break;
                    default:
                        System.err.println("No ha introducido una opción correcta");
                        sigue = false;
                }
            }
        }
    }

    //CASO DEL MENÚ 6
    /**
     * Pasando por pantalla una ruta de un archivo se van a guardar todos los datos de los ArrayList 'autorias' y 'libros'.
     * El método que realiza el funcionamiento se encuentra en la clase 'GestionaFicheros'.
     */
    public static void exportarTxt(){
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);

        System.out.println("Introduzca la ruta del archivo donde quiere exportar los datos.");
        System.out.println("Es necesario incluir la ruta completa. Ejemplo: ./files/archivo.txt");
        File file = new File(sc.nextLine());

        if(file.exists()){
            System.out.println("¿Quiere sobreescribir el archivo? [s/n]");
            String opcion = sc2.nextLine();
            if(opcion.equals("s")){
                GestionaFicherosV2 gf = new GestionaFicherosV2();
                //Aquí se realiza la gestión de excepciones que se puede generar en la clase 'GestionaFicheros'
                gf.exportar(file,autorias,libros);

            }else if(opcion.equals("n")){
                System.out.println("Volviendo al menú.");
            }else{
                System.err.println("Respuesta no válida, volviendo al menú.");
            }
        }else{
            System.err.println("El fichero no existe, volviendo al menú");
        }
    }

    //CASO DEL MENÚ 7
    /**
     * Pasando por pantalla una ruta de un archivo se van a añadir en los arrayList
     * 'autorias' y 'libros' todos los datos ya guardados.
     * El método que realiza el funcionamiento se encuentra en la clase 'GestionaFicheros'.
     */
    public static void importarTxt(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca la ruta del archivo de donde quiere importar los datos.");
        System.out.println("Es necesario incluir la ruta completa. Ejemplo: ./files/archivo.txt");
        File file = new File(sc.nextLine());

        GestionaFicherosV2 gf = new GestionaFicherosV2();
        gf.importarTexto(file,autorias,libros);


    }

    //CASO DEL MENÚ 8
    /**
     * Se guardarán todos los datos de los ArrayList 'autorias' y 'libros' en un fichero binario (./files/biblioteca.bin).
     * El método que realiza el funcionamiento se encuentra en la clase 'GestionaFicheros'.
     */
    public static void escribirBin(){
        GestionaFicherosV2 gf = new GestionaFicherosV2();
        gf.guardarBin(autorias,libros);
        System.out.println("Datos guardados en el archivo 'biblioteca.bin'");
    }

    //CASO DEL MENÚ 9
    /**
     * Se van aexportar los datos del archivo 'biblioteca.bin' a los ArrayList 'autorias' y 'libros'
     * El método que realiza el funcionamiento se encuentra en la clase 'GestionaFicheros'.
     */
    public static void leerBin(){
        GestionaFicherosV2 gf = new GestionaFicherosV2();
        gf.importar(autorias,libros);
        System.out.println("Datos importados al programa desde el archivo 'biblioteca.bin'");
    }
}
