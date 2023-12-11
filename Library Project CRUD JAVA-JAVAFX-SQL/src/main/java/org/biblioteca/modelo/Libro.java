package org.biblioteca.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Libro {
    private final IntegerProperty idLibro;
    private final StringProperty titulo;
    private final StringProperty autor;
    private final IntegerProperty anioPublicacion;
    private final IntegerProperty cantidadDisponible;

    public Libro(int idLibro, String titulo, String autor, int anioPublicacion, int cantidadDisponible) {
        this.idLibro = new SimpleIntegerProperty(idLibro);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.anioPublicacion = new SimpleIntegerProperty(anioPublicacion);
        this.cantidadDisponible = new SimpleIntegerProperty(cantidadDisponible);
    }

    public int getIdLibro() {
        return idLibro.get();
    }

    public IntegerProperty idLibroProperty() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo.get();
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public String getAutor() {
        return autor.get();
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public int getAnioPublicacion() {
        return anioPublicacion.get();
    }

    public IntegerProperty anioPublicacionProperty() {
        return anioPublicacion;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible.get();
    }

    public IntegerProperty cantidadDisponibleProperty() {
        return cantidadDisponible;
    }
}
