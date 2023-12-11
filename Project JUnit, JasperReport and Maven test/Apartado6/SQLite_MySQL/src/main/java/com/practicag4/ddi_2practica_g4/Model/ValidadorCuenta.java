package com.practicag4.ddi_2practica_g4.Model;

import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidadorCuenta {

    public List<String> validarNuevaCuenta(String numeroCuenta, String titular, String saldoStr, TextField numLabelvacio, TextField titLabelvacio, TextField saldoLabelvacio, List<Cuenta> cuentas, TextField nacLabelvacio, String fecha, TextField fecLabelvacio, String nacionalidad) {

        List<String> errores = new ArrayList<>();

        // Validación del número de cuenta
        if (!numeroCuenta.matches("^\\d+$")) {
            errores.add("Número de cuenta no es válido. Debe ser un número entero.");
            numLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            numLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores

            // Verificar si el número de cuenta ya existe en la lista de cuentas
            if (cuentaExistente(numeroCuenta, cuentas, null)) {
                errores.add("El número de cuenta ya existe.");
                numLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
            }
        }

        // Validación del saldo
        if (!saldoStr.matches("^\\d*\\.?\\d+$")) {
            errores.add("Saldo incorrecto. Debe ser un número válido (entero o decimal).");
            saldoLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            // Convierte saldoStr a un valor numérico para comparar
            double saldo = Double.parseDouble(saldoStr);

            if (saldo > 10000000) {
                errores.add("El saldo no puede ser mayor de 10,000,000.");
                saldoLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
            } else {
                saldoLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores
            }
        }

        // Validación del titular
        if (titular.isEmpty()) {
            errores.add("Titular no puede estar vacío.");
            titLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else if (!titular.matches("^[^\\d]+$")) {
            errores.add("El titular no puede ser un número.");
            titLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            titLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores
        }

        // Validación de la fecha
        if (fecha.isEmpty()) {
            errores.add("La fecha no puede estar vacía.");
            fecLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            try {
                dateFormat.parse(fecha);
                fecLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores
            } catch (ParseException e) {
                errores.add("La fecha debe tener el formato YYYY-MM-DD.");
                fecLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
            }
        }
        return errores;
    }
    public List<String> validarModificarCuenta(String numeroCuenta, String titular, String saldoStr, TextField numLabelvacio, TextField titLabelvacio, TextField saldoLabelvacio, List<Cuenta> cuentas, TextField nacLabelvacio, String fecha, TextField fecLabelvacio, String nacionalidad) {

        List<String> errores = new ArrayList<>();


        // Validación del saldo
        if (!saldoStr.matches("^\\d*\\.?\\d+$")) {
            errores.add("Saldo incorrecto. Debe ser un número válido (entero o decimal).");
            saldoLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            // Convierte saldoStr a un valor numérico para comparar
            double saldo = Double.parseDouble(saldoStr);

            if (saldo > 10000000) {
                errores.add("El saldo no puede ser mayor de 10,000,000.");
                saldoLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
            } else {
                saldoLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores
            }
        }

        // Validación del titular
        if (titular.isEmpty()) {
            errores.add("Titular no puede estar vacío.");
            titLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else if (!titular.matches("^[^\\d]+$")) {
            errores.add("El titular no puede ser un número.");
            titLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            titLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores
        }

        // Validación de la fecha
        if (fecha.isEmpty()) {
            errores.add("La fecha no puede estar vacía.");
            fecLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            try {
                dateFormat.parse(fecha);
                fecLabelvacio.setStyle(""); // Elimina el fondo rojo si no hay errores
            } catch (ParseException e) {
                errores.add("La fecha debe tener el formato YYYY-MM-DD.");
                fecLabelvacio.setStyle("-fx-control-inner-background: #ff6c6c;");
            }
        }
        return errores;
    }
    private boolean cuentaExistente(String numeroCuenta, List<Cuenta> cuentas, String numeroCuentaActual) {
        for (Cuenta cuenta : cuentas) {
            // Excluir la cuenta actual que se está modificando
            if (numeroCuentaActual != null && cuenta.getNumeroCuenta().equals(numeroCuentaActual)) {
                continue; // Saltar la cuenta actual durante la modificación
            }
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return true;
            }
        }
        return false;
    }


}
