package org.biblioteca.modelo;

import javafx.beans.property.*;

public class Proveedor {
    private final IntegerProperty proveedorId = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty direccion = new SimpleStringProperty();
    private final StringProperty contacto = new SimpleStringProperty();

    public Proveedor(int proveedorId, String nombre, String direccion, String contacto) {
        this.proveedorId.set(proveedorId);
        this.nombre.set(nombre);
        this.direccion.set(direccion);
        this.contacto.set(contacto);
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

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public String getContacto() {
        return contacto.get();
    }

    public StringProperty contactoProperty() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto.set(contacto);
    }
}
