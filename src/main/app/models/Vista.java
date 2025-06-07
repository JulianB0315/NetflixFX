package main.app.models;

import java.time.LocalDateTime;

public class Vista {
    private int idPerfil;
    private int idContenido;
    private LocalDateTime fechaVista;

    public Vista(int idPerfil, int idContenido, LocalDateTime fechaVista) {
        this.idPerfil = idPerfil;
        this.idContenido = idContenido;
        this.fechaVista = fechaVista;
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

    public LocalDateTime getFechaVista() {
        return fechaVista;
    }

    public void setFechaVista(LocalDateTime fechaVista) {
        this.fechaVista = fechaVista;
    }
}
