package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    protected static Connection conectar() throws SQLException{
        String user = "root";
        String pass = "Sandia4you";
        String url = "jdbc:mysql://localhost:3306/biblioteca?useSSL=false";
        Connection con = DriverManager.getConnection(url, user, pass);
        return con;
    }

    public static boolean crearTablas(){
        String sqlAu = "create table if not exists autoria " +
                "(id varchar(50) primary key," +
                "nombre varchar(50) not null," +
                "apellido varchar(50) not null);";
        try(Connection c = conectar()){
            Statement st = c.createStatement();
            st.executeUpdate(sqlAu);

            String sqlLi = "create table if not exists libro " +
                    "(isbn varchar(15) primary key," +
                    "titulo varchar(50) not null," +
                    "autoria varchar(50) not null," +
                    "foreign key (autoria) references autoria(id) );";
            st = c.createStatement();
            st.executeUpdate(sqlLi);

        } catch (SQLException e) {
            e.printStackTrace(); //TODO: buen mensaje de error
            return false;
        }
        return false;
    }
}
