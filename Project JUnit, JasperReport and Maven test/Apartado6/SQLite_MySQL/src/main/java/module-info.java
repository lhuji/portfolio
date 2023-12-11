module com.practicag.ddi_2practica_g {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires waffle.jna;
    requires org.mariadb.jdbc;
    requires jasperreports;
    opens com.practicag4.ddi_2practica_g4 to javafx.fxml;
    exports com.practicag4.ddi_2practica_g4;
    exports com.practicag4.ddi_2practica_g4.Controller;
    exports com.practicag4.ddi_2practica_g4.Model;
    opens com.practicag4.ddi_2practica_g4.Controller to javafx.fxml;
}