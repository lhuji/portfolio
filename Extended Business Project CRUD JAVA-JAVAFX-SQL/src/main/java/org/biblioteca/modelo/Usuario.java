package org.biblioteca.modelo;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasenaEncriptada;

    public Usuario ( int idUsuario , String nombreUsuario , String contrasenaEncriptada ) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaEncriptada = contrasenaEncriptada;
    }

    public int getIdUsuario ( ) {
        return idUsuario;
    }

    public void setIdUsuario ( int idUsuario ) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario ( ) {
        return nombreUsuario;
    }

    public void setNombreUsuario ( String nombreUsuario ) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenaEncriptada ( ) {
        return contrasenaEncriptada;
    }

    public void setContrasenaEncriptada ( String contrasenaEncriptada ) {
        this.contrasenaEncriptada = contrasenaEncriptada;
    }
}