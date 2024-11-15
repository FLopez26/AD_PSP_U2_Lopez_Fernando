package dao;

import clases.Autoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoriaDAO {

    //OPERACIONES CRUD

    /**
     * Añade una Autoria en la BBDD. Los datos a añadir los recoge de la Autoria que recibe por parámetro.
     * @param a
     * @return
     */
    public static int create(Autoria a) throws SQLException{
        int filas = -1;

        //Sentencia sql
        String sql = "insert into autoria " +
                "(id, nombre, apellido)" +
                "values (?, ?, ?)";

        Connection c = Conexion.conectar();
        PreparedStatement s = c.prepareStatement(sql);

        //Inserción de datos en la sentencia
        s.setInt(1, a.getId());
        s.setString(2, a.getNombre());
        s.setString(3, a.getApellido());

        filas = s.executeUpdate();

        return filas;
    }

    /**
     * Lee la Autoria de la BBDD que coincida con el id pasado por parámetro.
     * @param id
     * @return
     */
    public static Autoria read(int id) throws SQLException{
        //Inicializo una Autoria valiendo null de tal forma que si no existe una Autoria con ese id,
        //devolverá uno con valor null
        Autoria autoria = null;

        //Sentencia sql
        String sql = "select * from autoria where id = ?";

        Connection c = Conexion.conectar();
        PreparedStatement p = c.prepareStatement(sql);

        //Inserción de datos en la sentencia
        p.setInt(1, id);

        ResultSet rs = p.executeQuery();
        if(rs.next()){

            //Recojo los valores y se lo asigno a variables para luego crear la Autoria
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");

            autoria = new Autoria(id, nombre, apellido);
        }

        return autoria;
    }

    /**
     * Actualiza los datos de una Autoria de la BBDD con otra pasada por parámetro.
     * @param a
     * @return
     */
    public static int update(Autoria a) throws SQLException{
        int filas = -1;

        //Sentencia sql
        String sql = "update autoria set nombre = ?, apellido = ? where id = ?";

        Connection c = Conexion.conectar();
        PreparedStatement p = c.prepareStatement(sql);

        //Inserción de datos en la sentencia
        p.setString(1, a.getNombre());
        p.setString(2, a.getApellido());
        p.setInt(3, a.getId());

        filas = p.executeUpdate();

        return filas;
    }

    /**
     * Elimina la Autoria de la BBDD que coincida con el id pasado por parámetro.
     * @param id
     * @return
     */
    public static int delete(int id) throws SQLException{
        int filas = -1;

        //Sentencia sql
        String sql = "delete from autoria where id = ?";

        Connection c = Conexion.conectar();
        PreparedStatement p = c.prepareStatement(sql);

        //Inserción de datos en la sentencia
        p.setInt(1, id);

        filas = p.executeUpdate();

        return filas;
    }

    //OPERACIONES EXTRA

    /**
     * Lee todas las Autoria de la BBDD y las devuleve en un ArrayList.
     * @return
     */
    public static ArrayList<Autoria> readAll() throws SQLException{
        ArrayList<Autoria> autorias = new ArrayList<>();

        //Sentencia sql
        String sql = "select * from autoria";

        Connection c = Conexion.conectar();
        PreparedStatement p = c.prepareStatement(sql);

        ResultSet rs = p.executeQuery();
        while(rs.next()){

            //Recojo los valores y se lo asigno a variables para luego crear la Autoria y añadirla al ArrayList
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");

            Autoria autoria = new Autoria(id, nombre, apellido);
            autorias.add(autoria);
        }

        return autorias;
    }

    /**
     * Al recibir un ArrayList de Autorias por parámetro lo recorre y hace dos posibles opciones:
     * 1.- Si al comprobar el id la Autoria no existe, la crea.
     * 2.- Si la Autoria existe, la actualiza.
     * Devuelve un int que valdrá la cantidad de cambios que se hayan realizado.
     * @param a
     * @return
     */
    public static int createOrUpdateAll(ArrayList<Autoria> a) throws SQLException{
        int filas = 0;
        ArrayList<Autoria> autorias = a;

        //Recorro las Autoria del ArrayList
        for(Autoria autoriaArray : autorias){

            //Llamo a la función read() para que me devuelva la Autoria completa teniendo solo el id
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
