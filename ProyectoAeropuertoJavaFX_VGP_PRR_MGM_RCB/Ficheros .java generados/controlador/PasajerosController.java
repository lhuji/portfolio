/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import modelo.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.io.File;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import modelo.ConexionBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Vuelo;

/**
 * FXML Controller class
 *
 * @author dam1
 */
public class PasajerosController {

    @FXML
    private Button selecFoto;
    @FXML
    private Button aceptar;
    @FXML
    private Button cancelar;
    @FXML
    private Button limpiar;
    @FXML
    private TextField telefono;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido1;
    @FXML
    private TextField apellido2;
    @FXML
    private TextField direccion;
    @FXML
    private DatePicker fechaNac;
    @FXML
    private TextField correo;
    @FXML
    private TextField dniField;
    File selectedFile;
    byte[] fotoBytes;
    private int idPasajeros;
    private boolean isNuevoDNI;
    int idVuelo;
    @FXML
    private ComboBox<Vuelo> emergentePasajIDVuelo;

    /**
     * Initializes the controller class.
     * @param idPasajeros
     * @param isNuevoDNI
     * @param obs
     */
    
    public void initialize(int idPasajeros, boolean isNuevoDNI, ObservableList<Vuelo> obs) throws SQLException {
        this.idPasajeros = idPasajeros;
        this.isNuevoDNI = isNuevoDNI;
        emergentePasajIDVuelo.setItems(obs);
        
        
        if(!isNuevoDNI){
            
            ConexionBD con = new ConexionBD();
            PreparedStatement p = con.getConexion().prepareStatement("select * from pasajeros where idPasajeros = ?");
            p.setInt(1, idPasajeros);
            ResultSet r = p.executeQuery();
            r.next();
            telefono.setText(r.getString("telefono"));
            nombre.setText(r.getString("nombre"));
            apellido1.setText(r.getString("apellido1"));
            apellido2.setText(r.getString("apellido2"));
            direccion.setText(r.getString("direccion"));
            fechaNac.setValue(LocalDate.parse(r.getString("fechaNacimiento")));
            correo.setText(r.getString("ecorreo"));
            dniField.setText(r.getString("dni"));
            fotoBytes = r.getBytes("foto");
            p = con.getConexion().prepareStatement("select idVuelo from pasajeros_vuelos where idPasajeros = ?");
            p.setInt(1, idPasajeros);
            r = p.executeQuery();
            r.next();
            int idVuelo = r.getInt("idVuelo");
            for (Vuelo v:obs){
                   if(v.getIdVuelo()==idVuelo){
                       emergentePasajIDVuelo.setValue(v);
                   }
            }
            
            
        }
    }    

    @FXML
    private void elegirFoto(ActionEvent event) {
        
        FileChooser fileChooser = new FileChooser();

        // Configura el título del diálogo de selección de archivo
        fileChooser.setTitle("Seleccionar archivo");
        List<String> extensiones = new ArrayList<>();
        extensiones.add("*.jpg");
        extensiones.add("*.png");
        extensiones.add("*.gif");
        // Agrega filtros de extensión de archivo (opcional)
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de imagen", extensiones)
        );

        // Obtiene la ventana actual y muestra el diálogo de selección de archivo
        Stage stage = (Stage) selecFoto.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Realiza alguna acción con el archivo seleccionado
            System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void aceptar(ActionEvent event) throws SQLException {
        if (telefono.getText().isEmpty() || nombre.getText().isEmpty() || apellido1.getText().isEmpty() ||
            apellido2.getText().isEmpty() || direccion.getText().isEmpty() || fechaNac.getValue() == null ||
            correo.getText().isEmpty() || dniField.getText().isEmpty() || selectedFile == null) {
            mostrarMensajeError("Por favor, complete todos los campos");
            return;
        }
        ConexionBD con = new ConexionBD();
        if(isNuevoDNI){
        PreparedStatement p = con.getConexion().prepareStatement(
        "INSERT INTO pasajeros (nombre, apellido1, apellido2, fechaNacimiento, ecorreo, foto, telefono, direccion, dni) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        p.setString(1,nombre.getText());
        p.setString(2,apellido1.getText());
        p.setString(3,apellido2.getText());
        p.setString(4,fechaNac.getValue().toString());
        p.setString(5,correo.getText());
        p.setBytes(6,fotoBytes(selectedFile));
        p.setString(7, telefono.getText());
        p.setString(8, direccion.getText());
        p.setString(9,dniField.getText());
        p.executeUpdate();
        String query3 = "SELECT idPasajeros from pasajeros where dni=" + "'"+dniField.getText()+"'";
        ResultSet resul2 = con.ejecutarConsulta(query3);
        resul2.next();
        int idPasajeroAA= resul2.getInt("idPasajeros");
        p = con.getConexion().prepareStatement("insert into pasajeros_vuelos values (?,?,?)");
        p.setInt(1, emergentePasajIDVuelo.getSelectionModel().getSelectedItem().getIdVuelo());
        p.setInt(2, emergentePasajIDVuelo.getSelectionModel().getSelectedItem().getIdAvion());
        p.setInt(3, idPasajeroAA);
        p.executeUpdate();
        JOptionPane.showMessageDialog(null, "Pasajero añadido con éxito");
        Stage myStage = (Stage) this.nombre.getScene().getWindow();
        myStage.close();
        }
        else {
        PreparedStatement p = con.getConexion().prepareStatement(
                "UPDATE pasajeros SET nombre=?, apellido1=?, apellido2=?, fechaNacimiento=?, ecorreo=?, foto=?, telefono=?, direccion=?, dni=? WHERE idPasajeros=?");
        if (selectedFile != null) {
            fotoBytes = fotoBytes(selectedFile);
        }
        p.setString(1, nombre.getText());
        p.setString(2, apellido1.getText());
        p.setString(3, apellido2.getText());
        p.setString(4, fechaNac.getValue().toString());
        p.setString(5, correo.getText());
        p.setBytes(6, fotoBytes);
        p.setString(7, telefono.getText());
        p.setString(8, direccion.getText());
        p.setString(9, dniField.getText());
        p.setInt(10, idPasajeros);
        p.executeUpdate();
        p = con.getConexion().prepareStatement("update pasajeros_vuelos set idVuelo=?,idAvion=? where idPasajeros=?");
        p.setInt(1, emergentePasajIDVuelo.getSelectionModel().getSelectedItem().getIdVuelo());
        p.setInt(2, emergentePasajIDVuelo.getSelectionModel().getSelectedItem().getIdAvion());
        p.setInt(3, idPasajeros);
        p.executeUpdate();
        JOptionPane.showMessageDialog(null, "Pasajero editado con éxito");
        Stage myStage = (Stage) this.nombre.getScene().getWindow();
        myStage.close();
    }
    }
    @FXML
    private void cancelar(ActionEvent event) {
        Stage myStage = (Stage) this.nombre.getScene().getWindow();
        myStage.close();
    }
    

    @FXML
    private void limpiar(ActionEvent event) {
        telefono.setText("");
        nombre.setText("");
        fechaNac.setValue(null);
        apellido1.setText("");
        apellido2.setText("");
        direccion.setText("");
        correo.setText("");
        dniField.setText("");
        
    }

    private void cerrarVentana() {
        Stage stage = (Stage) dniField.getScene().getWindow();
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
    private byte[] fotoBytes(File file) {
        
        Image image = new Image(file.toURI().toString());

        // Convertir la imagen a bytes
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Crear una imagen en formato BufferedImage para obtener los bytes
            BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);

            // Escribir la imagen en el ByteArrayOutputStream en formato PNG
            ImageIO.write(bufferedImage, "png", outputStream);

            // Obtener los bytes de la imagen
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
