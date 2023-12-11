import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaBaterTest {


    @Test
    public void testConexionSQLite() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecer la conexión con SQLite (cambiar la URL según tu configuración)
            connection = DriverManager.getConnection("jdbc:sqlite:prueba");

            // Verificar que la conexión no sea nula
            assertNotNull(connection);

            // Crear una declaración SQL
            statement = connection.createStatement();

            // Ejecutar una consulta de prueba (cambiar la consulta según tu esquema)
            resultSet = statement.executeQuery("SELECT * FROM alumnos");

            // Verificar que la consulta devuelve resultados
            assertTrue(resultSet.next());

            System.out.println (resultSet.getInt ( "dni" ) );
            System.out.println (resultSet.getString ( "nombre" ) );

            // Puedes realizar más verificaciones según tus necesidades

        } catch (SQLException e) {
            fail("Error al conectar con la base de datos SQLite: " + e.getMessage());
        } finally {
            // Cerrar recursos en el bloque finally para asegurar que se cierren incluso si hay excepciones
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testConexionMySQL() {
        // Configuración para MySQL
        String url = "jdbc:mysql://localhost:3306/visor";
        String usuario = "root";
        String contraseña = "root";

        try {
            // Establecer la conexión con MySQL
            Connection connection = DriverManager.getConnection(url, usuario, contraseña);

            // Verificar que la conexión no sea nula
            assertNotNull(connection);

            // Ejecutar una consulta para obtener las columnas
            String consulta = "SELECT * FROM cuenta";
            ResultSet resultSet = connection.createStatement().executeQuery(consulta);

            // Mover el cursor a la primera fila
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("numeroCuenta"));
                System.out.println(resultSet.getString ("titular"));
                System.out.println(resultSet.getString ("nacionalidad"));
            }

            ResultSetMetaData metaData = resultSet.getMetaData();

            // Verificar las columnas específicas
            int numeroColumnas = metaData.getColumnCount();
            assertEquals(5, numeroColumnas);

            String[] columnasEsperadas = {"numeroCuenta", "titular", "fechaApertura", "saldo", "nacionalidad"};


            for (int i = 1; i <= numeroColumnas; i++) {
                assertEquals(columnasEsperadas[i - 1], metaData.getColumnName(i));
            }

            // Cerrar la conexión
            connection.close();
        } catch (SQLException e) {
            fail("Error al conectar con la base de datos MySQL: " + e.getMessage());
        }
    }
}