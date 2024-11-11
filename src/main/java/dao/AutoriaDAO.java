package dao;



import clases.Autoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoriaDAO {

    //OPERACIONES CRUD

    public static int create(Autoria a){
        int filas = -1;

        String sql = "insert into autoria " +
                "(id, nombre, apellido)" +
                "values (?, ?, ?)";

        try(Connection c = Conexion.conectar()){
            PreparedStatement s = c.prepareStatement(sql);

            s.setString(1, a.getId());
            s.setString(2, a.getNombre());
            s.setString(3, a.getApellido());

            filas = s.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return filas;
    }

    public static Autoria read(String id){
        Autoria autoria = null;
        String sql = "select * from autoria where id = ?";

        try (Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, id);

            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                autoria = new Autoria(id, nombre, apellido);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return autoria;
    }

    public static int update(Autoria a){
        int filas = -1;
        String sql = "update autoria set nombre = ?, apellido = ? where id = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, a.getNombre());
            p.setString(2, a.getApellido());
            p.setString(3, a.getId());

            filas = p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return filas;
    }

    public static int delete(String id){
        int filas = -1;
        String sql = "delete from autoria where id = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, id);

            filas = p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return filas;
    }

    //OPERACIONES EXTRA

    public static ArrayList<Autoria> readAll(){
        ArrayList<Autoria> autorias = new ArrayList<>();
        String sql = "select * from autoria";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            ResultSet rs = p.executeQuery();
            if(rs.next()){

                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                Autoria autoria = new Autoria(id, nombre, apellido);
                autorias.add(autoria);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
        }

        return autorias;
    }

    public static int createOrUpdateAll(ArrayList<Autoria> a){
        int filas = -1;
        //TODO
        return filas;
    }
}
