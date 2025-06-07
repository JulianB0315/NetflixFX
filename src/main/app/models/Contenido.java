package main.app.models;

public class Contenido {
    private int idContenido;
    private String titulo;
    private String descripcion;
    private int anio;
    private Integer duracion; // Puede ser null para series
    private String tipo;
    private String imagenUrl;

    public Contenido(int idContenido, String titulo, String descripcion, int anio, Integer duracion, String tipo,
            String imagenUrl) {
        this.idContenido = idContenido;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.anio = anio;
        this.duracion = duracion;
        this.tipo = tipo;
        this.imagenUrl = imagenUrl;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
