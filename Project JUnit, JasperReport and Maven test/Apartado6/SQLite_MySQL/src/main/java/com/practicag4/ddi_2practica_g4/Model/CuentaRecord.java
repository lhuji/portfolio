package com.practicag4.ddi_2practica_g4.Model;

import java.time.LocalDate;

public record CuentaRecord(String numeroCuenta, String titular, LocalDate fechaApertura, double saldo) {
}