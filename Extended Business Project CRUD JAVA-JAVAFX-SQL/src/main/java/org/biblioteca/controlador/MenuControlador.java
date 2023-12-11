package org.biblioteca.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import org.biblioteca.modelo.Cliente;
import org.biblioteca.modelo.ConexionPool;
import org.biblioteca.modelo.Producto;
import org.biblioteca.modelo.Proveedor;
import org.biblioteca.modelo.Venta;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuControlador {

    public TextField buscaNombre;
    public TextField buscaContacto;
    public TextField buscaDireccion;
    public Button generarHTMLGeneral;
    public Button generarHTMLFactura;
    @FXML
    private Label fechaActual;

    @FXML
    private Label horaActual;

    // Tabla y columnas para Clientes
    @FXML
    private TableView<Cliente> tablaCliente;

    @FXML
    private TableColumn<Cliente, Integer> colClientesID;

    @FXML
    private TableColumn<Cliente, String> colClientesNombre;

    @FXML
    private TableColumn<Cliente, String> colClientesDireccion;

    @FXML
    private TableColumn<Cliente, String> colClientesContacto;

    // Botón para crear Cliente
    @FXML
    private Button buttonCrearCliente;

    // Tabla y columnas para Productos
    @FXML
    private TableView<Producto> tablaInventario;

    @FXML
    private TableColumn<Producto, Integer> colInventarioID;

    @FXML
    private TableColumn<Producto, String> colInventarioNombre;

    @FXML
    private TableColumn<Producto, String> colInventarioDescripcion;

    @FXML
    private TableColumn<Producto, Double> colInventarioPrecio;

    @FXML
    private TableColumn<Producto, Integer> colInventarioStock;

    // Botón para crear Producto
    @FXML
    private Button buttonCrearProducto;

    // Tabla y columnas para Proveedores
    @FXML
    private TableView<Proveedor> tablaProveedores;

    @FXML
    private TableColumn<Proveedor, Integer> colProveedoresID;

    @FXML
    private TableColumn<Proveedor, String> colProveedoresNombre;

    @FXML
    private TableColumn<Proveedor, String> colProveedoresDireccion;

    @FXML
    private TableColumn<Proveedor, String> colProveedoresContacto;

    // Botón para crear Proveedor
    @FXML
    private Button buttonCrearProveedor;

    // Tabla y columnas para Ventas
    @FXML
    private TableView<Venta> tablaVentas;

    @FXML
    private TableColumn<Venta, Integer> colVentaID;

    @FXML
    private TableColumn<Venta, Integer> colVentaCantidad;

    @FXML
    private TableColumn<Venta, Date> colVentaFecha;

    @FXML
    private TableColumn<Venta, Integer> colVentaClienteID;

    @FXML
    public TableColumn<Venta, Integer> colVentaProductoID;
    @FXML
    public TableColumn<Venta, Integer> colVentaProveedorID;

    // Botón para crear Venta
    @FXML
    private Button buttonCrearVenta;
    @FXML
    private void initialize() {
        System.out.println("Inicializando el controlador...");
        configurarColumnas();
        cargarDatosClientes();
        cargarDatosProducto ();
        cargarDatosProveedores();
        cargarDatosVentas();
        configurarReloj();
    }
    private void configurarReloj() {
        // Actualizar la fecha cada segundo
        Timeline timelineFecha = new Timeline(
                new KeyFrame ( Duration.seconds(1), event -> {
                    LocalDate fecha = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    fechaActual.setText(formatter.format(fecha));
                    cargarDatosClientes();
                    cargarDatosProducto ();
                    cargarDatosProveedores();
                    cargarDatosVentas();
                })

        );
        timelineFecha.setCycleCount(Timeline.INDEFINITE);
        timelineFecha.play();

        // Actualizar la hora cada segundo
        Timeline timelineHora = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    LocalTime         hora      = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    horaActual.setText(formatter.format(hora));
                })
        );
        timelineHora.setCycleCount(Timeline.INDEFINITE);
        timelineHora.play();
    }
    private void configurarColumnas() {
        colInventarioID.setCellValueFactory(new PropertyValueFactory<>("productoId"));
        colInventarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colInventarioDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colInventarioPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colInventarioStock.setCellValueFactory(new PropertyValueFactory<>("cantidadEnStock"));

        colClientesID.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colClientesNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colClientesDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colClientesContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));

        colProveedoresID.setCellValueFactory(new PropertyValueFactory<>("proveedorId"));
        colProveedoresNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colProveedoresDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colProveedoresContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));

        colVentaID.setCellValueFactory(new PropertyValueFactory<>("ventaId"));
        colVentaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colVentaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colVentaClienteID.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colVentaProductoID.setCellValueFactory(new PropertyValueFactory<>("productoId"));
        colVentaProveedorID.setCellValueFactory(new PropertyValueFactory<>("proveedorId"));
    }
    @FXML
    private void crearCliente(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/registrarCliente.fxml"));
            Parent registrarRoot = loader.load();

            RegistrarCliente registrarClienteControlador = loader.getController();
            registrarClienteControlador.setClienteController (this);

            Scene scene = new Scene(registrarRoot);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Cliente");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void crearProducto() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/registrarProducto.fxml"));
            Parent registrarRoot = loader.load();

            RegistrarProducto registrarProductoControlador = loader.getController();
            registrarProductoControlador.setProductoController (this);

            Scene scene = new Scene(registrarRoot);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Producto");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void crearProveedor(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/registrarProveedor.fxml"));
            Parent registrarRoot = loader.load();

            RegistrarProveedor registrarProveedorControlador = loader.getController();
            registrarProveedorControlador.setProveedorController (this);

            Scene scene = new Scene(registrarRoot);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Proveedor");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void crearVenta(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/registrarVenta.fxml"));
            Parent registrarRoot = loader.load();

            RegistrarVenta registrarVentacontrolador = loader.getController();
            registrarVentacontrolador.setProductoController (this);

            Scene scene = new Scene(registrarRoot);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nueva Venta");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void crearPedido(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/biblioteca/view/registrarPedido.fxml"));
            Parent registrarRoot = loader.load();

            RegistrarPedido registrarPedidocontrolador = loader.getController();
            registrarPedidocontrolador.setProductoController (this);

            Scene scene = new Scene(registrarRoot);

            Stage stage = new Stage();
            stage.setTitle("Registrar Nuevo Pedido");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UTILITY);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargarDatosClientes() {
        try (Connection connection = ConexionPool.obtenerConexion()) {
            String query = "SELECT * FROM cliente WHERE nombre LIKE ? AND contacto LIKE ? AND direccion LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Configurar parámetros de búsqueda
                String nombreBusqueda = "%" + buscaNombre.getText() + "%";
                String contactoBusqueda = "%" + buscaContacto.getText() + "%";
                String direccionBusqueda = "%" + buscaDireccion.getText() + "%";

                preparedStatement.setString(1, nombreBusqueda);
                preparedStatement.setString(2, contactoBusqueda);
                preparedStatement.setString(3, direccionBusqueda);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    tablaCliente.getItems().clear();

                    while (resultSet.next()) {
                        // Cambiar la creación de Cliente para usar propiedades observables
                        Cliente cliente = new Cliente(
                                resultSet.getInt("clienteId"),
                                resultSet.getString("nombre"),
                                resultSet.getString("direccion"),
                                resultSet.getString("contacto")
                        );

                        tablaCliente.getItems().add(cliente);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar datos de clientes");
        }
    }
    private void cargarDatosProducto() {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM producto");
             ResultSet rs = stmt.executeQuery()) {

            tablaInventario.getItems().clear();

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("productoId"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidadEnStock")
                );
                tablaInventario.getItems ().add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void cargarDatosProveedores() {
        try (Connection connection = ConexionPool.obtenerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM proveedor");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            tablaProveedores.getItems().clear();

            while (resultSet.next()) {
                // Cambiar la creación de Proveedor para usar propiedades observables
                Proveedor proveedor = new Proveedor(
                        resultSet.getInt("proveedorId"),
                        resultSet.getString("nombre"),
                        resultSet.getString("direccion"),
                        resultSet.getString("contacto")
                );

                tablaProveedores.getItems().add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar datos de proveedores");
        }
    }
    private void cargarDatosVentas() {
        try (Connection connection = ConexionPool.obtenerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM venta");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            tablaVentas.getItems().clear();

            while (resultSet.next()) {
                // Cambiar la creación de Venta para usar propiedades observables
                Venta venta = new Venta(
                        resultSet.getInt("ventaId"),
                        resultSet.getInt("cantidad"),
                        resultSet.getDate("fecha"),
                        resultSet.getInt("clienteId"),
                        resultSet.getInt("productoId"),
                        resultSet.getInt ("proveedorId")
                );

                tablaVentas.getItems().add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar datos de ventas");
        }
    }
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    private void generarHTMLInventario() throws SQLException {
        // Establecer la conexión a la base de datos (puedes ajustar esto según tu configuración)
        Connection connection = ConexionPool.obtenerConexion();
        try {


            // Cambia la ruta del archivo Jasper a la ruta relativa en el classpath
            String jasperFile = "/InventarioActual.jrxml";

            // Obtén la entrada del recurso como un flujo de entrada
            InputStream jasperStream = getClass().getResourceAsStream(jasperFile);

            // Crear un mapa de parámetros (si el informe los utiliza)
            Map<String, Object> parameters = new HashMap<>();
            // Cambia la ruta del archivo de imagen
            String logoPath = "/image/logo.png";

            // Obtén la URL del recurso
            URL logoUrl = getClass().getResource(logoPath);

            if (logoUrl != null) {
                // Obtén la cadena de la URL
                parameters.put("logoPath", logoUrl.toExternalForm());
            } else {
                System.err.println("No se pudo cargar la URL del archivo de imagen: " + logoPath);
            }

            // Crear el informe JasperReport compilado
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);


            // Llenar el informe con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Exportar el informe a formato HTML
            String htmlFile = "InventarioActual.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, htmlFile);

            // Abre el archivo HTML en el navegador predeterminado (puedes ajustar esto según tu sistema operativo)
            Desktop.getDesktop().browse(new File (htmlFile).toURI());

        } catch ( JRException | IOException e) {
            e.printStackTrace();
            System.err.println("Error al generar el inventario actual: " + e.getMessage());
        } finally {
            // Cerrar la conexión a la base de datos
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    private void generarHTMLFactura() throws SQLException {
        // Establecer la conexión a la base de datos (puedes ajustar esto según tu configuración)
        Connection connection = ConexionPool.obtenerConexion();
        try {


            // Cambia la ruta del archivo Jasper a la ruta relativa en el classpath
            String jasperFile = "/FacturasVentas.jrxml";

            // Obtén la entrada del recurso como un flujo de entrada
            InputStream jasperStream = getClass().getResourceAsStream(jasperFile);

            // Crear un mapa de parámetros (si el informe los utiliza)
            Map<String, Object> parameters = new HashMap<>();
            // Cambia la ruta del archivo de imagen
            String logoPath = "/image/logo.png";

            // Obtén la URL del recurso
            URL logoUrl = getClass().getResource(logoPath);

            if (logoUrl != null) {
                // Obtén la cadena de la URL
                parameters.put("logoPath", logoUrl.toExternalForm());
            } else {
                System.err.println("No se pudo cargar la URL del archivo de imagen: " + logoPath);
            }

            // Crear el informe JasperReport compilado
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

            // Crear una lista de ventas (simula los resultados de tu consulta)
            // Nota: ya no necesitas esto porque la consulta se realiza directamente en el JRXML
            // List<Venta> ventas = obtenerDatosDeVentaDesdeBD();

            // Llenar el informe con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Exportar el informe a formato HTML
            String htmlFile = "FacturaVentas.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, htmlFile);

            // Abre el archivo HTML en el navegador predeterminado (puedes ajustar esto según tu sistema operativo)
            Desktop.getDesktop().browse(new File (htmlFile).toURI());

        } catch ( JRException | IOException e) {
            e.printStackTrace();
            System.err.println("Error al generar la factura de ventas: " + e.getMessage());
        } finally {
            // Cerrar la conexión a la base de datos
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

