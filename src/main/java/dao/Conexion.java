package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    /**
     * Función para conectar con la BBDD "biblioteca".
     * @return
     * @throws SQLException
     */
    protected static Connection conectar() throws SQLException{
        String user = "root";
        String pass = "Sandia4you";
        String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false";
        Connection con = DriverManager.getConnection(url, user, pass);
        return con;
    }

    /**
     * Función para crear las tablas "autoria" y "libro" en la BBDD.
     * @return
     */
    public static boolean crearTablas() throws SQLException{

        //Sentencia sql para crear la tabla "autoria"
        String sqlAu = "create table if not exists autoria " +
                "(id int primary key," +
                "nombre varchar(50) not null," +
                "apellido varchar(50) not null);";

        Connection c = conectar();
        Statement st = c.createStatement();
        st.executeUpdate(sqlAu);

        //Sentencia sql para crear la tabla "libro"
        String sqlLi = "create table if not exists libro " +
                "(isbn varchar(15) primary key," +
                "titulo varchar(50) not null," +
                "autoria_id int not null," +
                "foreign key (autoria_id) references autoria(id) );";

        st = c.createStatement();
        st.executeUpdate(sqlLi);

        return false;
    }
}
