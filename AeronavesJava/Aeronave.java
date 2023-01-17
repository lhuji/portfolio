import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Aeronave {
    String codigo;
    String fechaEntrega;
    String lineaMontaje;
    double horasTrabajoPrevistas;
    double horasTrabajoEmpleadas;
    int capacidadPasajeros;
    double coste;
    double precioVenta;

    public Aeronave(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        this.codigo = codigo;
        this.fechaEntrega = fechaEntrega;
        this.lineaMontaje = lineaMontaje;
        this.horasTrabajoPrevistas = horasTrabajoPrevistas;
        this.capacidadPasajeros = capacidadPasajeros;
        this.coste = coste;
        this.precioVenta = precioVenta;
    }
    public String fechaEntrega() {
        return fechaEntrega;
    }

    public double horasBenef() {
        return (precioVenta - coste) / horasTrabajoPrevistas;
    }

    public void propul() {
        System.out.println("La aeronave utiliza combustible de tipo genérico");
    }

    public void facturacion() {
        System.out.println("La facturación de esta aeronave es: " + precioVenta);
    }

    public void totalHoras() {
        System.out.println("El total de horas empleadas en esta aeronave es: " + horasTrabajoEmpleadas);
    }
    public String formadate() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date parsed = format.parse(this.fechaEntrega);
            return parsed.toString();
        } catch (Exception e) { // Invalid date was entered
            return null;
        }
    }
}

interface Propulsor {
    void propul();
}

class Dron extends Aeronave implements Propulsor {
    public Dron(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        super(codigo, fechaEntrega, lineaMontaje, horasTrabajoPrevistas, capacidadPasajeros, coste, precioVenta);
    }

    public void propul() {
        System.out.println("La aeronave utiliza electricidad");
    }
}
class Avioneta extends Aeronave implements Propulsor {
    public Avioneta(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        super(codigo, fechaEntrega, lineaMontaje, horasTrabajoPrevistas, capacidadPasajeros, coste, precioVenta);
    }

    public void propul() {
        System.out.println("La aeronave utiliza diesel");
    }
}

class Jet extends Aeronave implements Propulsor {
    public Jet(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        super(codigo, fechaEntrega, lineaMontaje, horasTrabajoPrevistas, capacidadPasajeros, coste, precioVenta);
    }

    public void propul() {
        System.out.println("La aeronave utiliza queroxeno");
    }
}

class AvComMed extends Aeronave implements Propulsor {
    public AvComMed(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        super(codigo, fechaEntrega, lineaMontaje, horasTrabajoPrevistas, capacidadPasajeros, coste, precioVenta);
    }

    public void propul() {
        System.out.println("La aeronave utiliza queroxeno");
    }
}

class AvComGran extends Aeronave implements Propulsor {
    public AvComGran(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        super(codigo, fechaEntrega, lineaMontaje, horasTrabajoPrevistas, capacidadPasajeros, coste, precioVenta);
    }

    public void propul() {
        System.out.println("La aeronave utiliza queroxeno");
    }
}

class Cohete extends Aeronave implements Propulsor {
    public Cohete(String codigo, String fechaEntrega, String lineaMontaje, double horasTrabajoPrevistas, int capacidadPasajeros, double coste, double precioVenta) {
        super(codigo, fechaEntrega, lineaMontaje, horasTrabajoPrevistas, capacidadPasajeros, coste, precioVenta);
    }

    public void propul() {
        System.out.println("La aeronave utiliza propulsor sólido");
    }
}