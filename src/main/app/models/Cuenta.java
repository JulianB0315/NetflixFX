package main.app.models;

import java.time.LocalDate;

public class Cuenta {
    private int idCuenta;
    private String correo;
    private String contrasena;
    private LocalDate fechaRegistro; // Cambiado para reflejar el nombre correcto en la base de datos
    private int idRol;

    public Cuenta(int idCuenta, String correo, String contrasena, LocalDate fechaRegistro, int idRol) {
        this.idCuenta = idCuenta;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechaRegistro = fechaRegistro;
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

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}