package modelo;

public class Vuelo {
    private int idVuelo;
    private int idAvion;

    private String destino;
    private String origen;
    private String fecha;
    private String salida;
    private String llegada;

    public Vuelo(int idVuelo,int idAvion, String origen, String destino, String fecha, String salida, String llegada){
        this.setDestino(destino);
        this.idAvion = idAvion;
        this.setOrigen(origen);
        this.setFecha(fecha);
        this.setSalida(salida);
        this.setLlegada(llegada);
        this.idVuelo = idVuelo;
    }

    public int getIdAvion(){
        return idAvion;
    }
   
    public String toString() {
        return getIdVuelo()+" "+getOrigen() + "-" + getDestino();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSalida() {
        return salida;
    }
      public String getLlegada() {
        return llegada;
    }
      public void setSalida(String salida) {
        this.salida = salida;
    }

    public void setLlegada(String llegada) {
        this.llegada = llegada;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setId(int id) {
        this.idVuelo = id;
    }
    
    
}

