/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.app.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Julian
 */
public class Conexion {
    private static final String URL = "dpg-d11ltk63jp1c73f4kgh0-a.oregon-postgres.render.com";
    private static final String USER = "netflixfx_user";
    private static final String PASSWORD = "6A5xod7t3CWsQLWPpiSn0pHfPQqNArcZ";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
