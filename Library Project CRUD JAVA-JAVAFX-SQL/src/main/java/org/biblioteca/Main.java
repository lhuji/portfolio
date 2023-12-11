package org.biblioteca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.biblioteca.controlador.UsuarioControlador;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 300, 256);

        primaryStage.setTitle("Libraría Luna Nueva");
        primaryStage.setScene(scene);

        // Obtén el controlador después de cargar el FXML y establece la escena y el escenario
        UsuarioControlador usuarioControlador = loader.getController();
        usuarioControlador.setPrimaryStage(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

