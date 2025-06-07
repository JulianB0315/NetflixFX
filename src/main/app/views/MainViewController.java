package main.app.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainViewController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox carouselHBox;

    @FXML
    private TextField emailField;

    private static final double STEP = 0.25; // 3 de 12 elementos

    @FXML
    private void moverIzquierda() {
        scrollPane.setHvalue(Math.max(0, scrollPane.getHvalue() - STEP));
    }

    @FXML
    private void moverDerecha() {
        scrollPane.setHvalue(Math.min(1, scrollPane.getHvalue() + STEP));
    }

    @FXML
    public void initialize() {
        // Cargar imágenes dinámicamente en el HBox
        try {
            for (int i = 1; i <= 12; i++) { // Ajustado para incluir 12 imágenes
                String imagePath = String.format("/main/app/views/img/movie%d.jpg", i);
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setPreserveRatio(true);
                carouselHBox.getChildren().add(imageView);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
        }
    }

    @FXML
    private void iniciarSesion() {
        abrirVentana("/main/app/views/LoginView.fxml", "Iniciar Sesión");
    }

    @FXML
    private void registrarse() {
        abrirVentana("/main/app/views/RegisterView.fxml", "Registrarse");
    }

    private void abrirVentana(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al abrir la ventana: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
