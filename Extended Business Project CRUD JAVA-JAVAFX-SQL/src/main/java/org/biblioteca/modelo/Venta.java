package org.biblioteca.modelo;

import javafx.beans.property.*;

import java.util.Date;

public class Venta {
    private final IntegerProperty ventaId = new SimpleIntegerProperty();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();
    private final ObjectProperty<Date> fecha = new SimpleObjectProperty<>();
    private final IntegerProperty clienteId = new SimpleIntegerProperty();
    private final IntegerProperty productoId = new SimpleIntegerProperty();
    private final IntegerProperty proveedorId = new SimpleIntegerProperty();

    public Venta(int ventaId, int cantidad, Date fecha, int clienteId, int productoId, int proveedorId) {
        this.ventaId.set(ventaId);
        this.cantidad.set(cantidad);
        this.fecha.set(fecha);
        this.clienteId.set(clienteId);
        this.productoId.set(productoId);
        this.proveedorId.set (proveedorId);
    }

    public int getVentaId() {
        return ventaId.get();
    }
    public IntegerProperty ventaIdProperty() {
        return ventaId;
    }
    public void setVentaId(int ventaId) {
        this.ventaId.set(ventaId);
    }
    public int getCantidad() {
        return cantidad.get();
    }
    public IntegerProperty cantidadProperty() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
    }
    public Date getFecha() {
        return fecha.get();
    }
    public ObjectProperty<Date> fechaProperty() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha.set(fecha);
    }
    public int getClienteId() {
        return clienteId.get();
    }
    public IntegerProperty clienteIdProperty() {
        return clienteId;
    }
    public void setClienteId(int clienteId) {
        this.clienteId.set(clienteId);
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
    public int getProveedorId() {
        return proveedorId.get();
    }
    public IntegerProperty proveedorIdProperty() {
        return proveedorId;
    }
    public void setProveedorId(int proveedorId) {
        this.proveedorId.set(proveedorId);
    }
    public void setPrecioProducto ( double precioProducto ) {
    }
    public void setPrecioSinIVA ( double precioSinIva ) {
    }
}
