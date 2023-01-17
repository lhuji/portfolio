class DigiControl {

// Declarando un arreglo constante de enteros para usarlo como multiplicadores en el cálculo de los dígitos de control
    private static final int[] CONSTANTES = { 1 , 2 , 4 , 8 , 5 , 10 , 9 , 7 , 3 , 6 };
// Método estático para generar el primer dígito de control
    static int generarDigitosControlPrimero ( String cuentaSinDC ) {

// acumulado para almacenar el resultado parcial del cálculo
        int acumulado = 0;

// variable para almacenar el dígito de control
        int dc = 0;

// variable para almacenar el resultado de la operación módulo
        int resto;

// Bucle para iterar sobre cada caracter de la cuenta sin dígitos de control
        for ( int i = 0 ; i < cuentaSinDC.length ( ) ; i++ ) {

// Sumando el resultado de multiplicar cada caracter de la cuenta por el multiplicador correspondiente
            acumulado += Character.getNumericValue ( cuentaSinDC.charAt ( i ) ) * CONSTANTES[ i % 10 ];

// Si se ha iterado sobre los primeros 9 caracteres de la cuenta
            if ( i == 9 ) {

// Calcular el módulo con respecto a 11
                resto = acumulado % 11;

// Asignar a dc el resultado de 11 menos el módulo
                dc = 11 - resto;

// Si el resultado es 10 o 11, asignar el valor correspondiente
                if ( dc == 10 ) {
                    dc = 1;
                } else if ( dc == 11 ) {
                    dc = 0;
                }
            }
        }

// Retornar el dígito de control calculado
        return dc;
    }

    // Método estático para validar un dígito de control
    static boolean validarDigitosControl ( String digitosControl , String digitosControlaValidar ) {

// Retorna si los dos dígitos de control son iguales
        return digitosControl.equals ( digitosControlaValidar );
    }
}
