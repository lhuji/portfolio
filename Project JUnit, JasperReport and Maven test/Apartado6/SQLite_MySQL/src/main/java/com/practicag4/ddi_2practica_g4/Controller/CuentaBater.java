package com.practicag4.ddi_2practica_g4.Controller;

import java.io.InputStream;
import java.net.URL;
import java.sql.*;

import com.practicag4.ddi_2practica_g4.Model.SegundaConsultaResultado;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import com.practicag4.ddi_2practica_g4.Model.Cuenta;
import com.practicag4.ddi_2practica_g4.Model.ValidadorCuenta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuentaBater {
    @FXML
    public Pane paneCreacion;
    @FXML
    public Pane paneGeneral;
    @FXML
    public Pane paneModificar;
    @FXML
    public Label numLabel;
    @FXML
    public Label saldoLabel;
    @FXML
    public Label titLabel;
    @FXML
    public Label fecLabel;
    @FXML
    public Label nacLabel;
    @FXML
    public TextField numLabelvacio;
    @FXML
    public TextField saldoLabelvacio;
    @FXML
    public TextField titLabelvacio;
    @FXML
    public TextField fecLabelvacio;
    @FXML
    public TextField nacLabelvacio;
    @FXML
    public Button botonAtras;
    @FXML
    public Button botonDelante;
    @FXML
    public Button botonCrear;
    @FXML
    public Button generarPDF;
    @FXML
    public Button botonEliminar;
    @FXML
    public Button botonModificar;
    @FXML Button botonUpdate;
    public static List < Cuenta >  cuentas;
    public int currentIndex;
    @FXML
    public Label subtituloLabel;
    @FXML
    public Label fechaApertura;

    public CuentaBater() {
        this.cuentas = obtenerCuentasDesdeBD ();
        currentIndex = 0;
    }
    public List<Cuenta> obtenerCuentasDesdeBD() {
        List<Cuenta> cuentas = new ArrayList<>();

        // Definir la URL de conexión a la base de datos
        String url = "jdbc:mariadb://localhost:3306/visor?user=root&password=root";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Consulta SQL para obtener las cuentas
            String sql = "SELECT numeroCuenta, titular, fechaApertura, saldo, nacionalidad FROM cuenta";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String numeroCuenta = rs.getString("numeroCuenta");
                    String titular = rs.getString("titular");
                    Date fechaApertura = rs.getDate("fechaApertura");
                    double saldo = rs.getDouble("saldo");
                    String nacionalidad = rs.getString("nacionalidad");

                    Cuenta cuenta = new Cuenta(numeroCuenta, titular, fechaApertura, saldo, nacionalidad);
                    cuentas.add(cuenta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cuentas;
    }

        @FXML
    void initialize() {

        mostrarCuenta();
        paneModificar.setVisible ( false );
        numLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        saldoLabel.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        ContextMenu Fecha = new ContextMenu ();
        MenuItem escribeFecha = new MenuItem ("YYYY-mm-dd");
        escribeFecha.setOnAction (this::mostrarFecha);

        Fecha.getItems ().add (escribeFecha);
        fechaApertura.setContextMenu (Fecha);

        // Crear un ContextMenu y agregar elementos de menú a él
        ContextMenu contextMenuAdelante = new ContextMenu();

        MenuItem opcionEspañol = new MenuItem("Adelante");
        opcionEspañol.setOnAction(this::handleEspanol);

        MenuItem opcionIngles = new MenuItem("Forward");
        opcionIngles.setOnAction(this::handleIngles);

        MenuItem opcionFrances = new MenuItem("En avant");
        opcionFrances.setOnAction(this::handleFrances);

        MenuItem opcionPortugues = new MenuItem("Para a frente");
        opcionPortugues.setOnAction(this::handlePortugues);

        contextMenuAdelante.getItems().addAll(opcionEspañol, opcionIngles, opcionFrances, opcionPortugues);

        // Asociar el ContextMenu con los botones
        botonDelante.setContextMenu(contextMenuAdelante);

        // Crear un ContextMenu y agregar elementos de menú a él
        ContextMenu contextMenuAtras = new ContextMenu();

        MenuItem opcionEspañolAtras = new MenuItem("Atrás");
        opcionEspañol.setOnAction(this::handleEspanolAtras);

        MenuItem opcionInglesAtras = new MenuItem("Back");
        opcionIngles.setOnAction(this::handleInglesAtras);

        MenuItem opcionFrancesAtras = new MenuItem("En arrière");
        opcionFrances.setOnAction(this::handleFrancesAtras);

        MenuItem opcionPortuguesAtras = new MenuItem("Para trás");
        opcionPortugues.setOnAction(this::handlePortuguesAtras);

        contextMenuAtras.getItems().addAll(opcionEspañolAtras, opcionInglesAtras, opcionFrancesAtras, opcionPortuguesAtras);
        botonAtras.setContextMenu(contextMenuAtras);
    }
    @FXML
    void mostrarSiguienteCuenta() {
        if (!cuentas.isEmpty()) {
            currentIndex++;
            if (currentIndex >= cuentas.size()) {
                currentIndex = cuentas.size() - 1; // Ajusta el índice al último elemento
                // No hay más cuentas, muestra el panel de creación
                paneGeneral.setVisible (false);
                paneCreacion.setVisible(true);
                // También puedes reiniciar los campos de entrada para que estén vacíos
                numLabelvacio.setText("");
                saldoLabelvacio.setText("");
                titLabelvacio.setText("");
                fecLabelvacio.setText("");
                nacLabelvacio.setText ("");
            } else {
                mostrarCuenta();
            }
        }
    }
    @FXML
    void mostrarCreacion() {
        subtituloLabel.setText ( "CREAR NUEVA CUENTA" );
        numLabelvacio.setEditable(true);
        paneGeneral.setVisible (false);
        paneCreacion.setVisible(true);
        numLabelvacio.setText("");
        saldoLabelvacio.setText("");
        titLabelvacio.setText("");
        fecLabelvacio.setText("");
        nacLabelvacio.setText ("");
    }
    @FXML
    void mostrarAnteriorCuenta() {
        if (!cuentas.isEmpty()) {
            currentIndex = (currentIndex - 1 + cuentas.size()) % cuentas.size();
            mostrarCuenta();
        }
    }
    @FXML
    void modificar() {
        subtituloLabel.setText ( "MODIFICAR CUENTA" );
        // Verificar si hay cuentas para modificar
        if (cuentas.isEmpty() || currentIndex >= cuentas.size()) {
            mostrarError("Error", "No hay cuentas para modificar.");
            return;
        }

        // Obtener la cuenta actual
        Cuenta cuentaActual = cuentas.get(currentIndex);

        // Llenar el panel de creación con los datos de la cuenta actual
        numLabelvacio.setText(cuentaActual.getNumeroCuenta());
        saldoLabelvacio.setText(String.valueOf(cuentaActual.getSaldo()));
        titLabelvacio.setText(cuentaActual.getTitular());
        fecLabelvacio.setText(cuentaActual.getFechaApertura().toString());
        nacLabelvacio.setText(cuentaActual.getNacionalidad());
        numLabelvacio.setEditable(false);
        // Mostrar el panel de creación para modificar la cuenta
        paneModificar.setVisible(true);
        paneGeneral.setVisible(false);
        paneCreacion.setVisible(true);

    }
    @FXML
    void update() {
        // Obtener la cuenta actual
        Cuenta cuentaActual = cuentas.get(currentIndex);

        // Obtener los nuevos valores de los campos de texto
        String nuevoNumeroCuenta = numLabelvacio.getText();
        String nuevoTitular = titLabelvacio.getText();
        String nuevoSaldoStr = saldoLabelvacio.getText();
        String nuevaFechaStr = fecLabelvacio.getText();
        String nuevaNacionalidad = nacLabelvacio.getText();


        ValidadorCuenta va = new ValidadorCuenta ();

        // Lógica de actualización aquí
        List<String> errores = va.validarModificarCuenta (
                numLabelvacio.getText(),
                titLabelvacio.getText(),
                saldoLabelvacio.getText(),
                numLabelvacio,
                titLabelvacio,
                saldoLabelvacio,
                cuentas,
                nacLabelvacio,
                fecLabelvacio.getText(),
                fecLabelvacio,
                cuentaActual.getNumeroCuenta());

        if (!errores.isEmpty()) {
            // Mostrar errores si los hay
            String mensajeError = "Se han encontrado los siguientes errores:\n\n";
            for (String error : errores) {
                mensajeError += "- " + error + "\n";
            }
            mostrarError("Error", mensajeError);
        } else {
            // Actualizar la cuenta actual con los nuevos valores
            cuentaActual.setNumeroCuenta(nuevoNumeroCuenta);
            cuentaActual.setTitular(nuevoTitular);
            cuentaActual.setSaldo(Double.parseDouble(nuevoSaldoStr));
            cuentaActual.setFechaApertura(Date.valueOf(LocalDate.parse(nuevaFechaStr)));
            cuentaActual.setNacionalidad(nuevaNacionalidad);

            // Actualizar la cuenta en la base de datos
            if (actualizarCuentaEnBD(cuentaActual)) {
                // Mostrar mensaje de éxito
                mostrarMensajeExito("Cuenta actualizada con éxito.");
            } else {
                mostrarError("Error", "No se pudo actualizar la cuenta en la base de datos.");
            }

            // Volver al panel general
            paneModificar.setVisible(false);
            paneCreacion.setVisible(false);
            paneGeneral.setVisible(true);

            // Volver a cargar las cuentas desde la base de datos
            cuentas = obtenerCuentasDesdeBD();

            // Mostrar la cuenta actualizada
            mostrarCuenta();
        }
    }

    @FXML
    void eliminar() {
        // Verificar si hay cuentas para eliminar
        if (cuentas.isEmpty() || currentIndex >= cuentas.size()) {
            mostrarError("Error", "No hay cuentas para eliminar.");
            return;
        }

        // Obtener la cuenta actual
        Cuenta cuentaActual = cuentas.get(currentIndex);

        // Eliminar la cuenta de la base de datos
        if (eliminarCuentaDeBD(cuentaActual.getNumeroCuenta())) {
            // Eliminar la cuenta de la lista local
            cuentas.remove(currentIndex);

            // Actualizar la visualización
            mostrarCuenta();

            mostrarMensajeExito("Cuenta eliminada con éxito.");
        } else {
            mostrarError("Error", "No se pudo eliminar la cuenta de la base de datos.");
        }
    }
    @FXML
    void crearNuevaCuenta() {
        String numeroCuenta = numLabelvacio.getText();
        String titular = titLabelvacio.getText();
        String saldoStr = saldoLabelvacio.getText();
        String fecha = fecLabelvacio.getText();
        String nacionalidad = nacLabelvacio.getText ();

        ValidadorCuenta valida = new ValidadorCuenta ();
        List<String> errores = valida.validarNuevaCuenta(numeroCuenta, titular, saldoStr, numLabelvacio, titLabelvacio, saldoLabelvacio, cuentas, nacLabelvacio, fecha, fecLabelvacio, nacionalidad);
        if (!errores.isEmpty()) {
            String mensajeError = "Se han encontrado los siguientes errores:\n\n";

            for (String error : errores) {
                mensajeError += "- " + error + "\n";
            }

            mostrarError("Error",mensajeError);
        }
        else{
            // Definir la URL de conexión a la base de datos
            String url = "jdbc:mariadb://localhost:3306/visor?user=root&password=root";

            try (Connection conn = DriverManager.getConnection (url)) {
                // Consulta SQL para obtener las cuentas
                String sql = "INSERT INTO cuenta (numeroCuenta, titular, fechaApertura, saldo, nacionalidad) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement (sql)) {
                    stmt.setString (1, numeroCuenta);
                    stmt.setString (2, titular);
                    stmt.setDate(3, Date.valueOf(LocalDate.now()));
                    stmt.setDouble (4, Double.parseDouble (saldoStr));
                    stmt.setString (5, nacionalidad);

                    stmt.executeUpdate ();
                }
            } catch (SQLException e) {
                e.printStackTrace ();
            }
            mostrarMensajeExito ("Cuenta creada sin problemas");
            paneCreacion.setVisible ( false );
            paneGeneral.setVisible ( true );
            cuentas = obtenerCuentasDesdeBD ();
            mostrarCuenta ();
        }
    }
    // Método para mostrar un mensaje de éxito
    private void mostrarMensajeExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    @FXML
    void cancelarCreacion() {
        if (!cuentas.isEmpty()) {
            // Configura currentIndex para que sea la última posición de la lista de cuentas
            currentIndex = cuentas.size() - 1;
        } else {
            // Si no hay cuentas, currentIndex se queda en 0.
            currentIndex = 0;
        }

        // Oculta el paneCreacion
        paneModificar.setVisible(false);
        paneCreacion.setVisible(false);
        paneGeneral.setVisible (true);
        subtituloLabel.setText ( "CUENTAS EXISTENTES" );
        // Muestra la cuenta correspondiente según el valor actual de currentIndex
        mostrarCuenta();
    }
    void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean actualizarCuentaEnBD(Cuenta cuenta) {
        // Lógica para actualizar la cuenta en la base de datos
        String url = "jdbc:mariadb://localhost:3306/visor?user=root&password=root";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Consulta SQL para actualizar la cuenta
            String sql = "UPDATE cuenta SET titular = ?, fechaApertura = ?, saldo = ?, nacionalidad = ? WHERE numeroCuenta = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cuenta.getTitular());
                stmt.setDate(2, ( Date ) cuenta.getFechaApertura() );
                stmt.setDouble(3, cuenta.getSaldo());
                stmt.setString(4, cuenta.getNacionalidad());
                stmt.setString(5, cuenta.getNumeroCuenta());

                int filasAfectadas = stmt.executeUpdate();

                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retorna false si hubo algún error
        return false;
    }
    void mostrarCuenta() {
        if (cuentas.isEmpty()) {
            numLabel.setText("");
            saldoLabel.setText("");
            titLabel.setText("");
            fecLabel.setText("");
            nacLabel.setText ("");
            // En la primera posición, oculta el botón para ir hacia atrás
            botonAtras.setVisible(false);
            subtituloLabel.setText ("Nueva Cuenta");
            // Cambia el texto del botónDelante a "Nueva" si no hay cuentas
            botonDelante.setText("Nueva");
        } else {
            // Asegúrate de que currentIndex esté en el rango válido
            currentIndex = Math.max(0, Math.min(currentIndex, cuentas.size() - 1));
            Cuenta cuentaActual = cuentas.get(currentIndex);
            numLabel.setText(cuentaActual.getNumeroCuenta()+ "  ");
            saldoLabel.setText((cuentaActual.getSaldo ())+ "0€  ");
            titLabel.setText("  " +cuentaActual.getTitular());
            fecLabel.setText("  " +cuentaActual.getFechaApertura().toString());
            nacLabel.setText (cuentaActual.getNacionalidad ());


            // Muestra el botón para ir hacia atrás si no estamos en la primera posición
            botonAtras.setVisible(currentIndex > 0);

            // Cambia el texto del botónDelante a "Nueva" cuando estás en la última cuenta
            if (currentIndex == cuentas.size() - 1) {
                botonDelante.setText("Nueva");
            } else {
                botonDelante.setText(">>");
            }
            if (currentIndex == cuentas.size()) {
                subtituloLabel.setText("Crear Nueva Cuenta");
            } else {

            }
        }
    }
    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
        currentIndex = 0;
        // Llama al método mostrarCuenta para mostrar la primera cuenta en la lista actualizada
        mostrarCuenta();
    }
    @FXML
    private void mostrarFecha(ActionEvent event){
        mostrarMensaje("Agregue este formato de fecha.");
    }
    @FXML
    private void handleEspanol(ActionEvent event) {
        mostrarMensaje("Español seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Español si es necesario.
    }

    @FXML
    private void handleIngles(ActionEvent event) {
        mostrarMensaje("Inglés seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Inglés si es necesario.
    }

    @FXML
    private void handleFrances(ActionEvent event) {
        mostrarMensaje("Francés seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Francés si es necesario.
    }

    @FXML
    private void handlePortugues(ActionEvent event) {
        mostrarMensaje("Portugués seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Portugués si es necesario.
    }

    @FXML
    private void handleEspanolAtras(ActionEvent event) {
        mostrarMensaje("Español seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Español si es necesario.
    }

    @FXML
    private void handleInglesAtras(ActionEvent event) {
        mostrarMensaje("Inglés seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Inglés si es necesario.
    }

    @FXML
    private void handleFrancesAtras(ActionEvent event) {
        mostrarMensaje("Francés seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Francés si es necesario.
    }

    @FXML
    private void handlePortuguesAtras(ActionEvent event) {
        mostrarMensaje("Portugués seleccionado.");
        // Agrega aquí la lógica para cambiar el idioma a Portugués si es necesario.
    }
    @FXML
    private void generarHTML() {
        try {
            // Cambia la ruta del archivo Jasper a la ruta relativa en el classpath
            String jasperFile = "/VisorHTML.jasper";

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

            // Cargar el archivo JasperReport compilado
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);


            // Obtener datos de la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/visor", "root", "root");

            String query = "SELECT * FROM cuenta ORDER BY nacionalidad";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Convertir los resultados de la consulta a una lista de JavaBeans
            List<Cuenta> cuentas = new ArrayList<>();
            while (resultSet.next()) {
                // Obtener la fecha de apertura como LocalDate
                LocalDate fechaApertura = resultSet.getObject("fechaApertura", LocalDate.class);

                // Convertir LocalDate a java.sql.Date
                java.sql.Date fechaAperturaUtilDate = java.sql.Date.valueOf(fechaApertura);

                // Crear un objeto Cuenta utilizando el constructor definido
                Cuenta cuenta = new Cuenta(
                        resultSet.getString("numeroCuenta"),
                        resultSet.getString("titular"),
                        fechaAperturaUtilDate,
                        resultSet.getDouble("saldo"),
                        resultSet.getString("nacionalidad")
                );
                // Agregar la cuenta a la lista
                cuentas.add(cuenta);
            }

            // En este ejemplo, usaremos una fuente de datos JRBeanCollectionDataSource con la lista de cuentas
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cuentas);

            // Obtener datos de la base de datos para la segunda consulta
            String query2 = "SELECT " +
                    "COUNT(*) AS total_clientes, " +
                    "CASE " +
                    "    WHEN (saldo <= 1000) THEN 'MenorIgual1000' " +
                    "    ELSE 'Mayor1000' " +
                    "END AS categoria, " +
                    "COUNT(*) AS cantidad, " +
                    "(COUNT(*) * 100.00 / (SELECT COUNT(*) FROM cuenta)) AS porcentaje " +
                    "FROM cuenta " +
                    "GROUP BY categoria";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            // Convertir los resultados de la segunda consulta a una lista
            List<SegundaConsultaResultado> resultadosSegundaConsulta = new ArrayList<>();
            while (resultSet2.next()) {
                SegundaConsultaResultado resultado = new SegundaConsultaResultado(
                        resultSet2.getLong("total_clientes"),
                        resultSet2.getString("categoria"),
                        resultSet2.getLong("cantidad"),
                        resultSet2.getBigDecimal("porcentaje")
                );
                resultadosSegundaConsulta.add(resultado);
            }

            // Agregar los resultados de la segunda consulta como un subdataset
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(resultadosSegundaConsulta);
            parameters.put("Porcentajes", dataSource2);

            // Llenar el informe con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Exportar el informe a formato HTML
            String htmlFile = "VisorHTML.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, htmlFile);

            // Mostrar el informe en un visor (opcional)
            JasperViewer.viewReport(jasperPrint, false);

            // Cerrar la conexión a la base de datos
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al generar el informe HTML: " + e.getMessage());
        }
    }

    @FXML
    private void generarPDF() {
        try {
            // Cambia la ruta del archivo Jasper a la ruta relativa en el classpath
            String jasperFile = "/VisorPDF.jasper";

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

            // Cargar el archivo JasperReport compilado
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

            // Obtener datos de la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/visor", "root", "root");
            String query = "SELECT * FROM cuenta";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Convertir los resultados de la consulta a una lista de JavaBeans
            List<Cuenta> cuentas = new ArrayList<>();
            while (resultSet.next()) {
                // Obtener la fecha de apertura como LocalDate
                LocalDate fechaApertura = resultSet.getObject("fechaApertura", LocalDate.class);

                // Convertir LocalDate a java.sql.Date
                java.sql.Date fechaAperturaUtilDate = java.sql.Date.valueOf(fechaApertura);

                // Crear un objeto Cuenta utilizando el constructor definido
                Cuenta cuenta = new Cuenta(
                        resultSet.getString("numeroCuenta"),
                        resultSet.getString("titular"),
                        fechaAperturaUtilDate,
                        resultSet.getDouble("saldo"),
                        resultSet.getString("nacionalidad")
                );

                // Agregar la cuenta a la lista
                cuentas.add(cuenta);
            }



            // En este ejemplo, usaremos una fuente de datos JRBeanCollectionDataSource con la lista de cuentas
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cuentas);

            // Llenar el informe con datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar el informe a formato PDF
            String pdfFile = "VisorPDF.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);

            // Mostrar el informe en un visor (opcional)
            JasperViewer.viewReport(jasperPrint, false);

            // Cerrar la conexión a la base de datos
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private boolean eliminarCuentaDeBD(String numeroCuenta) {

        String url = "jdbc:mariadb://localhost:3306/visor?user=root&password=root";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Consulta SQL para eliminar la cuenta
            String sql = "DELETE FROM cuenta WHERE numeroCuenta = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, numeroCuenta);

                int filasAfectadas = stmt.executeUpdate();

                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retorna false si hubo algún error
        return false;
    }
    public List<Cuenta> mayores50000() {
        List<Cuenta> listaCuentas = new ArrayList<>();

        // Configuración para MySQL
        String url = "jdbc:mysql://localhost:3306/visor";
        String usuario = "root";
        String contraseña = "root";

        try {
            // Establecer la conexión con MySQL
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta para obtener cuentas con saldo mayor a 50000
            String consulta = "SELECT * FROM cuenta WHERE saldo > 50000";
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Recorrer el resultado y agregar cuentas a la lista
            while (resultSet.next()) {
                String numeroCuenta = resultSet.getString("numeroCuenta");
                String titular = resultSet.getString("titular");
                Date fechaApertura = resultSet.getDate("fechaApertura");
                double saldo = resultSet.getDouble("saldo");
                String nacionalidad = resultSet.getString("nacionalidad");

                // Crear un objeto Cuenta y agregarlo a la lista
                Cuenta cuenta = new Cuenta(numeroCuenta, titular, fechaApertura, saldo, nacionalidad);
                listaCuentas.add(cuenta);
            }

            // Cerrar la conexión
            connection.close();
        } catch (SQLException e) {
            // Manejar excepciones (puedes imprimir un mensaje o lanzar una excepción personalizada)
            e.printStackTrace();
        }

        return listaCuentas;
    }
}