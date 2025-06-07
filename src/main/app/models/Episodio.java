package main.app.models;

public class Episodio {
    private int idEpisodio;
    private int idTemporada;
    private String titulo;
    private String descripcion;
    private int duracion;
    private int numero;

    public Episodio(int idEpisodio, int idTemporada, String titulo, String descripcion, int duracion, int numero) {
        this.idEpisodio = idEpisodio;
        this.idTemporada = idTemporada;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.numero = numero;
    }

    public int getIdEpisodio() {
        return idEpisodio;
    }

    public void setIdEpisodio(int idEpisodio) {
        this.idEpisodio = idEpisodio;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
