package main.app.database;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection connection = Conexion.getConnection();
        if (connection != null) {
            System.out.println("Conexión exitosa a la base de datos.");
        } else {
            System.out.println("Error al conectar con la base de datos.");
        }
    }
}
