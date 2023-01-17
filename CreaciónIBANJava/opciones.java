import java.util.Scanner;

class opciones {
    private static final Scanner scanner = new Scanner ( System.in );

    // método que obtiene una entrada del usuario con el mensaje especificado
    private static String getInput ( String message ) {
        System.out.print ( message );
        return scanner.nextLine ( );
    }

    public static void opcion1 ( ) {
        String banco = getInput ( "Introduce los 4 digitos del banco: " );
        String sucursal = getInput ( "Introduce los 4 digitos de la sucursal: " );
        String cuenta = getInput ( "Introduce los 10 digitos de la cuenta: " );
        String cuentaSinDC = "00" + banco + sucursal;
        int digitosControl1 = DigiControl.generarDigitosControlPrimero ( cuentaSinDC );
        int digitosControl2 = DigiControl.generarDigitosControlPrimero ( cuenta );
        String digitosControl = String.valueOf ( digitosControl1 ) + digitosControl2;
        System.out.println ( banco + " " + sucursal + " " + digitosControl + " " + cuenta );
    }

    public static void opcion2 ( ) {
        String banco = getInput ( "Introduce los 4 digitos del banco: " );
        String sucursal = getInput ( "Introduce los 4 digitos de la sucursal: " );
        String digitosControlAValidar = getInput ( "Introduce los 2 digitos de control: " );
        String cuenta = getInput ( "Introduce los 10 digitos de la cuenta: " );
        String cuentaSinDC = "00" + banco + sucursal;
        int digitosControl1 = DigiControl.generarDigitosControlPrimero ( cuentaSinDC );
        int digitosControl2 = DigiControl.generarDigitosControlPrimero ( cuenta );
        String digitosControl = String.valueOf ( digitosControl1 ) + digitosControl2;
        System.out.print ( "Número de cuenta a validar: " + banco + " " + sucursal + " " + digitosControlAValidar + " " + cuenta );
        boolean esValido = DigiControl.validarDigitosControl ( digitosControl , digitosControlAValidar );
        System.out.println ( "\nDígitos calculados: " + digitosControl );
        System.out.print ( esValido ? "Verificacion: Correcta" : "Verificacion: Incorrecta" );
    }

    public static void opcion3 ( ) {
        String banco = getInput ( "Introduce los 4 digitos del banco: " );
        String sucursal = getInput ( "Introduce los 4 digitos de la sucursal: " );
        String digitosControl = getInput ( "Introduce los 2 digitos de control: " );
        String cuenta = getInput ( "Introduce los 10 digitos de la cuenta: " );
        String iban = DigiIban.generarIBAN ( banco , sucursal , digitosControl , cuenta );
        System.out.println ( iban + " " + banco + " " + sucursal + " " + digitosControl + " " + cuenta );
    }

    public static void opcion4 ( ) {
        String ibanAValidar = getInput ( "Introduce los 4 primeros dígitos del IBAN: " );
        String banco = getInput ( "Introduce los 4 digitos del banco: " );
        String sucursal = getInput ( "Introduce los 4 digitos de la sucursal: " );
        String digitosControl = getInput ( "Introduce los 2 digitos de control: " );
        String cuenta = getInput ( "Introduce los 10 digitos de la cuenta: " );
        String ibancorrecto = DigiIban.generarIBAN ( banco , sucursal , digitosControl , cuenta );
        boolean esValido = DigiIban.validarIban ( ibancorrecto , ibanAValidar );
        System.out.println ( "\nNúmero de cuenta a validar: " + ibanAValidar + " " + banco + " " + sucursal + " " + digitosControl + " " + cuenta );
        System.out.print ( "IBAN calculado: " + ibancorrecto );
        if ( esValido ) {
            System.out.print ( "\tVerificacion: Correcta" );
        } else {
            System.out.print ( "\tVerificacion: Incorrecta" );
        }
    }
}