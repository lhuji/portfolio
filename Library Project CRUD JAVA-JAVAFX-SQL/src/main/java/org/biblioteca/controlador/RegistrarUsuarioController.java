package org.biblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.biblioteca.modelo.ConexionPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrarUsuarioController {
    public TextField usernameField;
    public PasswordField passwordField;
    // Variable para la referencia al controlador principal
    private UsuarioControlador usuarioControlador;

    // Método para establecer la referencia al controlador principal
    public void setUsuarioControlador(UsuarioControlador usuarioControlador) {
        this.usuarioControlador = usuarioControlador;
    }
    private void cerrarRecursos( PreparedStatement stmt, Connection conn) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
        }
    }
    private void cerrarRecursos( ResultSet rs , PreparedStatement stmt, Connection conn) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
        }
        try {
            if (rs != null){
                rs.close ();
            }
        }
        catch ( SQLException e ) {
            throw new RuntimeException ( e );
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
        }
    }

    public boolean registrarUsuario(ActionEvent actionEvent) {
        String nombreUsuario = usernameField.getText();
        String contrasena = passwordField.getText();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Verificar si el usuario ya existe
            if (usuarioExiste(nombreUsuario)) {
                mostrarMensajeError("El usuario ya existe.");
                return false;
            }

            conn = ConexionPool.obtenerConexion();
            String hashContrasena = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            String query = "INSERT INTO usuarios (nombre_usuario, contrasena) VALUES (?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, hashContrasena);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarMensajeExito();
                cerrarVentanaActual();
                return true;
            } else {
                mostrarMensajeError("Error al registrar el usuario.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
            mostrarMensajeError("Error al registrar el usuario.");
            return false;
        } finally {
            cerrarRecursos(stmt, conn);
        }
    }

    private boolean usuarioExiste(String nombreUsuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet         rs   = null;

        try {
            conn = ConexionPool.obtenerConexion();
            String query = "SELECT * FROM usuarios WHERE nombre_usuario=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreUsuario);
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            cerrarRecursos(rs, stmt, conn);
        }
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro exitoso");
        alert.setHeaderText(null);
        alert.setContentText("El usuario se ha registrado con éxito.");
        alert.showAndWait();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
        // Habilitar la ventana principal
        if (usuarioControlador != null) {
            usuarioControlador.habilitarVentanaPrincipal();
        }
    }
}
