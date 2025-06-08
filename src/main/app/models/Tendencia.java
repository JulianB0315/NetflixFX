package main.app.models;

public class Tendencia {
    private int idContenido;
    private String titulo;
    private double promedioCalificaciones;
    private int cantidadCalificaciones;
    private double promedioPonderado;
    private String imagenUrl; // Nueva propiedad para la URL de la imagen

    public Tendencia(int idContenido, String titulo, double promedioCalificaciones, int cantidadCalificaciones, double promedioPonderado, String imagenUrl) {
        this.idContenido = idContenido;
        this.titulo = titulo;
        this.promedioCalificaciones = promedioCalificaciones;
        this.cantidadCalificaciones = cantidadCalificaciones;
        this.promedioPonderado = promedioPonderado;
        this.imagenUrl = imagenUrl;
    }

    // Getters
    public int getIdContenido() { return idContenido; }
    public String getTitulo() { return titulo; }
    public double getPromedioCalificaciones() { return promedioCalificaciones; }
    public int getCantidadCalificaciones() { return cantidadCalificaciones; }
    public double getPromedioPonderado() { return promedioPonderado; }
    public String getImagenUrl() { return imagenUrl; } // Getter para la URL de la imagen
}
