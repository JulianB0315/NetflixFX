package main.app.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.app.database.Conexion;
import main.app.models.Contenido;
import main.app.models.Tendencia;
import main.utils.IAUtils;

public class DashboardService {

    public List<Tendencia> obtenerTendencias() throws Exception {
        return IAUtils.obtenerTendencias();
    }

    public List<Integer> generarRecomendaciones(int idPerfil) throws Exception {
        return IAUtils.generarRecomendaciones(idPerfil);
    }

    public List<Contenido> obtenerContenidosPorIds(List<Integer> ids) throws Exception {
        if (ids.isEmpty()) return new ArrayList<>();

        String query = "SELECT * FROM contenidos WHERE id_contenido IN (" +
                       String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new)) +
                       ")";

        List<Contenido> contenidos = new ArrayList<>();
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

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
        }
        return contenidos;
    }

    public List<Contenido> obtenerPeliculasAleatorias(int limite) throws Exception {
        String query = "SELECT * FROM contenidos WHERE tipo = 'pelicula' ORDER BY RANDOM() LIMIT ?";
        List<Contenido> contenidos = new ArrayList<>();

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, limite);
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
        }
        return contenidos;
    }

    public List<Contenido> generarParaTi(int idPerfil) throws Exception {
        List<Integer> paraTiIds = IAUtils.generarParaTi(idPerfil);
        return obtenerContenidosPorIds(paraTiIds);
    }
}
