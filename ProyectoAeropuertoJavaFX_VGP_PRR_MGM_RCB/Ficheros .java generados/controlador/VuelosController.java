package controlador;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import java.time.LocalDate;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelo.ConexionBD;

public class VuelosController {

    @FXML
    private TextField origenField;
    @FXML
    private TextField destinoField;
    @FXML
    private DatePicker fechaPicker;
    @FXML
    private TextField salidaField;
    @FXML
    private TextField llegadaField;
    @FXML
    private ComboBox<String> idAvion;

    private int idVuelo;
    private boolean isNuevoVuelo;

    public void initialize(int vueloId, boolean isNuevoVuelo) {
        this.idVuelo = vueloId;
        this.isNuevoVuelo = isNuevoVuelo;

        if (!isNuevoVuelo) {
            try {
                ConexionBD con = new ConexionBD();
                PreparedStatement p = con.getConexion().prepareStatement("SELECT vuelos.*, aviones.*, trayectos.* FROM vuelos JOIN trayectos ON vuelos.idTrayecto = trayectos.idTrayecto JOIN aviones ON vuelos.idAvion = aviones.idAvion WHERE idVuelo = ?");
                p.setInt(1, vueloId);
                ResultSet r = p.executeQuery();
                if (r.next()) {
                    origenField.setText(r.getString("trayectos.origen"));
                    destinoField.setText(r.getString("trayectos.destino"));
                    salidaField.setText(r.getString("horaSalida"));
                    fechaPicker.setValue(r.getDate("fecha").toLocalDate());
                    llegadaField.setText(r.getString("horaLlegada"));
                    idAvion.getSelectionModel().select(r.getString("aviones.matricula"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Cargar los nombres de los aviones en el ComboBox
        cargarNombresAviones();
    }

    private void cargarNombresAviones() {
        ConexionBD conexionBD = new ConexionBD();
        String consulta = "SELECT matricula FROM aviones WHERE estado = 1";

        try {
            ResultSet resultSet = conexionBD.ejecutarConsulta(consulta);

            List<String> nombresAviones = new ArrayList<>();
            while (resultSet.next()) {
                String nombreAvion = resultSet.getString("matricula");
                nombresAviones.add(nombreAvion);
            }

            idAvion.getItems().addAll(nombresAviones);

            // Personalizar el convertidor de cadena para mostrar solo la matrícula del avión en el ComboBox
            idAvion.setConverter(new StringConverter<String>() {
                @Override
                public String toString(String object) {
                    return object;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            });
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Manejo de errores
        }
    }

    @FXML
    private void aceptar(ActionEvent event) throws SQLException {
        String origen = origenField.getText();
        String destino = destinoField.getText();
        LocalDate fechaSeleccionada = fechaPicker.getValue();
        String salida = salidaField.getText();
        String llegada = llegadaField.getText();
        String idAvionSeleccionado = idAvion.getValue();

        // Validar los campos
        if (origen.isEmpty() || destino.isEmpty() || fechaSeleccionada == null || salida.isEmpty() || llegada.isEmpty() || idAvionSeleccionado == null) {
            mostrarMensajeError("Por favor, complete todos los campos");
            return;
        }

        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaSeleccionada);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            ConexionBD con = new ConexionBD();
            PreparedStatement p;

            if (isNuevoVuelo) {
                p = con.getConexion().prepareStatement("INSERT INTO vuelos (fecha, horaSalida, horaLlegada, idTrayecto, idAvion) VALUES (?, ?, ?, ?, (SELECT idAvion FROM aviones WHERE matricula = ?))");
            } else {
                p = con.getConexion().prepareStatement("UPDATE vuelos SET fecha = ?, horaSalida = ?, horaLlegada = ?, idTrayecto = ?, idAvion = (SELECT idAvion FROM aviones WHERE matricula = ?) WHERE idVuelo = ?");
                p.setInt(6, idVuelo);
            }

            p.setDate(1, fechaSQL);
            p.setString(2, salida);
            p.setString(3, llegada);
            p.setInt(4, obtenerIdTrayecto(origen, destino));
            p.setString(5, idAvionSeleccionado);
            p.executeUpdate();

            mostrarMensajeInformacion("El vuelo se ha guardado correctamente");
            cerrarVentana();
        } catch (SQLException ex) {
            mostrarMensajeError("Ya existe un vuelo con ese identificador, por favor elija un identificador diferente");
        }
    }

    private int obtenerIdTrayecto(String origen, String destino) throws SQLException {
        ConexionBD con = new ConexionBD();
        PreparedStatement p = con.getConexion().prepareStatement("SELECT idTrayecto FROM trayectos WHERE origen = ? AND destino = ?");
        p.setString(1, origen);
        p.setString(2, destino);
        ResultSet r = p.executeQuery();

        if (r.next()) {
            return r.getInt("idTrayecto");
        } else {
            PreparedStatement insertStmt = con.getConexion().prepareStatement("INSERT INTO trayectos (origen, destino) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, origen);
            insertStmt.setString(2, destino);
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el idTrayecto del trayecto recién insertado");
            }
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cerrarVentana();
    }
    

    @FXML
    private void limpiar(ActionEvent event) {
        origenField.setText("");
        destinoField.setText("");
        fechaPicker.setValue(null);
        salidaField.setText("");
        llegadaField.setText("");
        idAvion.getSelectionModel().clearSelection();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) idAvion.getScene().getWindow();
        stage.close();
    }

    private void mostrarMensajeInformacion(String mensaje) {
        mostrarMensaje(Alert.AlertType.INFORMATION, "Información", mensaje);
    }

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
}
