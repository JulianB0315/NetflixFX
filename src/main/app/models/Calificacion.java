package main.app.models;

import java.time.LocalDateTime;

public class Calificacion {
    private int idPerfil;
    private int idContenido;
    private int puntuacion;
    private LocalDateTime fecha;

    public Calificacion(int idPerfil, int idContenido, int puntuacion, LocalDateTime fecha) {
        this.idPerfil = idPerfil;
        this.idContenido = idContenido;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
