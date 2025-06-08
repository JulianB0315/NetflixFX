package main.app.models;

public class Contenido {
    private int id;
    private String titulo;
    private String descripcion;
    private String imagenUrl;
    private String categoria;
    private int duracion;

    public Contenido(int id, String titulo, String descripcion, String imagenUrl, String categoria, int duracion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
        this.duracion = duracion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getDuracion() {
        return duracion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
}
