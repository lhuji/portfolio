package controlador;

import javafx.fxml.FXML;

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
import javax.swing.JOptionPane;
import modelo.Tripulante;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 */
public class detalleTripuController {

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
    private TextField categoria;
    @FXML
    private ImageView fotoBytes;

    private Image fotoImage;

    private String correoPasajero;

    public void initialize(Tripulante tripulanteseleccionado) throws SQLException {
        correoPasajero = tripulanteseleccionado.getEmail();
        ConexionBD con = new ConexionBD();
        PreparedStatement p = con.getConexion().prepareStatement("SELECT * FROM miembros WHERE ecorreo = ?");
        p.setString(1, correoPasajero);
        ResultSet r = p.executeQuery();
        r.next();
        telefono.setText(r.getString("telefono"));
        nombre.setText(r.getString("nombre"));
        apellido1.setText(r.getString("apellido1"));
        apellido2.setText(r.getString("apellido2"));
        direccion.setText(r.getString("direccion"));
        fechaNac.setValue(LocalDate.parse(r.getString("fechaNacimiento")));
        correo.setText(r.getString("ecorreo"));
        categoria.setText(r.getString("categoria"));
        byte[] fotoBytesArray = r.getBytes("foto");
        fotoImage = new Image(new ByteArrayInputStream(fotoBytesArray));
        fotoBytes.setImage(fotoImage);
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
}
