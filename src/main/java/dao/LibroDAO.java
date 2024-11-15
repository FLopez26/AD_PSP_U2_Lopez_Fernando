package dao;

import clases.Autoria;
import clases.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibroDAO {

    //OPERACIONES CRUD

    /**
     * Añade un Libro en la BBDD. Los datos a añadir los recoge del Libro que recibe por parámetro.
     * @param l
     * @return
     */
    public static int create(Libro l){
        int filas = -1;

        //Sentencia sql
        String sql = "insert into libro " +
                "(isbn, titulo, autoria_id)" +
                "values (?, ?, ?)";

        try(Connection c = Conexion.conectar()){
            PreparedStatement s = c.prepareStatement(sql);

            //Inserción de datos en la sentencia
            s.setString(1, l.getIsbn());
            s.setString(2, l.getTitulo());
            s.setInt(3, l.getAutoria().getId());

            filas = s.executeUpdate();

        } catch (SQLException e){
            System.err.println("Error de SQL en create Libro: " + e.getMessage());
        }

        return filas;
    }

    /**
     * Lee el Libro de la BBDD que coincida con el isbn pasado por parámetro.
     * @param isbn
     * @return
     */
    public static Libro read(String isbn){
        //Inicializo una Autoria valiendo null de tal forma que si no existe una Autoria con ese id,
        //devolverá uno con valor null
        Libro libro = null;

        //Sentencia sql
        String sql = "select * from libro where isbn = ?";

        try (Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            //Inserción de datos en la sentencia
            p.setString(1, isbn);

            ResultSet rs = p.executeQuery();
            if(rs.next()){

                //Recojo los valores y se lo asigno a variables para luego crear el Libro
                String titulo = rs.getString("titulo");
                Autoria autoria = AutoriaDAO.read(rs.getInt("autoria_id"));

                libro = new Libro(isbn, titulo, autoria);
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL en read Libro: " + e.getMessage());
        }

        return libro;
    }

    /**
     * Actualiza los datos de un Libro de la BBDD con otro pasada por parámetro.
     * @param l
     * @return
     */
    public static int update(Libro l){
        int filas = -1;

        //Sentencia sql
        String sql = "update libro set titulo = ?, autoria_id = ? where isbn = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            //Inserción de datos en la sentencia
            p.setString(1, l.getTitulo());
            p.setInt(2, l.getAutoria().getId());
            p.setString(3, l.getIsbn());

            filas = p.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL en update Libro: " + e.getMessage());
        }

        return filas;
    }

    /**
     * Elimina el Libro de la BBDD que coincida con el isbn pasado por parámetro.
     * @param isbn
     * @return
     */
    public static int delete(String isbn){
        int filas = -1;

        //Sentencia sql
        String sql = "delete from libro where isbn = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            //Inserción de datos en la sentencia
            p.setString(1, isbn);

            filas = p.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL en delete Libro: " + e.getMessage());
        }

        return filas;
    }

    //OPERACIONES EXTRA

    /**
     * Lee todas los Libros de la BBDD y los devuleve en un ArrayList.
     * @return
     */
    public static ArrayList<Libro> readAll(){
        ArrayList<Libro> libros = new ArrayList<>();

        //Sentencia sql
        String sql = "select * from libro";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            ResultSet rs = p.executeQuery();
            while(rs.next()){

                //Recojo los valores y se lo asigno a variables para luego crear la Autoria y añadirla al ArrayList
                String isbn = rs.getString("isbn");
                String titulo = rs.getString("titulo");
                Autoria autoria = AutoriaDAO.read(rs.getInt("autoria_id"));

                Libro libro = new Libro(isbn, titulo, autoria);
                libros.add(libro);
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL en readAll Libro: " + e.getMessage());
        }

        return libros;
    }

    /**
     * Al recibir un ArrayList de Libros por parámetro lo recorre y hace dos posibles opciones:
     * 1.- Si al comprobar el isbn el Libro no existe, lo crea.
     * 2.- Si el Libro existe, lo actualiza.
     * Devuelve un int que valdrá la cantidad de cambios que se hayan realizado.
     * @param l
     * @return
     */
    public static int createOrUpdateAll(ArrayList<Libro> l){
        int filas = 0;
        ArrayList<Libro> libros = l;

        //Recorro los Libros del ArrayList
        for(Libro libroArray : libros){

            //Llamo a la función read() para que me devuelva el Libro completo teniendo solo el isbn
            Libro libro = read(libroArray.getIsbn());
            if(libro == null){
                create(libroArray);
                filas++;
            } else{
                update(libroArray);
                filas++;
            }
        }
        return filas;
    }
}
