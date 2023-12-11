/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;


import javafx.scene.Parent;
import java.text.SimpleDateFormat;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Avion;
import modelo.Vuelo;
import modelo.ConexionBD;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modelo.Tripulante;
import modelo.Pasajero;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import modelo.Tripulante2;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import modelo.Logomens;


/**
 * FXML Controller class
 *
 * @author DAM
 */
public class PestaniasController implements Initializable {
    
    
    int idAvionSelecAsig;
    
    private ArrayList<Tripulante2> miembrosSelec = new ArrayList<>();
    @FXML
    private TextArea asignacionTripulantesAsignados;
    @FXML
    private TableView<Avion> operativosOP;
    @FXML
    private TableColumn<Avion, Integer> idAvionOP;
    @FXML
    private TableColumn<Avion, String> matriculaOP;
    @FXML
    private TableColumn<Avion, String> modeloOP;
    @FXML
    private TableColumn<Avion, Integer> nasientosOP;
    @FXML
    private TableView<Avion> noOperativos;
    @FXML
    private TableColumn<Avion, Integer> idAvion;
    @FXML
    private TableColumn<Avion, String> matricula;
    @FXML
    private TableColumn<Avion, String> modelo;
    @FXML
    private TableColumn<Avion, Integer> nasientos;
    @FXML
    private ListView<Tripulante> listaTripus;
    @FXML
    private ImageView fotoTripu;
    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDetalle;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableColumn<Vuelo, Integer> idvuelo;
    @FXML
    private TableColumn<Vuelo, Integer> idavion;
    @FXML
    private TableColumn<Vuelo, String> idorigen;
    @FXML
    private TableColumn<Vuelo, String> destino;
    @FXML
    private TableColumn<Vuelo, String> fecha;
    @FXML
    private TableColumn<Vuelo, String> salida;
    @FXML
    private TableColumn<Vuelo, String> llegada;
    @FXML
    private Button btnEditarVUELO;
    @FXML
    private Button btnAnadirVUELO;
    @FXML
    private TableView<Vuelo> vuelos;
    @FXML
    private TableView<Pasajero> tablaPasaj;
    @FXML
    private TableColumn<Pasajero, Integer> idPasaj;
    @FXML
    private TableColumn<Pasajero, String> dniPasaj;
    @FXML
    private TableColumn<Pasajero, String> nomPasaj;
    @FXML
    private TableColumn<Pasajero, String> ape1Pasaj;
    @FXML
    private TableColumn<Pasajero, String> ape2Pasaj;
    @FXML
    private TableColumn<Pasajero, String> edadPasaj;
    @FXML
    private TableColumn<Pasajero, String> tlfPasaj;
    @FXML
    private TableColumn<Pasajero, String> emailPasaj;
    @FXML
    private TableColumn<Pasajero, String> dirPasaj;
    @FXML
    private TableColumn<Pasajero, Integer> idVueloPasaj;
    @FXML
    private ImageView fotoPasaj;
    @FXML
    private Button anadirPasaj;
    @FXML
    private Button editPasaj;
    @FXML
    private Button elimPasaj;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button asignacionLimpiar;

    @FXML
    private Button asignacionListo;

    @FXML
    private ListView<Tripulante2> asignacionTripulantes;

    @FXML
    private ComboBox<Vuelo> asignacionVuelos;

    @FXML
    private ComboBox<Avion> asignacionAvion;

    @FXML
    private TextArea asignacionPasajeros;
    @FXML
    private Label asignacionModelo;
    @FXML
    private Label asignacionFecha;
    @FXML
    private Label asignacionHora;
    private int idVueloSelecAsig = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Asocio los atributos de la clase Avion con las columnas de la tabla
        this.idAvion.setCellValueFactory(new PropertyValueFactory("idAvion"));
        this.matricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.modelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        this.nasientos.setCellValueFactory(new PropertyValueFactory("numAsientos"));
        this.idAvionOP.setCellValueFactory(new PropertyValueFactory("idAvion"));
        this.matriculaOP.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.modeloOP.setCellValueFactory(new PropertyValueFactory("modelo"));
        this.nasientosOP.setCellValueFactory(new PropertyValueFactory("numAsientos"));
        ObservableList<Avion> oper = getAviones(1);
        ObservableList<Avion> noOper = getAviones(0);
        this.operativosOP.setItems(oper);
        this.noOperativos.setItems(noOper);
        ObservableList<Tripulante> tripus = getTripus();
        this.listaTripus.setItems(tripus);    
        listaTripus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tripulante>() {

        @Override
        public void changed(ObservableValue<? extends Tripulante> observable, Tripulante oldValue, Tripulante newValue) {
            if (newValue == null) {
                // No se ha seleccionado ningún pasajero, por lo tanto, termina el método
                return;
                }
        // Your action here
        fotoTripu.setImage(listaTripus.getSelectionModel().getSelectedItem().getFoto());
    }
        
});     
        
        asignacionAvion.setItems(oper);
        asignacionAvion.setValue(oper.get(0));
        asignacionAvion.getOnAction().handle(new ActionEvent());
        idvuelo.setCellValueFactory(new PropertyValueFactory<>("idVuelo"));
        idavion.setCellValueFactory(new PropertyValueFactory<>("idAvion"));
        idorigen.setCellValueFactory(new PropertyValueFactory<>("origen"));
        destino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        salida.setCellValueFactory(new PropertyValueFactory<>("salida"));
        llegada.setCellValueFactory(new PropertyValueFactory<>("llegada"));
        idPasaj.setCellValueFactory(new PropertyValueFactory<>("idPasajero"));
        dniPasaj.setCellValueFactory(new PropertyValueFactory<>("dni"));
        nomPasaj.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ape1Pasaj.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        ape2Pasaj.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        edadPasaj.setCellValueFactory(new PropertyValueFactory<>("edad"));
        tlfPasaj.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        emailPasaj.setCellValueFactory(new PropertyValueFactory<>("email"));
        dirPasaj.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        idVueloPasaj.setCellValueFactory(new PropertyValueFactory<>("idVuelo"));
        
        
        // Obtener los Pasajeros desde la base de datos
        ObservableList<Pasajero> pasajeros = getPasajeros();
        tablaPasaj.setItems(pasajeros);
        tablaPasaj.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pasajero>() {
        @Override
        public void changed(ObservableValue<? extends Pasajero> observable, Pasajero oldValue, Pasajero newValue) {
            if (newValue == null) {
                // No se ha seleccionado ningún pasajero, por lo tanto, termina el método
                return;
                }

            // Se ha seleccionado un pasajero, establece la imagen en el ImageView
            fotoPasaj.setImage(tablaPasaj.getSelectionModel().getSelectedItem().getFoto());
            }
        });
        // Obtener los vuelos desde la base de datos
        ObservableList<Vuelo> vuelosData = getVuelos();
        vuelos.setItems(vuelosData);
        
        
        // Init asignacion
        ObservableList<Tripulante2> tripus2 = FXCollections.observableArrayList();
        tripus2.addAll(getTripus().stream()
            .map(tripulante -> new Tripulante2(tripulante.getIdTripu(), tripulante.getNombre(), tripulante.getApellido1(), tripulante.getApellido2()))
            .collect(Collectors.toList()));
        asignacionTripulantes.setItems(tripus2);

        asignacionTripulantes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tripulante2>() {
        @Override
        public void changed(ObservableValue<? extends Tripulante2> observable, Tripulante2 oldValue, Tripulante2 newValue) {
        Tripulante2 selec = asignacionTripulantes.getSelectionModel().getSelectedItem();
        if (selec != null) {
            if (miembrosSelec.size() < 5) {
                miembrosSelec.add(selec);
                asignacionTripulantesAsignados.appendText(selec.toString() + "\n");
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Información");
                alert.setContentText("Solo puede añadir 5 tripulantes. ¿Desea empezar de nuevo?");
                ButtonType yesButton = new ButtonType("Sí");
                ButtonType noButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    miembrosSelec.clear();
                    asignacionTripulantesAsignados.setText("");
                    asignacionTripulantes.getSelectionModel().clearSelection();
                    return; // Evitar agregar elementos nuevamente cuando se reinicie
                }
            }
        }
       
        // Resto del código si es necesario
    }
    });
    }
    
    // ---------------------------- OBTENCION DE DATOS -------------------------
    public ObservableList<Avion> getAviones(int estado){
        
         ObservableList<Avion> obs = FXCollections.observableArrayList();
         ConexionBD conexion = new ConexionBD ( );          
         ResultSet resultadosOperativos = conexion.ejecutarConsulta ("SELECT * FROM aviones WHERE estado="+estado );
             try {
                 while (resultadosOperativos.next()){
                     String matricula = resultadosOperativos.getString("matricula");
                     int id = resultadosOperativos.getInt("idAvion");
                     int nasientos = resultadosOperativos.getInt("numAsientos");
                     String modelo = resultadosOperativos.getString("modelo");
                     //Creo un avion
                     Avion a = new Avion(id,nasientos,matricula,modelo);
                     obs.add(a);
                 }
                 conexion.cerrarConexion ( );
             } catch (SQLException ex) {
                 Logger.getLogger(PestaniasController.class.getName()).log(Level.SEVERE, null, ex);
             }
     return obs;
        
    }
    public ObservableList<Tripulante> getTripus(){
        
        ObservableList<Tripulante> obs = FXCollections.observableArrayList();
         ConexionBD conexion = new ConexionBD ( );          
         ResultSet resu = conexion.ejecutarConsulta ("SELECT * FROM miembros" );
             try {
                 while (resu.next()){
                     int id = resu.getInt("idTripulacion");
                     String nombre = resu.getString("nombre");
                     String apellido1 = resu.getString("apellido1");
                     String apellido2 = resu.getString("apellido2");
                     String direccion = resu.getString("direccion");
                     String fechaNacimiento = resu.getString("fechaNacimiento");
                     String telefono = resu.getString("telefono");
                     String email = resu.getString("ecorreo");
                     String categoria = resu.getString("categoria");
                     Image foto = obtenerImagen(resu.getBytes("foto"));
                     //Creo un tripu
                     Tripulante t = new Tripulante(id,nombre,apellido1,apellido2,direccion,fechaNacimiento,
                     telefono,email,categoria,foto);
                     obs.add(t);
                 }
                 conexion.cerrarConexion ( );
             } catch (SQLException ex) {
                 Logger.getLogger(PestaniasController.class.getName()).log(Level.SEVERE, null, ex);
             }
     return obs;
        
    }
    public ObservableList<Pasajero> getPasajeros() {
    ObservableList<Pasajero> pasajeros = FXCollections.observableArrayList();

    try {
        ConexionBD conexionBD = new ConexionBD();
        ResultSet resultado = conexionBD.ejecutarConsulta("SELECT * FROM pasajeros");
        ResultSet resultado3 = conexionBD.ejecutarConsulta("SELECT edad(fechaNacimiento) as edad FROM pasajeros");

        while (resultado.next()) {
            boolean flag = false;
            resultado3.next();
            ResultSet resultado2 = conexionBD.ejecutarConsulta("SELECT * FROM pasajeros_vuelos WHERE idPasajeros=" + resultado.getInt("idPasajeros"));

            while (resultado2.next()) {
                Pasajero pasajero = new Pasajero(
                        resultado.getInt("idPasajeros"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido1"),
                        resultado.getString("apellido2"),
                        resultado.getString("direccion"),
                        resultado3.getString("edad"),
                        resultado.getString("telefono"),
                        resultado.getString("ecorreo"),
                        resultado.getString("dni"),
                        resultado2.getInt("idVuelo"),
                        obtenerImagen(resultado.getBytes("foto"))
                );

                pasajeros.add(pasajero);
                flag = true;
            }

            if (!flag) {
                Pasajero pasajero = new Pasajero(
                        resultado.getInt("idPasajeros"),
                        resultado.getString("dni"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido1"),
                        resultado.getString("apellido2"),
                        resultado3.getString("edad"),
                        resultado.getString("telefono"),
                        resultado.getString("ecorreo"),
                        resultado.getString("direccion"),
                        0,
                        obtenerImagen(resultado.getBytes("foto"))
                        
                );

                pasajeros.add(pasajero);
            }
        }

        conexionBD.cerrarConexion();
    } catch (SQLException ex) {
        Logger.getLogger(PestaniasController.class.getName()).log(Level.SEVERE, null, ex);
    }

    return pasajeros;
    }
    public ObservableList<Vuelo> getVuelos() {
    ObservableList<Vuelo> vuelosData = FXCollections.observableArrayList();
    
    // Formato de fecha y hora
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    try {
        // Crear una instancia de la clase de conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();
        // Ejecutar una consulta para obtener todos los vuelos de la tabla de vuelos
        ResultSet resultado = conexionBD.ejecutarConsulta("SELECT * FROM vuelos");
        
        while (resultado.next()) {
            ResultSet resultado2 = conexionBD.ejecutarConsulta("SELECT * FROM trayectos where idTrayecto=" + resultado.getInt("idTrayecto"));
            resultado2.next();
            String fecha = dateFormat.format(resultado.getDate("fecha"));
            String horaSalida = timeFormat.format(resultado.getTime("horaSalida"));
            String horaLlegada = timeFormat.format(resultado.getTime("horaLlegada"));
            Vuelo vuelo = new Vuelo(
                resultado.getInt("idVuelo"),
                resultado.getInt("idAvion"),
                resultado2.getString("origen"),
                resultado2.getString("destino"),
                fecha,
                horaSalida,
                horaLlegada
            );
            vuelosData.add(vuelo);
        }
        
        conexionBD.cerrarConexion();
    } catch (SQLException ex) {
        Logger.getLogger(PestaniasController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return vuelosData;
}

   // ---------------------------- TRIPULACION ---------------------------------
    @FXML
    private void anadirTripu(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/anadirTripu.fxml"));
        Pane ventana = (Pane) loader.load();
        AnadirTripuController nuev = loader.getController();
        nuev.initialize(-1,true);
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.setTitle("Añadir tripulante");
        stage.showAndWait();
        ObservableList<Tripulante> tripus = getTripus();
        this.listaTripus.setItems(tripus);
        cargaPasajerosAsignacion();
        
    }
    @FXML
    private void editarTripu(ActionEvent event) throws SQLException, IOException {
        Tripulante tripulanteSeleccionado = listaTripus.getSelectionModel().getSelectedItem();
        if (tripulanteSeleccionado == null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Editar tripulante");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, selecciona un tripulante antes de editarlo.");
        alert.showAndWait();
        } else {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/anadirTripu.fxml"));
        Pane ventana = (Pane) loader.load();
        AnadirTripuController nuev = loader.getController();
        int id = listaTripus.getSelectionModel().getSelectedItem().getIdTripu();
        nuev.initialize(id,false);
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.setTitle("Editar tripulante");
        stage.showAndWait();
        ObservableList<Tripulante> tripus = getTripus();
        this.listaTripus.setItems(tripus);
    }
    }
    @FXML
    private void detalleTripu(ActionEvent event) throws IOException,SQLException {
    Tripulante tripulanteSeleccionado = listaTripus.getSelectionModel().getSelectedItem();
    if (tripulanteSeleccionado == null) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Detalle de tripulante");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, selecciona un tripulante antes de ver los detalles.");
        alert.showAndWait();
    } else {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/detalleTripu.fxml"));
        Parent root = loader.load();
        detalleTripuController controller = loader.getController();
        controller.initialize(tripulanteSeleccionado);
        
        Stage stage = new Stage();
        stage.setTitle("Detalle del tripulante");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
    @FXML
    private void elimTripu(ActionEvent event) throws SQLException {
        Tripulante tripulanteseleccionado = listaTripus.getSelectionModel().getSelectedItem();
        if (tripulanteseleccionado == null) {
            // No se ha seleccionado ningún tripulante
            mostrarMensaje(AlertType.INFORMATION, "Error", "Debes seleccionar un tripulante.");
            return;
        }
        int id = listaTripus.getSelectionModel().getSelectedItem().getIdTripu();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Eliminar");
        alert.setContentText("¿Está seguro?");
        ButtonType buttonTypeOk = new ButtonType("Aceptar");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar");
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonTypeOk) {
                ConexionBD con = new ConexionBD();
                PreparedStatement p = con.getConexion().prepareStatement(
                        "delete from miembros where idTripulacion = ?");
                p.setInt(1, id);
                p.execute();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Info");
                alert2.setContentText("Eliminado con éxito");
                alert2.showAndWait();
            }
        }
        fotoTripu.setImage(null);
        ObservableList<Tripulante> tripus = getTripus();
        this.listaTripus.setItems(tripus);
        
    }
    
    // ---------------------------- PASAJEROS ----------------------------------
    @FXML
    private void anadirPasaj(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/emergentePasajero.fxml"));
        Pane ventana = (Pane) loader.load();
        PasajerosController nuev = loader.getController();
        ObservableList<Vuelo> obs = getVuelos();
        nuev.initialize(-1,true,obs);
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.setTitle("Añadir pasajero");
        stage.showAndWait();
        ObservableList<Pasajero> pasajeros = getPasajeros();
        this.tablaPasaj.setItems(pasajeros);
    }
    @FXML
    private void editPasaj(ActionEvent event) throws SQLException, IOException {
        Pasajero pasajeroseleccionado = tablaPasaj.getSelectionModel().getSelectedItem();
        if (pasajeroseleccionado == null) {
        mostrarMensajeError("Debe seleccionar un pasajero.");
        return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/emergentePasajero.fxml"));
        Pane ventana = (Pane) loader.load();
        PasajerosController nuev = loader.getController();
        int idPasajero = pasajeroseleccionado.getIdPasajero();
        ObservableList<Vuelo> obs = getVuelos();
        nuev.initialize(idPasajero,false,obs);
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.setTitle("Editar Pasajero");
        stage.showAndWait();
        ObservableList<Pasajero> pasajeros = getPasajeros();
        this.tablaPasaj.setItems(pasajeros);
    }
    @FXML
    private void elimPasaj(ActionEvent event) throws SQLException {
        Pasajero pasajeroseleccionado = tablaPasaj.getSelectionModel().getSelectedItem();
        if (pasajeroseleccionado == null) {
            // No se ha seleccionado ningún tripulante
            mostrarMensaje(AlertType.INFORMATION, "Error", "Debes seleccionar un pasajero.");
            return;
        }

        Alert confirmacionAlerta = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacionAlerta.setTitle("Eliminar Tripulante");
        confirmacionAlerta.setHeaderText(null);
        confirmacionAlerta.setContentText("¿Estás seguro de que quieres eliminar el pasajero seleccionado?");

        Optional<ButtonType> resultado = confirmacionAlerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            
                int idPasajero = pasajeroseleccionado.getIdPasajero();
                ConexionBD conexionBD = new ConexionBD();
                conexionBD.ejecutarConsulta("DELETE FROM pasajeros WHERE idPasajeros = " + idPasajero);
                conexionBD.cerrarConexion();

                // Eliminar el tripulante de la lista
                tablaPasaj.getItems().remove(pasajeroseleccionado);

                mostrarMensaje(AlertType.INFORMATION,"Éxito", "El pasajero se eliminó correctamente.");
            
        }
    }
    
    // ---------------------------- VUELOS -------------------------------------
    @FXML
    private void anadirVuelo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/emergenteVuelo.fxml"));
        Parent ventana = loader.load();
        VuelosController nuevoController = loader.getController();
        nuevoController.initialize(-1, true); // Pasar los datos al controlador
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        stage.setScene(scene);
        stage.setTitle("Añadir vuelo");
        stage.showAndWait();
        ObservableList<Vuelo> vuelosData = getVuelos();
        vuelos.setItems(vuelosData);
    }
    @FXML
    private void editarVuelo(ActionEvent event) throws SQLException, IOException {
    Vuelo vueloSeleccionado = vuelos.getSelectionModel().getSelectedItem();
    if (vueloSeleccionado == null) {
        mostrarMensajeError("Debe seleccionar un vuelo.");
        return; // Salir del método para evitar el error
    }
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/emergenteVuelo.fxml"));
    Pane ventana = (Pane) loader.load();
    VuelosController nuev = loader.getController();
    int id = vueloSeleccionado.getIdVuelo();
    nuev.initialize(id, false);
    Stage stage = new Stage();
    Scene scene = new Scene(ventana);
    stage.setScene(scene);
    stage.setTitle("Editar vuelo");
    stage.showAndWait();
    ObservableList<Vuelo> vuelosData = getVuelos();
    vuelos.setItems(vuelosData);
}
    @FXML
    private void eliminarVuelo(ActionEvent event) {
        Vuelo vueloSeleccionado = vuelos.getSelectionModel().getSelectedItem();
        if (vueloSeleccionado == null) {
            mostrarMensajeError("Debe seleccionar un vuelo.");
            return; // Salir del método para evitar el error
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmar eliminación");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("¿Está seguro de que desea eliminar este vuelo?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                ConexionBD con = new ConexionBD();
                PreparedStatement p = con.getConexion().prepareStatement("DELETE FROM vuelos WHERE idVuelo = ?");
                p.setInt(1, vueloSeleccionado.getIdVuelo());
                int affectedRows = p.executeUpdate();

                if (affectedRows > 0) {
                    mostrarMensajeInformacion("El vuelo ha sido eliminado correctamente");
                    ObservableList<Vuelo> vuelosData = getVuelos();
                    vuelos.setItems(vuelosData);
                } else {
                    mostrarMensajeError("No se encontró el vuelo especificado");
                }
            } catch (SQLException ex) {
                mostrarMensajeError("Error al eliminar el vuelo: " + ex.getMessage());
            }
        }
    }
    // ---------------------------- ASIGNACIÓN --------------------------
   private void asignarTripulantesALista() {
        // Crear un modelo observable y agregarlo al ListView
        ObservableList<Tripulante2> miembrosModel = FXCollections.observableArrayList();
        asignacionTripulantes.setItems(miembrosModel);

        // Obtener los datos de la tabla "miembros" y agregarlos al modelo observable
        ConexionBD conexionBD2 = new ConexionBD();
        ResultSet salida = conexionBD2.ejecutarConsulta("SELECT * FROM miembros");
        try {
            while (salida.next()) {
                Tripulante2 miembro = new Tripulante2(salida.getInt("idTripulacion"),
                        salida.getString("nombre"),
                        salida.getString("apellido1"),
                        salida.getString("apellido2"));
                miembrosModel.add(miembro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<Tripulante2> miembrosSelec = new ArrayList<>();
        asignacionTripulantes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tripulante2>() {
            @Override
            public void changed(ObservableValue<? extends Tripulante2> observable, Tripulante2 oldValue, Tripulante2 newValue) {
                Tripulante2 selec = asignacionTripulantes.getSelectionModel().getSelectedItem();
                if (selec != null) {
                    if (miembrosSelec.size() < 5) {
                        miembrosSelec.add(selec);
                        asignacionTripulantesAsignados.appendText(selec.toString() + "\n");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Información");
                        alert.setContentText("Solo puede añadir 5 tripulantes. ¿Desea empezar de nuevo?");
                        ButtonType yesButton = new ButtonType("Sí");
                        ButtonType noButton = new ButtonType("No");
                        alert.getButtonTypes().setAll(yesButton, noButton);
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == yesButton) {
                            miembrosSelec.clear();
                            asignacionTripulantesAsignados.setText("");
                            asignacionTripulantes.getSelectionModel().clearSelection();
                        }
                    }
                }
            }
        });
    }
    // ---------------------------- METODOS GENERALES --------------------------
    private void mostrarMensajeError(String mensaje) {
        mostrarMensaje(Alert.AlertType.ERROR, "Error", mensaje);
    }
    private void mostrarMensaje(Alert.AlertType tipoAlerta, String titulo, String mensaje) {
        Alert alert = new Alert(tipoAlerta);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void mostrarMensajeInformacion(String mensaje) {
        mostrarMensaje(Alert.AlertType.INFORMATION, "Información", mensaje);
    }
    public Image obtenerImagen(byte[] imagenBytes) throws SQLException {
        if (imagenBytes != null) {   
            // Escala la imagen al tamaño deseado si es necesario
            Image imagen = new Image(new ByteArrayInputStream(imagenBytes));
            // Crea un nuevo ImageIcon escalado y lo devuelve
            return imagen;
        }else{
            // Asignar una imagen predeterminada o realizar alguna acción alternativa
            // si la foto es nula
            String rutaDefault = "C:\\Users\\lhuji\\Desktop\\ProyectoAeropuertoFX\\default.png";
            Image imagenDefault = new Image(new File(rutaDefault).toURI().toString());
            return imagenDefault;
        }
    }

    @FXML
    private void asignacionLimpiar(ActionEvent event) {
        miembrosSelec.clear();
        asignacionTripulantesAsignados.setText("");
    }

    @FXML
    private void asignacionListo(ActionEvent event) {
                ConexionBD con = new ConexionBD();
                PreparedStatement p = null;
                try {
                    p = con.getConexion().prepareStatement("""
                            select idVuelo, idAvion
                            from miembros_vuelos pv
                            where idVuelo = ? and idAvion = ?;
                            """);
                    p.setInt(1, idVueloSelecAsig);
                    p.setInt(2, idAvionSelecAsig);
                    ResultSet res = p.executeQuery();
                    if (miembrosSelec.isEmpty() || idVueloSelecAsig == -1)
                    {
                        JOptionPane.showMessageDialog(null,"Seleccione un vuelo y al menos un tripulante");
                    } else {
                        if (!res.next()) {
                            Iterator<Tripulante2> it = miembrosSelec.iterator();
                            while (it.hasNext()) {
                                Tripulante2 m = it.next();
                                p = con.getConexion().prepareStatement("""
                            insert into miembros_vuelos
                            values(?,?,?,?)
                            """);
                            LocalDate fechaActual = LocalDate.now();
                            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String fechaComoString = fechaActual.format(formateador);
                            p.setInt(1, idVueloSelecAsig);
                            p.setInt(2, idAvionSelecAsig);
                            p.setInt(3, m.getIdTripu());
                            p.setString(4, fechaComoString);
                            int res1 = p.executeUpdate();
                        }
                            JOptionPane.showMessageDialog(null,"Tripulantes añadidos con éxito");
                            asignacionTripulantesAsignados.setText("");
                            asignacionTripulantes.getSelectionModel().clearSelection();
                            miembrosSelec.clear();
                            Logomens log = new Logomens();
                            log.escribirRegistro("Se asigno el vuelo "+idVueloSelecAsig);
                    } else {
                        UIManager.put("OptionPane.yesButtonText", "Sí");
                        int opt = JOptionPane.showConfirmDialog(null, "Ya se asignó ese vuelo. ¿Desea borrar la asignación existente?", "Información", JOptionPane.YES_NO_OPTION);
                        if (opt == JOptionPane.YES_NO_OPTION) {
                            p = con.getConexion().prepareStatement("""
                            delete from miembros_vuelos
                            where idVuelo = ? and idAvion = ?;
                            """);
                            p.setInt(1, idVueloSelecAsig);
                            p.setInt(2, idAvionSelecAsig);
                            int res1 = p.executeUpdate();
                            miembrosSelec.clear();
                            asignacionTripulantesAsignados.setText("");
                            asignacionTripulantes.getSelectionModel().clearSelection();
                            miembrosSelec.clear();

                        }
                    }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
    }
    

    @FXML
    private void asignacionVuelos(ActionEvent event) throws SQLException {
        Vuelo v = asignacionVuelos.getSelectionModel().getSelectedItem();
        if (v != null) {
            String fecha = v.getFecha();
            String horaSalida = v.getSalida();
            asignacionFecha.setText(fecha);
            asignacionHora.setText(horaSalida);
            idVueloSelecAsig = v.getIdVuelo();
            cargaPasajEnTextArea(idVueloSelecAsig, idAvionSelecAsig);
        } else {
            return;
        }
    }

    @FXML
    private void asignacionAvion(ActionEvent event) {
        asignacionPasajeros.setText("");
        asignacionFecha.setText("");
        asignacionHora.setText("");
        idAvionSelecAsig = asignacionAvion.getSelectionModel().getSelectedItem().getIdAvion();
        asignacionModelo.setText(asignacionAvion.getSelectionModel().getSelectedItem().getModelo());
        ObservableList<Vuelo> obs = getVuelos(idAvionSelecAsig);
        asignacionVuelos.setItems(obs);
        try {
        asignacionVuelos.setValue(obs.get(0));
        } catch(IndexOutOfBoundsException e) {
            idVueloSelecAsig = -1;
        }
        asignacionVuelos.getOnAction().handle(new ActionEvent());
        
    }
    
   public ObservableList<Vuelo> getVuelos(int idAvion) {
    ObservableList<Vuelo> vuelosData = FXCollections.observableArrayList();
    
    // Formato de fecha y hora
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    try {
        // Crear una instancia de la clase de conexión a la base de datos
        ConexionBD conexionBD = new ConexionBD();
        // Ejecutar una consulta para obtener todos los vuelos de la tabla de vuelos
        ResultSet resultado = conexionBD.ejecutarConsulta("SELECT * FROM vuelos where idAvion="+idAvion);
        
        while (resultado.next()) {
            ResultSet resultado2 = conexionBD.ejecutarConsulta("SELECT * FROM trayectos where idTrayecto=" + resultado.getInt("idTrayecto"));
            resultado2.next();
            String fecha = dateFormat.format(resultado.getDate("fecha"));
            String horaSalida = timeFormat.format(resultado.getTime("horaSalida"));
            String horaLlegada = timeFormat.format(resultado.getTime("horaLlegada"));
            Vuelo vuelo = new Vuelo(
                resultado.getInt("idVuelo"),
                resultado.getInt("idAvion"),
                resultado2.getString("origen"),
                resultado2.getString("destino"),
                fecha,
                horaSalida,
                horaLlegada
            );
            vuelosData.add(vuelo);
        }
        
        conexionBD.cerrarConexion();
    } catch (SQLException ex) {
        Logger.getLogger(PestaniasController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return vuelosData;


}
   public void cargaPasajEnTextArea(int idVuelo, int idAvion) throws SQLException{
       
       ConexionBD con = new ConexionBD();
       PreparedStatement p = con.getConexion().prepareStatement("""
            select nombre, apellido1, apellido2
            from pasajeros p
            join pasajeros_vuelos pv
            on p.idPasajeros = pv.idPasajeros
            where idVuelo = ? and idAvion = ?;
           """);
       p.setInt(1, idVuelo);
       p.setInt(2, idAvion);
       ResultSet r = p.executeQuery();
       asignacionPasajeros.setText("");
                        while (r.next()){
                            asignacionPasajeros.setText(r.getString("apellido1")+" "+r.getString("apellido2")+
                                    ", "+r.getString("nombre")+ "\n" +asignacionPasajeros.getText()  );
                        }
       
       
   };
   
   private void cargaPasajerosAsignacion() {
        // Crear un modelo observable y agregarlo al ListView
        ObservableList<Tripulante2> miembrosModel = FXCollections.observableArrayList();
        asignacionTripulantes.setItems(miembrosModel);

        // Obtener los datos de la tabla "miembros" y agregarlos al modelo observable
        ConexionBD conexionBD2 = new ConexionBD();
        ResultSet salida = conexionBD2.ejecutarConsulta("SELECT * FROM miembros");
        try {
            while (salida.next()) {
                Tripulante2 miembro = new Tripulante2(salida.getInt("idTripulacion"),
                        salida.getString("nombre"),
                        salida.getString("apellido1"),
                        salida.getString("apellido2"));
                miembrosModel.add(miembro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
}}

    

