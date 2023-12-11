/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mariadb.jdbc.Connection;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Logomens;

/**
 *
 * @author DAM
 */
public class LoginController {
    
    String usuario2;
    private Connection conexion;
    final AtomicBoolean autenticado = new AtomicBoolean(autenticar("root", "root"));
    @FXML
    private TextField usuario;
    @FXML
    private Button entrar;
    @FXML
    private PasswordField contrasenia;
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public Connection getConnection(){
        try {
            //El método forName() carga el driver en el programa
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        }
        Connection con;
        try {
            con = (Connection) DriverManager.getConnection("jdbc:mariadb://localhost:3306/proy3te4","root","root");
            return con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
     public boolean autenticar(String usuario, String contrasena) {
        try {
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM usuario WHERE NOMBRE = ? AND CONTRASENIA = ?");
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            boolean autenticado = rs.next();
            rs.close();
            stmt.close();
            con.close();
            return autenticado;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    private void loginTry(ActionEvent event) throws IOException {
        usuario2 = usuario.getText();
            String contrasena = new String(contrasenia.getText());
            autenticado.set(autenticar(usuario2, contrasena)); // Actualiza el valor de autenticado
            if (autenticado.get()) {
                entradaCorrecta();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/pestanias.fxml"));
                Pane ventana = (Pane) loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(ventana);
                stage.setScene(scene);
                stage.setTitle("Inicio de sesión");
                stage.show();
                // Cierro la ventana login
                 Stage myStage = (Stage) this.usuario.getScene().getWindow();
                 myStage.close();
            } else {
                entradaIncorrecta();
            }
    }
    
     public void entradaCorrecta(){
        JOptionPane.showMessageDialog(null, "Bienvenido!");
        Logomens log = new Logomens ();
        log.escribirRegistro("Conexión correcta al Login con usuario "+usuario);
    }
    public void entradaIncorrecta(){
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Por favor, inténtelo de nuevo.");
        Logomens log = new Logomens ();
        log.escribirRegistro("Conexión incorrecta al Login con usuario "+usuario);
    }
    
}
