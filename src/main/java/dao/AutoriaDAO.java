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

            s.setInt(1, a.getId());
            s.setString(2, a.getNombre());
            s.setString(3, a.getApellido());

            filas = s.executeUpdate();

        } catch (SQLException e){
            System.err.println("Error de SQL en create Autoria: " + e.getMessage());
        }

        return filas;
    }

    public static Autoria read(int id){
        Autoria autoria = null;
        String sql = "select * from autoria where id = ?";

        try (Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1, id);

            ResultSet rs = p.executeQuery();
            if(rs.next()){
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                autoria = new Autoria(id, nombre, apellido);
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL en read Autoria: " + e.getMessage());
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
            p.setInt(3, a.getId());

            filas = p.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL en update Autoria: " + e.getMessage());
        }

        return filas;
    }

    public static int delete(int id){
        int filas = -1;
        String sql = "delete from autoria where id = ?";

        try(Connection c = Conexion.conectar()){
            PreparedStatement p = c.prepareStatement(sql);

            p.setInt(1, id);

            filas = p.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error de SQL en delete Autoria: " + e.getMessage());
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
            while(rs.next()){

                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                Autoria autoria = new Autoria(id, nombre, apellido);
                autorias.add(autoria);
            }

        } catch (SQLException e) {
            System.err.println("Error de SQL en readAll Autoria: " + e.getMessage());
        }

        return autorias;
    }

    public static int createOrUpdateAll(ArrayList<Autoria> a){
        int filas = 0;
        ArrayList<Autoria> autorias = a;
        for(Autoria autoriaArray : autorias){
             Autoria autoria = read(autoriaArray.getId());
             if(autoria == null){
                 create(autoriaArray);
                 filas++;
             } else{
                 update(autoriaArray);
                 filas++;
             }
        }
        return filas;
    }
}
