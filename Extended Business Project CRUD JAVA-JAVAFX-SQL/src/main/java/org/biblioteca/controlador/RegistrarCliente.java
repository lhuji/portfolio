package org.biblioteca.controlador;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.biblioteca.modelo.ConexionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarCliente {
    public Button btnRegistrar;
    public Button btnCancelar;
    public TextField nombreClienteField;
    public TextField direccionClienteField;
    public TextField contactoClienteField;
    private MenuControlador menuController;

    public void setClienteController(MenuControlador menuController) {
        this.menuController = menuController;
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) nombreClienteField.getScene().getWindow();
        stage.close();
    }

    public void cancelar(ActionEvent event) {
        cerrarVentanaActual();
    }

    private void cerrarRecursos(PreparedStatement stmt, Connection conn) {
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

    public boolean registrarCliente(ActionEvent actionEvent) {
        String nombreCliente = nombreClienteField.getText();
        String direccionCliente = direccionClienteField.getText();
        String contactoCliente = contactoClienteField.getText();

        // Validar campos y obtener mensaje de error
        String mensajeError = validarCampos(nombreCliente, direccionCliente, contactoCliente);

        if (mensajeError != null) {
            // Mostrar el mensaje de error específico
            mostrarMensajeError(mensajeError);
            return false; // Detener el proceso si hay errores
        }

        // Todos los campos son válidos, procede con el registro
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexionPool.obtenerConexion();

            String query = "INSERT INTO cliente (nombre, direccion, contacto) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreCliente);
            stmt.setString(2, direccionCliente);
            stmt.setString(3, contactoCliente);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarMensajeExito();
                cerrarVentanaActual();
                return true;
            } else {
                mensajeError = "Error al registrar el cliente.";
                mostrarMensajeError(mensajeError);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
            mensajeError = "Error al registrar el cliente. Detalles: " + e.getMessage();
            mostrarMensajeError(mensajeError);
            return false;
        } finally {
            cerrarRecursos(stmt, conn);
        }
    }

    // Método para validar los campos
    private String validarCampos(String nombre, String direccion, String contacto) {
        if (nombre.isEmpty() || direccion.isEmpty() || contacto.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        if (!validarNombre(nombre)) {
            return "El nombre solo debe contener letras.";
        }

        if (!validarDireccion(direccion)) {
            return "La dirección solo puede contener letras, números, espacios y algunos caracteres especiales.";
        }

        if (!validarContacto(contacto)) {
            return "El contacto debe contener exactamente 9 dígitos numéricos.";
        }

        return null; // Todos los campos son válidos
    }

    private boolean validarNombre(String nombre) {
        return nombre.matches("[a-zA-Z]+");
    }

    private boolean validarDireccion(String direccion) {
        return direccion.matches("[a-zA-Z0-9\\s.,-]+");
    }

    private boolean validarContacto(String contacto) {
        return contacto.matches("\\d{9}");
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro exitoso");
        alert.setHeaderText(null);
        alert.setContentText("El cliente se ha registrado con éxito.");
        alert.showAndWait();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
