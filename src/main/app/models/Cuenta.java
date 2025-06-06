package main.app.models;

import java.time.LocalDate;

public class Cuenta {
    private int idCuenta;
    private String correo;
    private String contrasena;
    private LocalDate fechaRegsitro;
    private int idRol;

    public Cuenta(int idCuenta, String correo, String contrasena, LocalDate fechaRegsitro, int idRol) {
        this.idCuenta = idCuenta;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaRegsitro = fechaRegsitro;
        this.idRol = idRol;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDate getFechaRegsitro() {
        return fechaRegsitro;
    }

    public void setFechaRegsitro(LocalDate fechaRegsitro) {
        this.fechaRegsitro = fechaRegsitro;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}