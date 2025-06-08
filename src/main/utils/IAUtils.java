package main.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.app.database.Conexion;
import main.app.models.Tendencia;

public class IAUtils {

    public static List<Tendencia> obtenerTendencias() throws Exception {
        String queryTendencias = """
            SELECT c.id_contenido, c.titulo, c.imagen_url, 
                   AVG(cal.puntuacion) AS promedio_calificaciones,
                   COUNT(cal.puntuacion) AS cantidad_calificaciones,
                   (AVG(cal.puntuacion) * COUNT(cal.puntuacion)) AS promedio_ponderado
            FROM contenidos c
            LEFT JOIN calificaciones cal ON c.id_contenido = cal.id_contenido
            GROUP BY c.id_contenido, c.titulo, c.imagen_url
            HAVING COUNT(cal.puntuacion) > 0  -- Solo incluir contenidos con calificaciones
            ORDER BY promedio_ponderado DESC  -- Ordenar por promedio ponderado (descendente)
            LIMIT 10
        """;

        List<Tendencia> tendencias = new ArrayList<>();
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryTendencias)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idContenido = resultSet.getInt("id_contenido");
                String titulo = resultSet.getString("titulo");
                String imagenUrl = resultSet.getString("imagen_url"); // Obtener la URL de la imagen
                double promedioCalificaciones = resultSet.getDouble("promedio_calificaciones");
                int cantidadCalificaciones = resultSet.getInt("cantidad_calificaciones");
                double promedioPonderado = resultSet.getDouble("promedio_ponderado");

                // Crear un objeto Tendencia
                Tendencia tendencia = new Tendencia(idContenido, titulo, promedioCalificaciones, cantidadCalificaciones, promedioPonderado, imagenUrl);

                // Agregar a la lista
                tendencias.add(tendencia);
            }
        }

        return tendencias;
    }

    public static List<Integer> generarRecomendaciones(int idPerfil) throws Exception {
        String queryRecomendaciones = """
            SELECT c.id_contenido
            FROM contenidos c
            JOIN contenido_categoria cc ON c.id_contenido = cc.id_contenido
            WHERE cc.id_categoria IN (
                SELECT cc.id_categoria
                FROM vistas v
                JOIN contenido_categoria cc ON v.id_contenido = cc.id_contenido
                WHERE v.id_perfil = ?
                GROUP BY cc.id_categoria
                ORDER BY COUNT(*) DESC
                LIMIT 1
            )
            AND c.id_contenido NOT IN (
                SELECT v.id_contenido FROM vistas v WHERE v.id_perfil = ?
                UNION
                SELECT cal.id_contenido FROM calificaciones cal WHERE cal.id_perfil = ?
            )
            ORDER BY RANDOM()
            LIMIT 10
        """;

        List<Integer> recomendaciones = new ArrayList<>();
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryRecomendaciones)) {

            statement.setInt(1, idPerfil);
            statement.setInt(2, idPerfil);
            statement.setInt(3, idPerfil);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                recomendaciones.add(resultSet.getInt("id_contenido"));
            }
        }

        return recomendaciones;
    }

    public static List<Integer> generarParaTi(int idPerfil) throws Exception {
        String queryParaTi = """
        SELECT c.id_contenido
        FROM contenidos c
        JOIN contenido_categoria cc ON c.id_contenido = cc.id_contenido
        WHERE cc.id_categoria IN (
            SELECT cc.id_categoria
            FROM vistas v
            JOIN contenido_categoria cc ON v.id_contenido = cc.id_contenido
            WHERE v.id_perfil = ?
            UNION
            SELECT cc.id_categoria
            FROM favoritos f
            JOIN contenido_categoria cc ON f.id_contenido = cc.id_contenido
            WHERE f.id_perfil = ?
        )
        AND c.id_contenido NOT IN (
            SELECT v.id_contenido FROM vistas v WHERE v.id_perfil = ?
            UNION
            SELECT f.id_contenido FROM favoritos f WHERE f.id_perfil = ?
        )
        GROUP BY c.id_contenido
        ORDER BY RANDOM()
        LIMIT 10
    """;

        List<Integer> paraTiIds = new ArrayList<>();
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryParaTi)) {

            statement.setInt(1, idPerfil);
            statement.setInt(2, idPerfil);
            statement.setInt(3, idPerfil);
            statement.setInt(4, idPerfil);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                paraTiIds.add(resultSet.getInt("id_contenido"));
            }
        }

        return paraTiIds;
    }

    public static List<String> obtenerHistorialVisualizaciones(int idPerfil) throws Exception {
        String queryHistorial = """
        SELECT c.titulo
        FROM vistas v
        JOIN contenidos c ON v.id_contenido = c.id_contenido
        WHERE v.id_perfil = ?
        ORDER BY v.fecha_vista DESC
    """;

        List<String> historial = new ArrayList<>();
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(queryHistorial)) {

            statement.setInt(1, idPerfil);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                historial.add(resultSet.getString("titulo"));
            }
        }

        return historial;
    }
}
