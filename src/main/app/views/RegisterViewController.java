package main.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.app.services.AuthService;

public class RegisterViewController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        // Agregar el icono a la ventana después de que la escena esté completamente inicializada
        emailField.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                String cssPath = getClass().getResource("/main/css/registro.css").toExternalForm();
                if (cssPath != null) {
                    newScene.getStylesheets().add(cssPath);
                } else {
                    System.err.println("No se pudo cargar el archivo CSS: registro.css");
                }

                newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow instanceof Stage stage) {
                        stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm()));
                    }
                });
            }
        });
    }

    @FXML
    private void handleRegister() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (email == null || email.isEmpty() || password == null || password.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            mostrarAlerta("Error", "Por favor, completa todos los campos.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden.");
            return;
        }

        try {
            authService.registrar(email, password);
            mostrarAlerta("Éxito", "Registro exitoso. Ahora puedes iniciar sesión.");
            cerrarVentana();
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al registrar: " + e.getMessage());
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
