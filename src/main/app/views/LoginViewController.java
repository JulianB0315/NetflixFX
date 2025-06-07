package main.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.app.database.Conexion;
import main.app.models.Cuenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginViewController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa tu correo y contraseña.");
            return;
        }

        try (Connection connection = Conexion.getConnection()) {
            if (connection == null) {
                mostrarAlerta("Error", "No se pudo conectar a la base de datos.");
                return;
            }

            String query = "SELECT * FROM cuentas WHERE correo = ? AND contrasena = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Cuenta cuenta = new Cuenta(
                    resultSet.getInt("id_cuenta"),
                    resultSet.getString("correo"),
                    resultSet.getString("contrasena"),
                    resultSet.getDate("fecha_registro").toLocalDate(),
                    resultSet.getInt("id_rol")
                );
                mostrarAlerta("Éxito", "Inicio de sesión exitoso para: " + cuenta.getCorreo());
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        cerrarVentana();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.close();
    }
}
