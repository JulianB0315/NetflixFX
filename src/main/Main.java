package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/main/app/views/MainView.fxml"));
        Scene scene = new Scene(root);
        // Depuración: verifica si el CSS existe
        java.net.URL cssUrl = getClass().getResource("css/styles.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("No se encontró el archivo CSS.");
        }
        stage.setScene(scene);
        stage.setTitle("NetfixFX");
        // Configurar el ícono de la aplicación
        java.net.URL iconUrl = getClass().getResource("/main/app/views/img/icon.jpg");
        if (iconUrl != null) {
            stage.getIcons().add(new javafx.scene.image.Image(iconUrl.toExternalForm()));
        } else {
            System.err.println("No se encontró el ícono de la aplicación.");
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}

