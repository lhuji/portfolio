package com.practicag4.ddi_2practica_g4;

import com.practicag4.ddi_2practica_g4.Controller.CuentaBater;
import com.practicag4.ddi_2practica_g4.Model.Cuenta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CuentaBater c = new CuentaBater();
        List<Cuenta> cuentas = c.obtenerCuentasDesdeBD (); // Carga las cuentas antes de mostrar la ventana

        // Cargar la imagen como recurso desde el classpath
        InputStream iconStream = Main.class.getResourceAsStream("/image/logo2.png");
        Image iconImage = new Image(iconStream);

        // Agregar la imagen como icono de la ventana
        stage.getIcons().add(iconImage);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/practicag4/ddi_2practica_g4/view/Vista.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PORKYBANK");
        stage.setScene(scene);
        CuentaBater cuentaBater = fxmlLoader.getController();
        cuentaBater.setCuentas(cuentas);

        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
