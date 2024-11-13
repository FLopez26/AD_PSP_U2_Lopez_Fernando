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

    public static int create(Libro l){
        int filas = -1;

        String sql = "insert into libro " +
                "(isbn, titulo, autoria_id)" +
                "values (?, ?, ?)";

        try(Connection c = Conexion.conectar()){
            PreparedStatement s = c.prepareStatement(sql);

            s.setString(1, l.getIsbn());
            s.setString(2, l.getTitulo());
            s.setInt(3, l.getAutoria().getId());

            filas = s.executeUpdate();

        } catch (SQLException e){
            System.err.println("Error de SQL en create Libro: " + e.getMessage());
        }

        return filas;
    }

    public static Libro read(String isbn){
        Libro libro = null;
        String sql = "select * from libro where isbn = ?";

        try (Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, isbn);

            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String titulo = rs.getString("titulo");
                Autoria autoria = AutoriaDAO.read(rs.getInt("autoria_id"));

                libro = new Libro(isbn, titulo, autoria);
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL en read Libro: " + e.getMessage());
        }

        return libro;
    }

    public static int update(Libro l){
        int filas = -1;
        String sql = "update libro set titulo = ?, autoria_id = ? where isbn = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, l.getTitulo());
            p.setInt(2, l.getAutoria().getId());
            p.setString(3, l.getIsbn());

            filas = p.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL en update Libro: " + e.getMessage());
        }

        return filas;
    }

    public static int delete(String isbn){
        int filas = -1;
        String sql = "delete from libro where isbn = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, isbn);

            filas = p.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL en delete Libro: " + e.getMessage());
        }

        return filas;
    }

    //OPERACIONES EXTRA

    public static ArrayList<Libro> readAll(){
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "select * from libro";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            ResultSet rs = p.executeQuery();
            while(rs.next()){

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

    public static int createOrUpdateAll(ArrayList<Libro> l){
        int filas = 0;
        ArrayList<Libro> libros = l;
        for(Libro libroArray : libros){
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
