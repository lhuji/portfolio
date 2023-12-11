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

public class RegistrarProveedor {
    public Button btnRegistrar;
    public Button btnCancelar;
    public TextField nombreProveedorField;
    public TextField direccionProveedorField;
    public TextField contactoProveedorField;
    private MenuControlador menuController;
    public void setProveedorController(MenuControlador menuController) {
        this.menuController = menuController;
    }
    private void cerrarVentanaActual() {
        Stage stage = (Stage) nombreProveedorField.getScene().getWindow();
        stage.close();
    }
    public void cancelar(ActionEvent event) {
        cerrarVentanaActual ();
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
    public void registrarProveedor(ActionEvent actionEvent) {
        String nombreProveedor = nombreProveedorField.getText();
        String direccionProveedor = direccionProveedorField.getText();
        String contactoProveedor = contactoProveedorField.getText();

        String mensajeError = validarCampos(nombreProveedor, direccionProveedor, contactoProveedor);

        if (mensajeError != null) {
            // Mostrar el mensaje de error específico
            mostrarMensajeError(mensajeError);
            return; // Detener el proceso si hay errores
        }

        // Todos los campos son válidos, procede con el registro
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexionPool.obtenerConexion();

            String query = "INSERT INTO proveedor (nombre, direccion, contacto) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreProveedor);
            stmt.setString(2, direccionProveedor);
            stmt.setString(3, contactoProveedor);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarMensajeExito();
                cerrarVentanaActual();
            } else {
                mensajeError = "Error al registrar el proveedor.";
                mostrarMensajeError(mensajeError);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
            mensajeError = "Error al registrar el proveedor. Detalles: " + e.getMessage();
            mostrarMensajeError(mensajeError);
        } finally {
            cerrarRecursos(stmt, conn);
        }
    }


    private String validarCampos(String nombre, String direccion, String contacto) {
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
        alert.setContentText("El proveedor se ha registrado con éxito.");
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
