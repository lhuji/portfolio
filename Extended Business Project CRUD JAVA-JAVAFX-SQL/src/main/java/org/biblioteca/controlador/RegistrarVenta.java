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

public class RegistrarVenta {
    @FXML
    public ComboBox<String> comboProducto;
    @FXML
    public ComboBox<String> comboCliente;
    @FXML
    public ComboBox<String> comboProveedor;
    @FXML
    public DatePicker fechaVenta;
    @FXML
    public TextField cantidadVenta;
    @FXML
    public Button btnRegistrar;
    @FXML
    public Button btnCancelar;

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
        cargarProductos();
        cargarClientes();
        cargarProveedores();
    }

    private void cargarProductos() {
        ObservableList<String> productos = FXCollections.observableArrayList();
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM producto");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productos.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboProducto.setItems(productos);
    }

    private void cargarClientes() {
        ObservableList<String> clientes = FXCollections.observableArrayList();
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre FROM cliente");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        comboCliente.setItems(clientes);
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
    public void registrarVenta(ActionEvent actionEvent) {
        String cantidad = cantidadVenta.getText();
        String productoSeleccionado = comboProducto.getValue();
        String clienteSeleccionado = comboCliente.getValue();
        String proveedorSeleccionado = comboProveedor.getValue();
        Date fecha = java.sql.Date.valueOf(fechaVenta.getValue());

        // Validar campos y obtener mensaje de error
        String mensajeError = validarCampos(cantidad, productoSeleccionado, clienteSeleccionado, proveedorSeleccionado, fecha);

        if (mensajeError != null) {
            // Mostrar el mensaje de error específico
            mostrarMensajeError(mensajeError);
            return; // Detener el proceso si hay errores
        }

        int productoId = obtenerId("producto", "nombre", productoSeleccionado);
        int clienteId = obtenerId("cliente", "nombre", clienteSeleccionado);
        int proveedorId = obtenerId("proveedor", "nombre", proveedorSeleccionado);

        if (productoId == -1 || clienteId == -1 || proveedorId == -1) {
            mostrarMensajeError("Error al obtener IDs de productos, clientes o proveedores.");
            return;
        }

        if (registrarVentaEnBD(cantidad, fecha, clienteId, productoId, proveedorId)) {
            mostrarMensajeExito();
            cerrarVentanaActual();
        }
    }

    private String validarCampos(String cantidad, String producto, String cliente, String proveedor, Date fecha) {
        if (cantidad.isEmpty() || producto == null || cliente == null || proveedor == null || fecha == null) {
            return "Por favor, complete todos los campos.";
        }

        try {
            Integer.parseInt(cantidad); // Verificar si la cantidad es un número
        } catch (NumberFormatException e) {
            return "La cantidad debe ser un número entero.";
        }

        return null; // Todos los campos son válidos
    }

    private int obtenerId(String tabla, String columna, String valor) {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tabla + " WHERE " + columna + " = ?")) {

            stmt.setString(1, valor);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(tabla + "Id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean registrarVentaEnBD(String cantidad, Date fecha, int clienteId, int productoId, int proveedorId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexionPool.obtenerConexion();

            // Obtener el stock actual del producto
            int stockActual = obtenerStockProducto(conn, productoId);

            // Verificar si hay suficiente stock para la venta
            int cantidadVendida = Integer.parseInt(cantidad);
            if (stockActual < cantidadVendida) {
                mostrarMensajeError("No hay suficiente stock para la venta.");
                return false;
            }

            // Restar la cantidad vendida al stock actual
            int nuevoStock = stockActual - cantidadVendida;

            // Actualizar el nuevo stock en la base de datos
            String actualizarStockQuery = "UPDATE producto SET cantidadEnStock = ? WHERE productoId = ?";
            PreparedStatement actualizarStockStmt = conn.prepareStatement(actualizarStockQuery);
            actualizarStockStmt.setInt(1, nuevoStock);
            actualizarStockStmt.setInt(2, productoId);
            actualizarStockStmt.executeUpdate();

            // Registrar la venta
            String query = "INSERT INTO venta (cantidad, fecha, clienteId, productoId, proveedorId) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, cantidad);
            stmt.setDate(2, new java.sql.Date(fecha.getTime())); // Convertir Date a java.sql.Date
            stmt.setInt(3, clienteId);
            stmt.setInt(4, productoId);
            stmt.setInt(5, proveedorId);

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

    // Método para obtener el stock actual de un producto
    private int obtenerStockProducto(Connection conn, int productoId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT cantidadEnStock FROM producto WHERE productoId = ?")) {
            stmt.setInt(1, productoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidadEnStock");
                }
            }
        }
        return -1;
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro exitoso");
        alert.setHeaderText(null);
        alert.setContentText("La venta se ha registrado con éxito.");
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
