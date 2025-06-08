package main.app.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.app.database.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileSelectionController {
    @FXML
    private HBox profilesHBox;

    @FXML
    private HBox profilesContainer;

    @FXML
    private VBox crearPerfilForm;

    @FXML
    private TextField nombrePerfilField;

    private int idCuenta;

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
        cargarPerfiles();
    }

    private void cargarPerfiles() {
        String query = "SELECT id_perfil, nombre, avatar_url FROM perfiles WHERE id_cuenta = ?";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idCuenta);
            ResultSet resultSet = statement.executeQuery();

            profilesHBox.getChildren().clear(); 

            while (resultSet.next()) {
                int idPerfil = resultSet.getInt("id_perfil");
                String nombrePerfil = resultSet.getString("nombre");
                String imagenUrl = resultSet.getString("avatar_url");

                // Crear un VBox para contener la imagen y el nombre del perfil
                VBox profileBox = new VBox();
                profileBox.setSpacing(5);
                profileBox.setStyle("-fx-alignment: center;");

                // Crear un ImageView para la foto del perfil
                ImageView avatarImageView = new ImageView();
                avatarImageView.setFitWidth(100);
                avatarImageView.setFitHeight(100);
                avatarImageView.setPreserveRatio(true);

                try {
                    avatarImageView.setImage(new Image(getClass().getResource(imagenUrl).toExternalForm()));
                } catch (Exception e) {
                    System.err.println("Error al cargar la imagen del perfil: " + e.getMessage());
                }

                // Crear un botón para seleccionar el perfil
                Button perfilButton = new Button(nombrePerfil);
                perfilButton.setOnAction(event -> seleccionarPerfil(idPerfil));
                perfilButton.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 10 20; -fx-font-size: 14px;");

                // Agregar la imagen y el botón al VBox
                profileBox.getChildren().addAll(avatarImageView, perfilButton);

                // Agregar el VBox al HBox principal
                profilesHBox.getChildren().add(profileBox);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar perfiles: " + e.getMessage());
        }
    }

    private void seleccionarPerfil(int idPerfil) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/app/views/Dashboard.fxml"));
            Parent root = loader.load();

            // Configurar el controlador del Dashboard
            DashboardController controller = loader.getController();
            controller.setIdPerfilActual(idPerfil);

            // Mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) profilesHBox.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            System.err.println("Error al cargar el Dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarFormularioCrearPerfil() {
        crearPerfilForm.setVisible(true);
        crearPerfilForm.setManaged(true);
    }

    @FXML
    private void ocultarFormularioCrearPerfil() {
        crearPerfilForm.setVisible(false);
        crearPerfilForm.setManaged(false);
    }

    @FXML
    private void crearPerfil() {
        String nombrePerfil = nombrePerfilField.getText();

        if (nombrePerfil == null || nombrePerfil.trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre del perfil no puede estar vacío.");
            return;
        }

        try (Connection connection = Conexion.getConnection()) {
            // Generar un nuevo ID único para el perfil si no es autoincremental
            int nuevoIdPerfil = generarNuevoIdPerfil(connection);

            String query = "INSERT INTO perfiles (id_perfil, id_cuenta, nombre, avatar_url) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, nuevoIdPerfil);
                statement.setInt(2, idCuenta);
                statement.setString(3, nombrePerfil);
                statement.setString(4, "/main/app/views/img/perfil.jpg"); // Asignar la imagen predeterminada
                statement.executeUpdate();
            }

            mostrarAlerta("Éxito", "Perfil creado exitosamente.");
            ocultarFormularioCrearPerfil();
            cargarPerfiles(); // Recargar los perfiles
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear el perfil: " + e.getMessage());
        }
    }

    private int generarNuevoIdPerfil(Connection connection) throws Exception {
        String query = "SELECT MAX(id_perfil) + 1 AS nuevo_id FROM perfiles";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("nuevo_id");
            } else {
                throw new Exception("No se pudo generar un nuevo ID para el perfil.");
            }
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
