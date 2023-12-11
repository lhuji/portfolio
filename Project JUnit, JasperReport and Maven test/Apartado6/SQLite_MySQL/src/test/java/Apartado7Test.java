import com.practicag4.ddi_2practica_g4.Model.Apartado7;
import com.practicag4.ddi_2practica_g4.Model.Cuenta;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Apartado7Test {

    @Test
    public void testMayores50000() {
        Apartado7 apartado7 = new Apartado7();
        Apartado7.ResultadoConsulta resultado = apartado7.mayores50000();

        // Verificar que la lista de cuentas no sea nula y contenga al menos una cuenta
        assertNotNull(resultado.getListaCuentas());
        assertFalse(resultado.getListaCuentas().isEmpty());
        for (Cuenta cuenta : resultado.getListaCuentas()) {
            System.out.println (cuenta.getTitular () + " " + cuenta.getSaldo () + "€ " + cuenta.getNacionalidad () );
        }

        // Verificar que el total de cuentas sea mayor que cero
        assertTrue(resultado.getTotalCuentas() > 0);
        System.out.println (resultado.getTotalCuentas () );
    }
}
