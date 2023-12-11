package com.practicag4.ddi_2practica_g4.Model;

import java.util.Date;

public class Cuenta {
    private String numeroCuenta;
    private String titular;
    private Date fechaApertura;
    private double saldo;

    private String nacionalidad;

    public Cuenta(String numeroCuenta, String titular, Date fechaApertura, double saldo, String nacionalidad) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.fechaApertura = fechaApertura;
        this.saldo = saldo;
        this.nacionalidad = nacionalidad;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getNacionalidad(){
        return nacionalidad;
    }
    public void setNacionalidad( String nuevaNacionalidad ){
        this.nacionalidad = nacionalidad;
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
