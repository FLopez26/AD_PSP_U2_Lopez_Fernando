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
                "(isbn, titulo, autoria)" +
                "values (?, ?, ?)";

        try(Connection c = Conexion.conectar()){
            PreparedStatement s = c.prepareStatement(sql);

            s.setString(1, l.getIsbn());
            s.setString(2, l.getTitulo());
            s.setString(3, l.getAutoria().getId());

            filas = s.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return filas;
    }

    public static Libro read(String isbn){
        Libro libro = null;
        String sql = "select * from libro where id = ?";

        try (Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, isbn);

            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String titulo = rs.getString("titulo");
                Autoria autoria = AutoriaDAO.read(rs.getString("autoria"));

                libro = new Libro(isbn, titulo, autoria);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return libro;
    }

    public static int update(Libro l){
        int filas = -1;
        String sql = "update libro set titulo = ?, autoria = ? where isbn = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, l.getTitulo());
            p.setString(2, l.getAutoria().getId());
            p.setString(3, l.getIsbn());

            filas = p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return filas;
    }

    public static int delete(String isbn){
        int filas = -1;
        String sql = "delete from libro where id = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, isbn);

            filas = p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
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
            if(rs.next()){

                String isbn = rs.getString("isbn");
                String titulo = rs.getString("titulo");
                Autoria autoria = AutoriaDAO.read(rs.getString("autoria"));

                Libro libro = new Libro(isbn, titulo, autoria);
                libros.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return libros;
    }

    public static int createOrUpdateAll(ArrayList<Libro> l){
        //TODO
        return -1;
    }
}
