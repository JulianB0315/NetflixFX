package main.app.models;

import java.time.LocalDateTime;

public class Favorito {
    private int idPerfil;
    private int idContenido;
    private LocalDateTime fechaAgregado;

    public Favorito(int idPerfil, int idContenido, LocalDateTime fechaAgregado) {
        this.idPerfil = idPerfil;
        this.idContenido = idContenido;
        this.fechaAgregado = fechaAgregado;
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

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
