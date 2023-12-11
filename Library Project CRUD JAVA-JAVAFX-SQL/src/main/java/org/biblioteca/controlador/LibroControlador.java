package org.biblioteca.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.biblioteca.modelo.ConexionPool;
import org.biblioteca.modelo.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibroControlador {
    public Button actLibro;
    private String nombreUsuario;
    @FXML
    private Label fechaActual, horaActual, usuarioField;
    @FXML
    private TableView<Libro> librosTableView;
    @FXML
    private TextField tituloField, autorField, anioField, nuevoTituloField, nuevoAutorField, nuevoAnioField, actualizarTituloField, nuevoAutorActualizadoField, nuevaCantidadField;
    @FXML
    private ChoiceBox<String> seccionChoiceBox, seccionNuevoChoiceBox, seccionActualizarChoiceBox;
    @FXML
    private TableColumn<Libro, Integer> idColumn;
    @FXML
    private TableColumn<Libro, String> tituloColumn, autorColumn;
    @FXML
    private TableColumn<Libro, Integer> anoColumn, cantidadColumn;
    private boolean automaticUpdatesEnabled = true;
    public void inicializarConNombreUsuario(String nombreUsuario) {
        setNombreUsuario(nombreUsuario);
        initialize();
    }
    @FXML
    private void initialize() {


        Timeline timeline = new Timeline(
                new KeyFrame ( Duration.seconds(1), event -> {
                    // Actualiza la fecha y la hora
                    actualizarFechaYHora();
                    if (automaticUpdatesEnabled) {
                        llenarTablaConLibros();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        configurarColumnas();
        configurarTabla();


        usuarioField.setText("Bienvenido de nuevo "+ nombreUsuario);
    }

    public void setNombreUsuario(String nombreUsuario) {

        this.nombreUsuario = nombreUsuario;

    }

    private void actualizarFechaYHora() {
        // Obtiene la fecha y la hora actual
        Date now = new Date();

        // Formatea la fecha y la hora
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        // Actualiza las etiquetas
        fechaActual.setText(dateFormat.format(now));
        horaActual.setText(timeFormat.format(now));
    }
    private void configurarColumnas() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponible"));
    }
    @FXML
    void consultarLibros(ActionEvent actionEvent) {
        automaticUpdatesEnabled = !automaticUpdatesEnabled;

        String titulo = tituloField.getText();
        String autor = autorField.getText();
        int anio = anioField.getText().isEmpty() ? 0 : Integer.parseInt(anioField.getText());

        List<Libro> librosEncontrados = consultarLibros(titulo, autor, anio);
        mostrarLibrosEnTableView(librosEncontrados);

        // Update the table only if automatic updates are enabled
        if (automaticUpdatesEnabled) {
            llenarTablaConLibros();
        }
    }

    @FXML
    void registrarLibro(ActionEvent actionEvent) {
        Libro nuevoLibro = new Libro(0, nuevoTituloField.getText(), nuevoAutorField.getText(), nuevoAnioField.getText().isEmpty() ? 0 : Integer.parseInt(nuevoAnioField.getText()), 0);
        registrarLibro(nuevoLibro);
        nuevoTituloField.clear ();
        nuevoAutorField.clear ();
        nuevoAnioField.clear ();
        mostrarAlerta("Libro Registrado", "El nuevo libro se ha registrado exitosamente.");
        llenarTablaConLibros(); // Actualizar la tabla después de registrar un nuevo libro
    }

    @FXML
    void actualizarLibro(ActionEvent actionEvent) {
        // Obtén el id del libro seleccionado desde la tabla
        Libro libroSeleccionado = librosTableView.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {
            libroSeleccionado.tituloProperty().set(actualizarTituloField.getText());
            libroSeleccionado.autorProperty().set(nuevoAutorActualizadoField.getText());
            libroSeleccionado.cantidadDisponibleProperty().set(nuevaCantidadField.getText().isEmpty() ? 0 : Integer.parseInt(nuevaCantidadField.getText()));

            actualizarLibro(libroSeleccionado);
            mostrarAlerta("Libro Actualizado", "La información del libro se ha actualizado exitosamente.");
            llenarTablaConLibros(); // Actualizar la tabla después de actualizar un libro
        } else {
            mostrarAlerta("Libro no Seleccionado", "Por favor, selecciona un libro de la tabla para actualizar.");
        }
    }
    private List<Libro> consultarLibrosPorId(int idLibro) {
        List<Libro> librosEncontrados = new ArrayList<>();
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM libros WHERE idLibro = ?")) {

            stmt.setInt(1, idLibro);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("idLibro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anioPublicacion"),
                            rs.getInt("cantidadDisponible")
                    );
                    librosEncontrados.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librosEncontrados;
    }

    private void mostrarLibrosEnTableView(List<Libro> libros) {
        ObservableList<Libro> observableLibros = FXCollections.observableArrayList(libros);
        librosTableView.setItems(observableLibros);
    }

    void llenarTablaConLibros() {
        List<Libro> todosLosLibros = consultarTodosLosLibros();
        ObservableList<Libro> observableLibros = FXCollections.observableArrayList(todosLosLibros);
        librosTableView.setItems(observableLibros);
    }

    private List<Libro> consultarTodosLosLibros() {
        List<Libro> todosLosLibros = new ArrayList<>();
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM libros");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("idLibro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anioPublicacion"),
                        rs.getInt("cantidadDisponible")
                );
                todosLosLibros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todosLosLibros;
    }
    private List<Libro> consultarLibros(String titulo, String autor, int anio) {
        List<Libro> librosEncontrados = new ArrayList<>();
        StringBuilder consulta = new StringBuilder("SELECT * FROM libros WHERE 1 = 1");

        if (!titulo.isEmpty()) {
            consulta.append(" AND titulo LIKE ?");
        }

        if (!autor.isEmpty()) {
            consulta.append(" AND autor LIKE ?");
        }

        if (anio != 0) {
            consulta.append(" AND anioPublicacion = ?");
        }

        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(consulta.toString())) {

            int indiceParametro = 1;

            if (!titulo.isEmpty()) {
                stmt.setString(indiceParametro++, "%" + titulo + "%");
            }

            if (!autor.isEmpty()) {
                stmt.setString(indiceParametro++, "%" + autor + "%");
            }

            if (anio != 0) {
                stmt.setInt(indiceParametro, anio);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("idLibro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getInt("anioPublicacion"),
                            rs.getInt("cantidadDisponible")
                    );
                    librosEncontrados.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librosEncontrados;
    }

    public void registrarLibro(Libro nuevoLibro) {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO libros (titulo, autor, anioPublicacion, cantidadDisponible) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, nuevoLibro.getTitulo());
            stmt.setString(2, nuevoLibro.getAutor());
            stmt.setInt(3, nuevoLibro.getAnioPublicacion());
            stmt.setInt(4, nuevoLibro.getCantidadDisponible());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarLibro(Libro libroActualizado) {
        try (Connection conn = ConexionPool.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement("UPDATE libros SET titulo=?, autor=?, anioPublicacion=?, cantidadDisponible=? WHERE idLibro=?")) {

            stmt.setString(1, libroActualizado.getTitulo());
            stmt.setString(2, libroActualizado.getAutor());
            stmt.setInt(3, libroActualizado.getAnioPublicacion());
            stmt.setInt(4, libroActualizado.getCantidadDisponible());
            stmt.setInt(5, libroActualizado.getIdLibro());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void configurarTabla() {
        // Configurar el listener para el cambio de selección en la tabla
        librosTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Libro>() {
            @Override
            public void changed(ObservableValue<? extends Libro> observable, Libro oldValue, Libro newValue) {
                if (newValue != null) {
                    // Llenar los campos de actualización con la información del libro seleccionado
                    actualizarTituloField.setText(newValue.getTitulo());
                    nuevoAutorActualizadoField.setText(newValue.getAutor());
                    nuevaCantidadField.setText(String.valueOf(newValue.getCantidadDisponible()));
                }
            }
        });

        // Configurar las celdas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponible"));
    }
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    public void actualizarTabla(ActionEvent event) {
        automaticUpdatesEnabled = true;
    }
}
