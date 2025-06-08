package main.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.app.database.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DetallePeliculaController {
    @FXML
    private ImageView peliculaImage;

    @FXML
    private Label peliculaTitle;

    @FXML
    private Label peliculaDescription;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Label ratingLabel;

    private int idPerfilActual;
    private int idContenido;

    public void setPeliculaData(int idPerfil, int idContenido, String imageUrl, String titulo, String descripcion) {
        this.idPerfilActual = idPerfil;
        this.idContenido = idContenido;

        if (imageUrl != null && !imageUrl.isEmpty()) {
            peliculaImage.setImage(new Image(imageUrl));
        } else {
            // Asegúrate de que la ruta al archivo de imagen predeterminada sea válida
            try {
                String defaultImagePath = getClass().getResource("/main/assets/default-movie.png").toExternalForm();
                peliculaImage.setImage(new Image(defaultImagePath));
            } catch (NullPointerException e) {
                System.err.println("Error: No se pudo cargar la imagen predeterminada. Verifica la ruta '/main/assets/default-movie.png'.");
            }
        }

        peliculaTitle.setText(titulo);
        peliculaDescription.setText(descripcion);

        // Agregar el icono a la ventana después de que la escena esté inicializada
        peliculaImage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                    if (newWindow instanceof Stage stage) {
                        stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm()));
                    }
                });
            }
        });
    }

    @FXML
    private void initialize() {
        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingLabel.setText(String.valueOf(newValue.intValue()));
        });
    }

    @FXML
    private void guardarCalificacion() {
        int calificacion = (int) ratingSlider.getValue();

        String query = """
            INSERT INTO calificaciones (id_perfil, id_contenido, puntuacion, fecha)
            VALUES (?, ?, ?, CURRENT_TIMESTAMP)
            ON CONFLICT (id_perfil, id_contenido)
            DO UPDATE SET puntuacion = EXCLUDED.puntuacion, fecha = CURRENT_TIMESTAMP
        """;

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idPerfilActual);
            statement.setInt(2, idContenido);
            statement.setInt(3, calificacion);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                mostrarAlerta("Éxito", "Calificación guardada correctamente.");
            } else {
                mostrarAlerta("Error", "No se pudo guardar la calificación.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar la calificación: " + e.getMessage());
        }
    }

    @FXML
    private void marcarComoVisto() {
        // Implementar lógica para marcar como visto
        mostrarAlerta("Visto", "Has marcado esta película como vista.");
    }

    @FXML
    private void agregarAFavoritos() {
        // Implementar lógica para agregar a favoritos
        mostrarAlerta("Favorito", "Has agregado esta película a tus favoritos.");
    }

    @FXML
    private void abrirCalificacion() {
        // Implementar lógica para abrir la ventana de calificación
        mostrarAlerta("Calificar", "Abrir ventana para calificar esta película.");
    }

    @FXML
    private void handleCancel() {
        // Cierra la ventana actual
        Stage stage = (Stage) peliculaImage.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
