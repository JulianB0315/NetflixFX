package main.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.app.database.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SerieViewController {
    @FXML
    private ImageView serieImage;

    @FXML
    private Label serieTitle;

    @FXML
    private Label serieDescription;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Label ratingLabel;

    private int idPerfilActual;
    private int idSerieActual;

    public void setSerieData(int idPerfil, int idSerie, String imageUrl, String title, String description) {
        this.idPerfilActual = idPerfil;
        this.idSerieActual = idSerie;

        serieTitle.setText(title);
        serieDescription.setText(description);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            serieImage.setImage(new Image(imageUrl));
        } else {
            serieImage.setImage(new Image(getClass().getResource("/main/assets/default-movie.png").toExternalForm()));
        }

        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratingLabel.setText(String.valueOf(newValue.intValue()));
        });
    }

    @FXML
    private void guardarCalificacion() {
        int rating = (int) ratingSlider.getValue();

        String query = "INSERT INTO calificaciones (id_perfil, id_contenido, puntuacion, fecha) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idPerfilActual);
            statement.setInt(2, idSerieActual);
            statement.setInt(3, rating);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                mostrarAlerta("Éxito", "Tu calificación ha sido guardada.");
            } else {
                mostrarAlerta("Error", "No se pudo guardar la calificación.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar la calificación: " + e.getMessage());
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
