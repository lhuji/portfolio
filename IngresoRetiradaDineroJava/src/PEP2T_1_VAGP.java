import java.util.Scanner;

public class PEP2T_1_VAGP {
    // Atributos de la clase
    private float saldoActual;
    private float topeRetirada;

    String negrita = "\u001b[0;1m";
    String normal = "\u001b[0;0m";

    public static void main ( String[] args ) {
        PEP2T_1_VAGP Obj1 = new PEP2T_1_VAGP ( );

        // Leemos el saldo actual y el tope de retirada de los argumentos
        Obj1.saldoActual = Float.parseFloat ( args[ 0 ] );
        Obj1.topeRetirada = Float.parseFloat ( args[ 1 ] );
        // Mostramos el menú de opciones al usuario
        Obj1.mostrarMenu ( );
    }

    private void mostrarMenu ( ) {
        int opcion = 0;

        // Mostramos el menú y leemos la opción seleccionada por el usuario
        // mientras la opción sea distinta de 3 (Salir)
        Scanner scanner = new Scanner ( System.in );
        while ( opcion != 3 ) {
            System.out.println ( negrita + "\n\t\tMenú de Opciones" );
            System.out.println ( "\t\t================" + normal );
            System.out.println ( "\n1) Retirada de dinero" );
            System.out.println ( "2) Ingreso de dinero" );
            System.out.println ( "3) Salir" );
            System.out.print ( "\n\t\tOpción: " );
            opcion = scanner.nextInt ( );

            // Ejecutamos la opción seleccionada
            switch ( opcion ) {
                case 1:
                    retirarDinero ( );
                    break;
                case 2:
                    ingresarDinero ( );
                    break;
                case 3:
                    break;
                default:
                    System.out.println ( "\tOpción inválida." );
                    break;
            }
        }
    }

    // Método para realizar ingresos de dinero
    private void ingresarDinero ( ) {
        verificarSaldoActual ( );
        System.out.print ( "\tTeclee dinero a ingresar: " );
        Scanner scanner = new Scanner ( System.in );
        float cantidad = scanner.nextFloat ( );

        // Validamos que la cantidad ingresada sea positiva
        while ( cantidad < 0 ) {
            System.out.println ( "\tLa cantidad debe ser positiva. Por favor, teclee una cantidad válida: " );
            cantidad = scanner.nextFloat ( );
        }

        // Añadimos la cantidad al saldo actual
        saldoActual += cantidad;
        System.out.println ( "\tUsted ingresó " + negrita + cantidad + normal + "€" );
        verificarSaldoActual ( );
    }


    private void retirarDinero ( ) {
        System.out.println ( "\n\tSu saldo actual es de " + negrita + saldoActual + normal );
        System.out.print ( "\tTeclee dinero a retirar: \n\t" );
        Scanner scanner = new Scanner ( System.in );
        float cantidad = scanner.nextFloat ( );

        // Validamos que la cantidad ingresada sea positiva
        while ( cantidad < 0 ) {
            System.out.println ( "\tLa cantidad debe ser positiva. Por favor, teclee una cantidad válida: " );
            cantidad = scanner.nextFloat ( );
        }

        if ( cantidad > topeRetirada ) {
            System.out.print ( "\tIntenta retirar " + negrita + cantidad + normal );
            verificarTopeRetirada ( );
            verificarSaldoActual ( );
            return;
        }

        // Comprobamos si la cantidad supera el saldo actual
        if ( cantidad > saldoActual ) {
            System.out.println ( "\tNo es posible retirar " + negrita + cantidad + normal + "€ ya que supera su saldo actual" );
            verificarSaldoActual ( );

        }
        if ( cantidad <= topeRetirada ) {
            // Retiramos la cantidad del saldo actual
            saldoActual -= cantidad;
            topeRetirada -= cantidad;
            System.out.println ( "\tUsted retiró " + negrita + cantidad + normal + "€" );
            verificarSaldoActual ( );
        }
    }

    private void verificarSaldoActual ( ) {
        System.out.println ( "\tSu saldo actual es de " + negrita + saldoActual + normal );
    }

    private void verificarTopeRetirada ( ) {
        System.out.println ( "\n\tTiene establecido ahora un tope de " + negrita + topeRetirada + normal + "€" );
    }
}