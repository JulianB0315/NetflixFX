package main.app.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.app.database.Conexion;
import main.app.models.Contenido;

public class ContenidoController {
    public List<Contenido> obtenerContenidosPorCategoria(String categoria) {
        List<Contenido> contenidos = new ArrayList<>();
        String query = """
            SELECT c.* 
            FROM contenidos c
            JOIN contenido_categoria cc ON c.id_contenido = cc.id_contenido
            JOIN categorias cat ON cc.id_categoria = cat.id_categoria
            WHERE cat.nombre = ?;
        """;

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categoria);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Contenido contenido = new Contenido(
                    resultSet.getInt("id_contenido"),
                    resultSet.getString("titulo"),
                    resultSet.getString("descripcion"),
                    resultSet.getString("imagen_url"),
                    resultSet.getString("tipo"),
                    resultSet.getInt("anio")
                );
                contenidos.add(contenido);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener contenidos: " + e.getMessage());
        }

        return contenidos;
    }

    public List<Contenido> cargarContenidos() {
        List<Contenido> contenidos = new ArrayList<>();
        String query = "SELECT id_contenido, titulo, descripcion, imagen_url, categoria, duracion FROM contenidos";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_contenido");
                String titulo = resultSet.getString("titulo");
                String descripcion = resultSet.getString("descripcion");
                String imagenUrl = resultSet.getString("imagen_url");
                String categoria = resultSet.getString("categoria");
                int duracion = resultSet.getInt("duracion");

                // Crear un objeto Contenido
                Contenido contenido = new Contenido(id, titulo, descripcion, imagenUrl, categoria, duracion);

                // Agregar a la lista
                contenidos.add(contenido);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los contenidos: " + e.getMessage());
        }

        return contenidos;
    }
}
