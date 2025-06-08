package main.app.views;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.utils.IAUtils;

import java.util.List;

public class HistorialViewController {

    @FXML
    private ListView<String> historialListView;

    @FXML
    public void initialize() {
        // Cargar el historial después de que la escena esté completamente inicializada
        historialListView.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                try {
                    System.out.println("Cargando historial...");
                    List<String> historial = IAUtils.obtenerHistorialVisualizaciones(1); // Cambiar 1 por el ID del perfil actual
                    historialListView.getItems().addAll(historial);
                    System.out.println("Historial cargado correctamente.");

                    // Agregar el icono a la ventana
                    newScene.windowProperty().addListener((obs, oldWindow, newWindow) -> {
                        if (newWindow instanceof Stage stage) {
                            stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm()));
                        }
                    });
                } catch (Exception e) {
                    System.err.println("Error al cargar el historial: " + e.getMessage());
                }
            }
        });
    }
}
