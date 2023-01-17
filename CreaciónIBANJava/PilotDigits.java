import java.util.InputMismatchException;
import java.util.Scanner;

public class PilotDigits {

    public static void main ( String[] args ) {
        // Crea un objeto Scanner para obtener la entrada del usuario
        Scanner sc = new Scanner ( System.in );
        boolean exit = false;
        String negrita = "\u001b[0;1m";
        String normal = "\u001b[0;0m";
        System.out.println ( negrita + "Bienvenido al programa de generación y validación de dígitos de control y IBAN." + normal );

        // Bucle principal del programa
        while ( ! exit ) {
            // Imprime el menú de opciones
            System.out.println ( negrita + "\n\t\tMenú de Opciones" );
            System.out.println ( "\t\t================" + normal );
            System.out.println ( "\n1) Generar dígitos de control" );
            System.out.println ( "2) Validar dígitos de control" );
            System.out.println ( "3) Generar dígitos del IBAN" );
            System.out.println ( "4) Validar dígitos del IBAN" );
            System.out.println ( "5) Salir" );
            System.out.print ( "\n\t\tOpción: " );
            try {
                // Obtiene la opción del usuario
                int option = sc.nextInt ( );
                sc.nextLine ( ); // consume el "enter"
                // Ejecuta la opción seleccionada
                switch ( option ) {
                    case 1:
                        opciones.opcion1 ( );
                        System.out.println ( "\n" );
                        break;
                    case 2:
                        opciones.opcion2 ( );
                        System.out.println ( "\n" );
                        break;
                    case 3:
                        opciones.opcion3 ( );
                        System.out.println ( "\n" );
                        break;
                    case 4:
                        opciones.opcion4 ( );
                        System.out.println ( "\n" );
                        break;
                    case 5:
                        System.out.println ( "Saliendo..." );
                        exit = true;
                        break;
                    default:
                        System.out.println ( "Opción inválida. Seleccione una opción válida." );
                }
            } catch ( InputMismatchException e ) {
                System.out.println ( "Por favor ingrese un valor numérico válido." );
                sc.nextLine ( ); // consume el valor inválido
            }
        }
        System.out.println ( negrita + "Gracias por utilizar el programa. ¡Hasta la próxima!" + normal );
    }
}