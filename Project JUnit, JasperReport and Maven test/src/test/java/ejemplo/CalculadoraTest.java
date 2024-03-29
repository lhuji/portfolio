package ejemplo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {
    private Calculadora calculadora;

    @BeforeEach
    void creaCalculadora() {
        calculadora = new Calculadora();
    }
    @Test
    @DisplayName("suma 5 y 5")
    void suma() {
        assertEquals(10, calculadora.suma(5, 5)); //Verificación del método
    }
    @Test
    @DisplayName("resta 5 de 5")
    @Disabled ("Le tengo que echar un vistazo")
    void resta() {
        assertEquals(0, calculadora.resta(5, 5)); //Verificación del método
    }
    @Test
    @DisplayName("multiplica 5 por 5")
    void multiplicacion() {
        assertEquals(25, calculadora.multiplicacion(5, 5)); //Verificación del método
    }
    @Test
    @DisplayName("divide 5 entre 5")
    void division() {
        assertEquals(1, calculadora.division(5, 5)); //Verificación del método
    }
    @AfterEach
    void destruyeCalculadora() {
        calculadora = null;
    }
}