package main.app.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import main.app.database.Conexion;
import main.app.models.Perfil;

public class PerfilController {
    public List<Perfil> obtenerPerfilesPorCuenta(int idCuenta) {
        List<Perfil> perfiles = new ArrayList<>();
        String query = "SELECT * FROM perfiles WHERE id_cuenta = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCuenta);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Perfil perfil = new Perfil(
                    resultSet.getInt("id_perfil"),
                    resultSet.getInt("id_cuenta"),
                    resultSet.getString("nombre"),
                    resultSet.getInt("edad"),
                    resultSet.getString("genero"),
                    resultSet.getString("avatar_url")
                );
                perfiles.add(perfil);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener perfiles: " + e.getMessage());
        }

        return perfiles;
    }
}
