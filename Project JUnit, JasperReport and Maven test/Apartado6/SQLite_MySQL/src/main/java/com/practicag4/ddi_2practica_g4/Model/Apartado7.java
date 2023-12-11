package com.practicag4.ddi_2practica_g4.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Apartado7 {
    public ResultadoConsulta mayores50000() {
        List<Cuenta> listaCuentas = new ArrayList<>();
        int totalCuentas = 0;

        // Configuración para MySQL
        String url = "jdbc:mysql://localhost:3306/visor";
        String usuario = "root";
        String contraseña = "root";

        try {
            // Establecer la conexión con MySQL
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta para obtener cuentas con saldo mayor a 50000
            String consulta = "SELECT * FROM cuenta WHERE saldo > 50000";
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Recorrer el resultado y agregar cuentas a la lista
            while (resultSet.next()) {
                String numeroCuenta = resultSet.getString("numeroCuenta");
                String titular = resultSet.getString("titular");
                Date fechaApertura = resultSet.getDate("fechaApertura");
                double saldo = resultSet.getDouble("saldo");
                String nacionalidad = resultSet.getString("nacionalidad");

                // Crear un objeto Cuenta y agregarlo a la lista
                Cuenta cuenta = new Cuenta(numeroCuenta, titular, fechaApertura, saldo, nacionalidad);
                listaCuentas.add(cuenta);
            }

            // Consulta para obtener el número total de cuentas
            String consultaTotal = "SELECT COUNT(*) AS total FROM cuenta";
            ResultSet resultSetTotal = connection.createStatement().executeQuery(consultaTotal);
            if (resultSetTotal.next()) {
                totalCuentas = resultSetTotal.getInt("total");
            }

            // Cerrar la conexión
            connection.close();
        } catch (SQLException e) {
            // Manejar excepciones (puedes imprimir un mensaje o lanzar una excepción personalizada)
            e.printStackTrace();
        }

        return new ResultadoConsulta(listaCuentas, totalCuentas);
    }

    public static class ResultadoConsulta {
        private final List<Cuenta> listaCuentas;
        private final int totalCuentas;

        public ResultadoConsulta(List<Cuenta> listaCuentas, int totalCuentas) {
            this.listaCuentas = listaCuentas;
            this.totalCuentas = totalCuentas;
        }

        public List<Cuenta> getListaCuentas() {
            return listaCuentas;
        }

        public int getTotalCuentas() {
            return totalCuentas;
        }
    }
}
