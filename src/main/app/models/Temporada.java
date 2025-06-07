package main.app.models;

public class Temporada {
    private int idTemporada;
    private int idContenido;
    private int numero;
    private String titulo;

    public Temporada(int idTemporada, int idContenido, int numero, String titulo) {
        this.idTemporada = idTemporada;
        this.idContenido = idContenido;
        this.numero = numero;
        this.titulo = titulo;
    }

    public int getIdTemporada() {
        return idTemporada;
    }

    public void setIdTemporada(int idTemporada) {
        this.idTemporada = idTemporada;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
