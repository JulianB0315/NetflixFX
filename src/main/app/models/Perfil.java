package main.app.models;

public class Perfil {
    private int idPerfil;
    private int idCuenta;
    private String nombre;
    private int edad;
    private String genero;
    private String avatarUrl;

    public Perfil(int idPerfil, int idCuenta, String nombre, int edad, String genero, String avatarUrl) {
        this.idPerfil = idPerfil;
        this.idCuenta = idCuenta;
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.avatarUrl = avatarUrl;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
