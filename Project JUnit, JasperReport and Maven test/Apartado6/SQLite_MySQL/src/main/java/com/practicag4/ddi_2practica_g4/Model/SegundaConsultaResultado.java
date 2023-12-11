package com.practicag4.ddi_2practica_g4.Model;

import java.math.BigDecimal;

public class SegundaConsultaResultado {
    public Long total_clientes;
    private String categoria;
    private Long cantidad;
    private BigDecimal porcentaje;
    public SegundaConsultaResultado(Long total_clientes, String categoria, Long cantidad, BigDecimal porcentaje) {
        this.total_clientes = total_clientes;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.porcentaje = porcentaje;
    }

    public Long getTotal_clientes() {
        return total_clientes;
    }

    public void setTotal_clientes(Long total_clientes) {
        this.total_clientes = total_clientes;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }


}
