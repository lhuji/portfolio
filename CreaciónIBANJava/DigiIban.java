// importa la clase BigInteger de la librería java.math para trabajar con números grandes
import java.math.BigInteger;

// clase que contiene métodos para generar y validar los dígitos IBAN
class DigiIban {

    // método que genera el IBAN a partir de los datos de la cuenta (banco, sucursal, dígitos de control y número de cuenta)
    static String generarIBAN ( String banco , String sucursal , String digitosControl , String cuenta ) {

// concatena los datos de la cuenta para formar la cuenta completa
        String cuentacompleta = banco + sucursal + digitosControl + cuenta;

// concatena la cuenta completa con el número 142800
        String cuentacompletado = cuentacompleta + "142800";

// crea un objeto BigInteger con el valor de la cuenta completado
        BigInteger numerocuenta = new BigInteger(cuentacompletado);

// crea un objeto BigInteger con el valor 97
        BigInteger modulo = new BigInteger("97");

// calcula el resto de la división entre el número de cuenta completado y 97
        BigInteger resto = numerocuenta.mod(modulo);

// calcula los dígitos IBAN restando 98 al resto de la división anterior
        int digitosiban = 98 - resto.intValue();

// si los dígitos IBAN son menores a 10, los concatena con ES0
        if(digitosiban < 10) {
            return "ES0" + digitosiban + " " + cuentacompleta;
        }

// si los dígitos IBAN son 10 o más, los concatena con ES
        return "ES" + digitosiban;
    }

    // método que valida si el IBAN ingresado es igual al IBAN correcto
    static boolean validarIban ( String ibanCorrecto , String ibanAValidar ) {

// Compara si el ibanCorrecto es igual al ibanAValidar
        return ibanCorrecto.equals(ibanAValidar);
    }
}