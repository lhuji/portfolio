public class ProgramaAeronaves {
    public static void main ( String[] args ) {
        Aeronave[] tablaAeronaves = new Aeronave[ 6 ];
        tablaAeronaves[ 0 ] = new Dron ( "DR-001" , "02/02/2008" , "L2GA" , 205 , 0 , 2700 , 3000 );
        tablaAeronaves[ 1 ] = new Avioneta ( "AV-001" , "10/03/2008" , "L3GA" , 2500 , 8 , 120000 , 125000 );
        tablaAeronaves[ 2 ] = new Jet ( "JT-001" , "22/05/2008" , "L1GA" , 10550 , 6 , 600000 , 750000 );
        tablaAeronaves[ 3 ] = new AvComMed ( "ACM-001" , "28/10/2008" , "L2GA" , 22785 , 120 , 2000000 , 2500000 );
        tablaAeronaves[ 4 ] = new AvComGran ( "ACG-001" , "22/05/2008" , "L1GA" , 10550 , 350 , 6000000 , 75000000 );
        tablaAeronaves[ 5 ] = new Cohete ( "CH-001" , "14/09/2022" , "L4GA" , 22785 , 0 , 2000000 , 2500000 );
        double facturacionTotal = 0;
        double totalHorasEmpleadas = 0;
        double totalHorasEmpleadasAvioneta = 0;
        for ( Aeronave aeronave : tablaAeronaves ) {
            aeronave.facturacion ( );
            aeronave.propul ( );
            aeronave.totalHoras ( );
            facturacionTotal += aeronave.precioVenta;
            totalHorasEmpleadas += aeronave.horasTrabajoEmpleadas;
            if ( aeronave instanceof Avioneta ) {
                totalHorasEmpleadasAvioneta += aeronave.horasTrabajoEmpleadas;
            }
        }
        System.out.println ( "La facturación total de la compañía a día de hoy: " + facturacionTotal + " €" );
        System.out.println ( "El total de horas de trabajo empleadas en todas las aeronaves: " + totalHorasEmpleadas + " horas" );
        System.out.println ( "El total de horas de trabajo empleadas en el tipo Avioneta fueron: " + totalHorasEmpleadasAvioneta + " horas" );
    }
}