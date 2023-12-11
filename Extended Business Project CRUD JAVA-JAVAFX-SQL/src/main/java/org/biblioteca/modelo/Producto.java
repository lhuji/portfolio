package org.biblioteca.modelo;

import javafx.beans.property.*;

public class Producto {
    private final IntegerProperty productoId = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty descripcion = new SimpleStringProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final IntegerProperty cantidadEnStock = new SimpleIntegerProperty();

    public Producto(int productoId, String nombre, String descripcion, double precio, int cantidadEnStock) {
        this.productoId.set(productoId);
        this.nombre.set(nombre);
        this.descripcion.set(descripcion);
        this.precio.set(precio);
        this.cantidadEnStock.set(cantidadEnStock);
    }

    public int getProductoId() {
        return productoId.get();
    }

    public IntegerProperty productoIdProperty() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId.set(productoId);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public double getPrecio() {
        return precio.get();
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public int getCantidadEnStock() {
        return cantidadEnStock.get();
    }

    public IntegerProperty cantidadEnStockProperty() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock.set(cantidadEnStock);
    }
}
