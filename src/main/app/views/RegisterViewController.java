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
import java.time.LocalDate;

public class RegisterViewController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() {
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

            String query = "INSERT INTO cuentas (correo, contrasena, fecha_registro, id_rol) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(4, 2); // Asignar un rol predeterminado, por ejemplo, "Usuario"

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                mostrarAlerta("Éxito", "Registro exitoso.");
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo completar el registro.");
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
