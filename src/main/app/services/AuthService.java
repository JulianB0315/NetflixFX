package main.app.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import main.app.database.Conexion;

public class AuthService {

    public int autenticar(String email, String password) throws Exception {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Correo y contraseña son obligatorios.");
        }

        String query = "SELECT id_cuenta FROM cuentas WHERE correo = ? AND contrasena = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_cuenta");
            } else {
                throw new IllegalArgumentException("Correo o contraseña incorrectos.");
            }
        }
    }

    public void registrar(String email, String password) throws Exception {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Correo electrónico no válido.");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }

        String query = "INSERT INTO cuentas (correo, contrasena, fecha_registro, id_rol) VALUES (?, ?, ?, ?)";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, password);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(4, 2); // Rol de usuario por defecto

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new IllegalArgumentException("No se pudo completar el registro.");
            }
        }
    }
}
