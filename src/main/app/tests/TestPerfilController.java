package main.app.tests;

import java.util.List;
import main.app.controllers.PerfilController;
import main.app.models.Perfil;

public class TestPerfilController {
    public static void main(String[] args) {
        PerfilController perfilController = new PerfilController();
        List<Perfil> perfiles = perfilController.obtenerPerfilesPorCuenta(1);

        for (Perfil perfil : perfiles) {
            System.out.println("Perfil: " + perfil.getNombre());
        }
    }
}
