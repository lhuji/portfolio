package modelo;

import javafx.scene.image.Image;

public class Pasajero {
    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private String edad;
    private String telefono;
    private String email;
    private String dni;
    private int idPasajero;
    private int idVuelo;
    private Image fotoPasaj;
    
    public Pasajero(int idPasajero, String dni, String nombre, String apellido1, String apellido2,
                    String edad, String telefono, String email, String direccion, int idVuelo, Image fotoPasaj) {
        this.idPasajero = idPasajero;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = direccion;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.dni = dni;
        this.idVuelo = idVuelo;
        this.fotoPasaj = fotoPasaj;
    }

    public Image getFoto() {
        return fotoPasaj;
    }
    
    public void setFoto(Image fotoPasaj) {
        this.fotoPasaj = fotoPasaj;
    }

    public int getIdPasajero() {
        return idPasajero;
    }
    
    public void setIdPasajero(int idPasajero) {
        this.idPasajero = idPasajero;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    @Override
    public String toString() {
        return telefono + " " + nombre + " " + apellido1 + " " + apellido2;
    }
}
