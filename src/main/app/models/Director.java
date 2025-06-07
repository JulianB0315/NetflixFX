package main.app.models;

public class Director {
    private int idDirector;
    private String nombre;

    public Director(int idDirector, String nombre) {
        this.idDirector = idDirector;
        this.nombre = nombre;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
