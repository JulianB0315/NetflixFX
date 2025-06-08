package main.app.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.app.database.Conexion;
import main.app.models.Contenido;
import main.app.models.Tendencia;
import main.app.services.DashboardService;
import main.utils.IAUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class DashboardController {

    @FXML
    private ImageView heroImage;

    @FXML
    private Label heroTitle;

    @FXML
    private Label heroDescription;

    @FXML
    private HBox tendenciasHBox;

    @FXML
    private HBox paraTiHBox;

    @FXML
    private HBox cualquierPeliculaHBox;

    private int idPerfilActual;
    private DashboardService dashboardService = new DashboardService();

    public void setIdPerfilActual(int idPerfilActual) {
        this.idPerfilActual = idPerfilActual;
        cargarHero();
        cargarTendencias();
        cargarParaTi();
        cargarCualquierPelicula();
    }

    private void cargarHero() {
        String query = "SELECT * FROM contenidos ORDER BY RANDOM() LIMIT 1";

        try (Connection connection = Conexion.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                String imageUrl = resultSet.getString("imagen_url");
                String titulo = resultSet.getString("titulo");
                String descripcion = resultSet.getString("descripcion");
                int anio = resultSet.getInt("anio");
                Integer duracion = resultSet.getObject("duracion", Integer.class); // Puede ser null para series
                String tipo = resultSet.getString("tipo");

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    heroImage.setImage(new Image(imageUrl));
                } else {
                    heroImage.setImage(new Image(getClass().getResource("/main/assets/default-hero.png").toExternalForm()));
                }

                heroTitle.setText(titulo != null ? titulo : "Título no disponible");
                heroDescription.setText(
                        (descripcion != null ? descripcion : "Descripción no disponible.")
                        + "\n\n"
                        + "Año: " + anio
                        + (duracion != null ? " | Duración: " + duracion + " min" : "")
                        + " | Tipo: " + (tipo != null ? tipo : "Desconocido")
                );
            }
        } catch (Exception e) {
            System.err.println("Error al cargar la sección Hero: " + e.getMessage());
        }
    }

    private void cargarTendencias() {
        try {
            // Obtener las tendencias desde el servicio
            List<Tendencia> tendencias = dashboardService.obtenerTendencias();

            // Limpiar el HBox antes de cargar nuevos contenidos
            tendenciasHBox.getChildren().clear();

            // Mostrar las tendencias
            int rank = 1;
            for (Tendencia tendencia : tendencias) {
                VBox contentBox = new VBox();
                contentBox.setSpacing(5);
                contentBox.setStyle("-fx-alignment: center; -fx-background-color: #141414; -fx-border-radius: 10; -fx-background-radius: 10;");

                // Crear un Label para el número de ranking
                Label rankLabel = new Label("#" + rank);
                rankLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

                // Crear un ImageView para la imagen
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(220);
                imageView.setPreserveRatio(true);

                // Manejar la carga de la imagen
                String imageUrl = tendencia.getImagenUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageView.setImage(new Image(imageUrl, true));
                } else {
                    imageView.setImage(new Image(getClass().getResource("/main/assets/default-movie.png").toExternalForm()));
                }

                // Crear un Label para el título
                Label titleLabel = new Label(tendencia.getTitulo());
                titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-wrap-text: true;");
                titleLabel.setMaxWidth(150);

                // Botón "Visto"
                Button vistoButton = new Button("Visto");
                vistoButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-padding: 5 10;");
                vistoButton.setOnAction(event -> marcarComoVisto(tendencia.getIdContenido()));

                // Botón "Favorito"
                Button favoritoButton = new Button("Favorito");
                favoritoButton.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 5 10;");
                favoritoButton.setOnAction(event -> agregarAFavoritos(tendencia.getIdContenido()));

                // Botón "Calificación"
                Button calificarButton = new Button("Calificar");
                calificarButton.setStyle("-fx-background-color: #0073e6; -fx-text-fill: white; -fx-padding: 5 10;");
                calificarButton.setOnAction(event -> abrirCalificacion(tendencia.getIdContenido(), tendencia.getTitulo()));

                // Agregar los elementos al VBox
                contentBox.getChildren().addAll(rankLabel, imageView, titleLabel, vistoButton, favoritoButton, calificarButton);

                // Agregar el VBox al HBox
                tendenciasHBox.getChildren().add(contentBox);

                rank++;
            }
        } catch (Exception e) {
            System.err.println("Error al cargar tendencias: " + e.getMessage());
        }
    }

    private void cargarParaTi() {
        try {
            // Obtener las recomendaciones "Para ti" desde el servicio
            List<Contenido> contenidos = dashboardService.generarParaTi(idPerfilActual);

            // Limpiar el HBox antes de cargar nuevos contenidos
            paraTiHBox.getChildren().clear();

            // Mostrar los contenidos
            for (Contenido contenido : contenidos) {
                VBox contentBox = new VBox();
                contentBox.setSpacing(5);
                contentBox.setStyle("-fx-alignment: center; -fx-background-color: #141414; -fx-border-radius: 10; -fx-background-radius: 10;");

                // Crear un ImageView para la imagen
                ImageView imageView = new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(220);
                imageView.setPreserveRatio(true);

                // Manejar la carga de la imagen
                String imageUrl = contenido.getImagenUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    imageView.setImage(new Image(imageUrl, true));
                } else {
                    imageView.setImage(new Image(getClass().getResource("/main/assets/default-movie.png").toExternalForm()));
                }

                // Crear un Label para el título
                Label titleLabel = new Label(contenido.getTitulo());
                titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-wrap-text: true;");
                titleLabel.setMaxWidth(150);

                // Botón "Visto"
                Button vistoButton = new Button("Visto");
                vistoButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-padding: 5 10;");
                vistoButton.setOnAction(event -> marcarComoVisto(contenido.getId()));

                // Botón "Favorito"
                Button favoritoButton = new Button("Favorito");
                favoritoButton.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 5 10;");
                favoritoButton.setOnAction(event -> agregarAFavoritos(contenido.getId()));

                // Botón "Calificación"
                Button calificarButton = new Button("Calificar");
                calificarButton.setStyle("-fx-background-color: #0073e6; -fx-text-fill: white; -fx-padding: 5 10;");
                calificarButton.setOnAction(event -> abrirCalificacion(contenido.getId(), contenido.getTitulo()));

                // Agregar los elementos al VBox
                contentBox.getChildren().addAll(imageView, titleLabel, vistoButton, favoritoButton, calificarButton);

                // Agregar el VBox al HBox
                paraTiHBox.getChildren().add(contentBox);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar recomendaciones 'Para ti': " + e.getMessage());
        }
    }

    private void cargarCualquierPelicula() {
        try {
            // Obtener los contenidos de tipo "película" directamente desde el servicio
            List<Contenido> contenidos = dashboardService.obtenerPeliculasAleatorias(10);
            cargarContenidoEnHBox(contenidos, cualquierPeliculaHBox);
        } catch (Exception e) {
            System.err.println("Error al cargar cualquier película: " + e.getMessage());
        }
    }

    private void agregarAFavoritos(int idContenido) {
        try (Connection connection = Conexion.getConnection()) {
            if (connection == null) {
                System.err.println("No se pudo conectar a la base de datos.");
                return;
            }

            String query = "INSERT INTO favoritos (id_perfil, id_contenido) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idPerfilActual);
                statement.setInt(2, idContenido);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Contenido agregado a favoritos.");
                } else {
                    System.err.println("No se pudo agregar el contenido a favoritos.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al agregar a favoritos: " + e.getMessage());
        }
    }

    private void marcarComoVisto(int idContenido) {
        String query = "INSERT INTO vistas (id_perfil, id_contenido) VALUES (?, ?)";

        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idPerfilActual); // Asegúrate de que idPerfilActual esté definido
            statement.setInt(2, idContenido);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Contenido marcado como visto: " + idContenido);
            } else {
                System.err.println("No se pudo marcar el contenido como visto.");
            }
        } catch (Exception e) {
            System.err.println("Error al marcar como visto: " + e.getMessage());
        }
    }

    private void cargarContenidoEnHBox(List<Contenido> contenidos, HBox hbox) {
        hbox.getChildren().clear();
        for (Contenido contenido : contenidos) {
            VBox contentBox = new VBox();
            contentBox.setSpacing(5);
            contentBox.setStyle("-fx-alignment: center; -fx-background-color: #141414; -fx-border-radius: 10; -fx-background-radius: 10;");

            ImageView imageView = new ImageView();
            imageView.setFitWidth(150);
            imageView.setFitHeight(220);
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image(contenido.getImagenUrl() != null ? contenido.getImagenUrl() : getClass().getResource("/main/assets/default-movie.png").toExternalForm()));

            Label titleLabel = new Label(contenido.getTitulo());
            titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-wrap-text: true;");
            titleLabel.setMaxWidth(150);

            Button favoritosButton = new Button("Favoritos");
            favoritosButton.setStyle("-fx-background-color: #e50914; -fx-text-fill: white; -fx-padding: 5 10;");
            favoritosButton.setOnAction(event -> agregarAFavoritos(contenido.getId()));

            Button vistoButton = new Button("Visto");
            vistoButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-padding: 5 10;");
            vistoButton.setOnAction(event -> marcarComoVisto(contenido.getId()));

            contentBox.getChildren().addAll(imageView, titleLabel, favoritosButton, vistoButton);
            hbox.getChildren().add(contentBox);
        }
    }

    private void abrirDetallePelicula(int idContenido, String imageUrl, String titulo, String descripcion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/app/views/DetallePeliculaView.fxml"));
            Parent root = loader.load();

            // Configurar el controlador de la vista de detalles
            DetallePeliculaController controller = loader.getController();
            controller.setPeliculaData(idPerfilActual, idContenido, imageUrl, titulo, descripcion);

            // Mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Detalles de la Película");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm())); // Agregar icono
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al cargar la vista de detalles de la película: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Agregar un listener para cargar el CSS después de que la escena esté configurada
        tendenciasHBox.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                try {
                    String cssPath = getClass().getResource("/main/css/dasboard.css").toExternalForm();
                    if (cssPath != null) {
                        newScene.getStylesheets().add(cssPath);
                        System.out.println("CSS cargado correctamente: " + cssPath);
                    } else {
                        System.err.println("No se pudo cargar el archivo CSS: /main/css/dasboard.css");
                    }
                } catch (Exception e) {
                    System.err.println("Error al cargar el CSS: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void recargarDashboard() {
        try {
            // Obtener la ventana actual
            Stage currentStage = (Stage) tendenciasHBox.getScene().getWindow();

            // Cargar el archivo FXML del Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/app/views/Dashboard.fxml"));
            Parent root = loader.load();

            // Configurar la nueva escena
            Stage newStage = new Stage();
            newStage.setTitle("Dashboard");
            newStage.setScene(new Scene(root));
            newStage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm())); // Agregar icono

            // Pasar el ID del perfil al nuevo controlador
            DashboardController controller = loader.getController();
            controller.setIdPerfilActual(idPerfilActual);

            // Mostrar la nueva ventana y cerrar la actual
            newStage.show();
            currentStage.close();
        } catch (Exception e) {
            System.err.println("Error al recargar el Dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirCalificacion(int idContenido, String titulo) {
        try {
            // Asegúrate de que la ruta al archivo FXML sea correcta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/app/views/DetallePeliculaView.fxml"));
            if (loader.getLocation() == null) {
                throw new IllegalArgumentException("No se pudo encontrar el archivo FXML: DetallePeliculaView.fxml");
            }

            Parent root = loader.load();

            // Configurar el controlador de la ventana de calificación
            DetallePeliculaController controller = loader.getController();
            controller.setPeliculaData(idPerfilActual, idContenido, null, titulo, "Descripción de la película");

            // Mostrar la ventana de calificación
            Stage stage = new Stage();
            stage.setTitle("Calificar " + titulo);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm())); // Agregar icono
            stage.show();
        } catch (Exception e) {
            System.err.println("Error al abrir la ventana de calificación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void abrirHistorial() {
        try {
            System.out.println("Intentando abrir la vista de historial...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/app/views/HistorialView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Historial de Visualizaciones");
            stage.setScene(new Scene(root));

            // Agregar el icono a la ventana
            stage.getIcons().add(new Image(getClass().getResource("/main/app/views/img/icon.jpg").toExternalForm()));

            stage.show();
            System.out.println("Vista de historial abierta correctamente.");
        } catch (Exception e) {
            System.err.println("Error al abrir la vista de historial: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
