package main.app.models;

public class Actor {
    private int idActor;
    private String nombre;

    public Actor(int idActor, String nombre) {
        this.idActor = idActor;
        this.nombre = nombre;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
