
package modelo;

/**
 *
 * @author DAM
 */
public class Tripulante2 {
   
    private String nombre;
    private String apellido1;
    private String apellido2;

    private int idTripu;
   
    public Tripulante2 ( int idTripu, String nombre , String apellido1 , String apellido2 ) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.idTripu = idTripu;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido1
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * @param apellido1 the apellido1 to set
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * @return the apellido2
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * @param apellido2 the apellido2 to set
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

   
    /**
     * @return the idTripu
     */
    public int getIdTripu() {
        return idTripu;
    }

    /**
     * @param idTripu the idTripu to set
     */
    public void setIdTripu(int idTripu) {
        this.idTripu = idTripu;
    }
   
    @Override
    public String toString ( ) {
        return getNombre()+" "+getApellido1()+" "+getApellido2();
    }
}