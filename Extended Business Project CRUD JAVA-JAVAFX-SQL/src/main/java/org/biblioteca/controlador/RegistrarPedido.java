package org.biblioteca.controlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.biblioteca.modelo.ConexionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RegistrarPedido {

    @FXML
    public ComboBox<String> comboProveedor;

    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnCancelar;
    public TextField nombreProductoField;
    public TextField stockProductoField;

    private MenuControlador menuController;

    public void setProductoController(MenuControlador menuController) {
        this.menuController = menuController;
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
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
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        cargarDatosComboBox();
    }

    private void cargarDatosComboBox() {
        cargarProveedores();
    }

    private void cargarProveedores() {
        ObservableList<String> proveedores = FXCollections.observableArrayList();
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM proveedor");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                proveedores.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboProveedor.setItems(proveedores);
    }

    @FXML
    public void registrarProducto(ActionEvent actionEvent) {
        String cantidad = stockProductoField.getText();
        String nombreProducto = nombreProductoField.getText ();
        String proveedorSeleccionado = comboProveedor.getValue();


        // Validar campos y obtener mensaje de error
        String mensajeError = validarCampos(nombreProducto, cantidad, proveedorSeleccionado);

        if (mensajeError != null) {
            // Mostrar el mensaje de error específico
            mostrarMensajeError(mensajeError);
            return; // Detener el proceso si hay errores
        }

        if (registrarVentaEnBD(nombreProducto, cantidad, proveedorSeleccionado)) {
            mostrarMensajeExito();
            cerrarVentanaActual();
        }
    }

    private String validarCampos(String nombreProducto, String cantidad, String proveedorSeleccionado) {
        if (cantidad.isEmpty() || nombreProducto == null || proveedorSeleccionado == null) {
            return "Por favor, complete todos los campos.";
        }

        try {
            Integer.parseInt(cantidad); // Verificar si la cantidad es un número
        } catch (NumberFormatException e) {
            return "La cantidad debe ser un número entero.";
        }

        return null; // Todos los campos son válidos
    }


    private boolean registrarVentaEnBD(String nombreProducto, String cantidad, String proveedorSeleccionado) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexionPool.obtenerConexion();

            // Registrar la venta
            String query = "INSERT INTO pedido (nombreProducto, cantidad, nombreProveedor) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nombreProducto);
            stmt.setInt(2, Integer.parseInt (cantidad));
            stmt.setString (3,  proveedorSeleccionado);

            int filasAfectadas = stmt.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al registrar la venta.");
        } finally {
            cerrarRecursos(stmt, conn);
        }
        return false;
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro exitoso");
        alert.setHeaderText(null);
        alert.setContentText("El pedido se ha registrado con éxito.");
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
