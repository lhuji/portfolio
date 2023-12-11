/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author DAM
 */
public class Avion {

    private int idAvion;
    private int numAsientos;
    private String matricula;
    private int estado;
    private String modelo;

    public Avion(int idAvion, int numAsientos, String matricula, String modelo) {
        this.idAvion = idAvion;
        this.numAsientos = numAsientos;
        this.matricula = matricula;
        this.modelo = modelo;
    }
    
    /**
     * @return the idAvion
     */
    public int getIdAvion() {
        return idAvion;
    }

    /**
     * @param idAvion the idAvion to set
     */
    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    /**
     * @return the numAsientos
     */
    public int getNumAsientos() {
        return numAsientos;
    }

    /**
     * @param numAsientos the numAsientos to set
     */
    public void setNumAsientos(int numAsientos) {
        this.numAsientos = numAsientos;
    }

    /**
     * @return the matricula
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * @param matricula the matricula to set
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return idAvion+": "+matricula;
    }
    
    
}
