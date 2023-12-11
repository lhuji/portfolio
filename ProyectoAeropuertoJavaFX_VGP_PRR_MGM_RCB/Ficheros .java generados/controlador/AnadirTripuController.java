/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import javafx.scene.control.Alert;
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

/**
 * FXML Controller class
 *
 * @author DAM
 */
public class AnadirTripuController {

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
    private ComboBox<String> categoria;
    
    File selectedFile;
    
    byte[] fotoBytes;
    
    int idTripu;
    boolean estado;
    @FXML
    private TextField dni;
    /**
     * Initializes the controller class.
     * @param idTripu
     * @param estado
     */
    public void initialize(int idTripu, boolean estado ) throws SQLException{
        this.estado = estado;
        this.idTripu = idTripu;
        ObservableList<String> cat = FXCollections.observableArrayList(
                "Piloto",
                "Copiloto",
                "Ing Vuelo",
                "Azafato"
        );
        categoria.setItems(cat);
        if(!estado){
            
            ConexionBD con = new ConexionBD();
            PreparedStatement p = con.getConexion().prepareStatement("select * from miembros where idTripulacion = ?");
            p.setInt(1, idTripu);
            ResultSet r = p.executeQuery();
            r.next();
            telefono.setText(r.getString("telefono"));
            nombre.setText(r.getString("nombre"));
            apellido1.setText(r.getString("apellido1"));
            apellido2.setText(r.getString("apellido2"));
            direccion.setText(r.getString("direccion"));
            fechaNac.setValue(LocalDate.parse(r.getString("fechaNacimiento")));
            correo.setText(r.getString("ecorreo"));
            categoria.setValue(r.getString("categoria"));
            fotoBytes = r.getBytes("foto");
            
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
        // Comprobar campos vacíos
        if (telefono.getText().isEmpty() || nombre.getText().isEmpty() || apellido1.getText().isEmpty() ||
            apellido2.getText().isEmpty() || direccion.getText().isEmpty() || fechaNac.getValue() == null ||
            correo.getText().isEmpty() || categoria.getSelectionModel().isEmpty() || selectedFile == null) {
            mostrarMensajeError("Por favor, complete todos los campos");
            return;
        }
        ConexionBD con = new ConexionBD();
        if(estado){
        PreparedStatement p = con.getConexion().prepareStatement(
        "INSERT INTO miembros (telefono, nombre, apellido1, apellido2, direccion, fechaNacimiento, ecorreo, categoria, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        p.setString(1,telefono.getText());
        p.setString(2,nombre.getText());
        p.setString(3,apellido1.getText());
        p.setString(4,apellido2.getText());
        p.setString(5,direccion.getText());
        p.setString(6,fechaNac.getValue().toString());
        p.setString(7, correo.getText());
        p.setString(8, categoria.getSelectionModel().getSelectedItem());
        p.setBytes(9,imagenBytes(selectedFile));
        p.executeUpdate();
        JOptionPane.showMessageDialog(null, "Tripulante añadido con éxito");
        Stage myStage = (Stage) this.nombre.getScene().getWindow();
        myStage.close();
        }
        if(!estado){
            PreparedStatement p = con.getConexion().prepareStatement(
            "UPDATE miembros SET telefono=?, nombre=?, apellido1=?, apellido2=?, direccion=?, fechaNacimiento=?, ecorreo=?, categoria=?, foto=? WHERE idTripulacion=?");
            if (selectedFile!=null){
                fotoBytes = imagenBytes(selectedFile);
            }
            p.setString(1,telefono.getText());
            p.setString(2,nombre.getText());
            p.setString(3,apellido1.getText());
            p.setString(4,apellido2.getText());
            p.setString(5,direccion.getText());
            p.setString(6,fechaNac.getValue().toString());
            p.setString(7, correo.getText());
            p.setString(8, categoria.getSelectionModel().getSelectedItem());
            p.setBytes(9,fotoBytes);
            p.setInt(10, idTripu);
            p.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tripulante editado con éxito");
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
            apellido1.setText("");
            apellido2.setText("");
            direccion.setText("");
            fechaNac.setValue(null);
            correo.setText("");
            categoria.setValue(null);
            selectedFile = null;
    }
    
    private byte[] imagenBytes(File file) {
        
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
