package main.app.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.app.services.AuthService;

public class LoginViewController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private AuthService authService = new AuthService();

    @FXML
    public void initialize() {
        // Asegúrate de que el CSS se cargue después de que la escena esté configurada
        emailField.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                String cssPath = getClass().getResource("/main/css/login.css").toExternalForm();
                if (cssPath != null) {
                    newScene.getStylesheets().add(cssPath);
                } else {
                    System.err.println("No se pudo cargar el archivo CSS: login.css");
                }

                // Agregar el icono a la ventana
                newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow instanceof Stage stage) {
                        stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm()));
                    }
                });
            }
        });
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa tu correo y contraseña.");
            return;
        }

        try {
            int idCuenta = authService.autenticar(email, password);
            abrirSeleccionPerfil(idCuenta);
            cerrarVentana();
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al iniciar sesión: " + e.getMessage());
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

    private void abrirSeleccionPerfil(int idCuenta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/app/views/ProfileSelection.fxml"));
            Parent root = loader.load();

            // Pasar el ID de la cuenta al controlador de selección de perfil
            ProfileSelectionController controller = loader.getController();
            controller.setIdCuenta(idCuenta);

            Stage stage = new Stage();
            stage.setTitle("Seleccionar Perfil");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista de selección de perfil: " + e.getMessage());
        }
    }
}
