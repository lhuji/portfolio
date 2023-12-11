package org.biblioteca.controlador;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.biblioteca.modelo.ConexionPool;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioControlador {

    public TextField usernameField;
    public PasswordField passwordField;
    private String nombreUsuario;
    private Stage primaryStage;
    private Node root;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = primaryStage.getScene();
        if (scene != null) {
            this.root = scene.getRoot();
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public boolean validarCredenciales() {
        String nombreUsuario = usernameField.getText();
        String contrasena = passwordField.getText();

        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nombre_usuario=?")) {

            stmt.setString(1, nombreUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("contrasena");
                    boolean credencialesValidas = BCrypt.checkpw(contrasena, hashGuardado);

                    if (credencialesValidas) {
                        Platform.runLater(() -> {
                            mostrarAlerta("Inicio de Sesión Correcto", "Bienvenido " + nombreUsuario);
                            abrirVentanaMenu(nombreUsuario);
                        });
                        return true;
                    } else {
                        mostrarAlertaError("Credenciales Incorrectas", "Usuario o contraseña incorrectos");
                    }
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void abrirVentanaMenu(String nombreUsuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/menu.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Mi Empresa");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            Stage actualStage = (Stage) usernameField.getScene().getWindow();
            actualStage.close();

            // Obtén el controlador del nuevo menú
            MenuControlador menuControlador= loader.getController();


            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void mostrarRegistrar() {
        try {
            root.setDisable(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/registrarUsuario.fxml"));
            Parent registrarRoot = loader.load();

            RegistrarUsuarioController registrarUsuarioController = loader.getController();
            registrarUsuarioController.setUsuarioControlador(this);

            Scene scene = new Scene(registrarRoot);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Usuario");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void habilitarVentanaPrincipal() {
        root.setDisable(false);
    }
}
