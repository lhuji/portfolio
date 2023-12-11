package org.biblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.biblioteca.modelo.ConexionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarProducto {
    public TextField nombreProductoField;
    public TextField precioProductoField;
    public TextField stockProductoField;
    public TextArea descripcionProductoField;
    public Button btnRegistrar;
    public Button btnCancelar;
    private MenuControlador menuController;

    public void setProductoController(MenuControlador menuController) {
        this.menuController = menuController;
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) nombreProductoField.getScene().getWindow();
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

    public boolean registrarProducto(ActionEvent actionEvent) {
        String nombreProducto = nombreProductoField.getText();
        String precioProducto = precioProductoField.getText();
        String stockProducto = stockProductoField.getText();
        String descripcionProducto = descripcionProductoField.getText();

        // Validar campos y obtener mensaje de error
        String mensajeError = validarCampos(nombreProducto, descripcionProducto, precioProducto, stockProducto);

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

            String query = "INSERT INTO producto (nombre, descripcion, precio, cantidadEnStock) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreProducto);
            stmt.setString(2, descripcionProducto);
            stmt.setString(3, precioProducto);
            stmt.setString(4, stockProducto);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarMensajeExito();
                cerrarVentanaActual();
                return true;
            } else {
                mensajeError = "Error al registrar el producto.";
                mostrarMensajeError(mensajeError);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores (considera logging)
            mensajeError = "Error al registrar el producto. Detalles: " + e.getMessage();
            mostrarMensajeError(mensajeError);
            return false;
        } finally {
            cerrarRecursos(stmt, conn);
        }
    }

    // Método para validar los campos
    private String validarCampos(String nombre, String descripcion, String precio, String stock) {
        if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || stock.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        if (!validarPrecio(precio)) {
            return "El precio debe ser un número válido.";
        }

        if (!validarStock(stock)) {
            return "La cantidad en stock debe ser un número válido.";
        }

        return null; // Todos los campos son válidos
    }

    private boolean validarPrecio(String precio) {
        try {
            Double.parseDouble(precio);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validarStock(String stock) {
        try {
            Integer.parseInt(stock);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro exitoso");
        alert.setHeaderText(null);
        alert.setContentText("El producto se ha registrado con éxito.");
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
