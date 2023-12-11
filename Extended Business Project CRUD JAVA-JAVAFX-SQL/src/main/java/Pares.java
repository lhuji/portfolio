import java.util.concurrent.atomic.AtomicInteger;

public class Pares {

    public static void main(String[] args) {
        AtomicInteger suma = new AtomicInteger ( );
        Runnable numerosParesEjecutable = () -> {
            for (int i = 2; i <= 10; i += 2) {
                suma.addAndGet ( i );
                System.out.println("Hilo de Números Pares: " + i);
                System.out.println("Hilo de suma pares: " + suma);
            }
        };
        //Estos son los hilos
        Thread numerosPareshilo = new Thread (numerosParesEjecutable);
        //Aqui asignamos el primero hilo para que comience
        numerosPareshilo.start ();
        // Los dos hilos anteriores irán siempre en el mismo orden, uno detrás de otro por como se han llamado
        // sin embargo, este siguiente for que contiene los impares, entrará en ejecución según le llegue,
        // por lo que podrá salir en primera o en última posición
        int sumaImpar= 0;
        for (int i = 1; i <= 10; i += 2) {
            sumaImpar += i;
            System.out.println("Hilo Principal - Números Impares: " + i + "\nSuma de los impares=" + sumaImpar);
        }
    }
}