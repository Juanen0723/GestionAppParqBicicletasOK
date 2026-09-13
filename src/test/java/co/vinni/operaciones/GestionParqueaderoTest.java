package co.vinni.operaciones;
import co.vinni.datos.Bicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
public class GestionParqueaderoTest {
    private GestionParqueadero servicio;

    @BeforeEach
    public void configurar() {
        servicio = new GestionParqueadero();
    }

    @Test
    public void registrarIngresoCorrectamente() {
        Bicicleta b =
                servicio.registrarIngreso(
                        "123",
                        "Carlos",
                        "B001",
                        "Montaña",
                        LocalDateTime.of(
                                2026,
                                9,
                                8,
                                8,
                                0
                        )
                );

        assertEquals("123", b.obtenerCedulaDueno());
        assertEquals("Carlos", b.obtenerNombreDueno());
        assertEquals("B001", b.obtenerPlaca());
        assertEquals("Montaña", b.obtenerTipo());
        assertEquals(
                1,
                servicio
                        .obtenerParqueadero()
                        .obtenerCantidadOcupadas()
        );

        assertEquals(
                19,
                servicio
                        .obtenerParqueadero()
                        .obtenerCuposLibres()
        );
    }

    @Test
    public void cobrarHastaDiezMinutos() {
        long minutos = 10;
        assertEquals(
                10000,
                servicio.calcularValor(minutos)
        );
    }

    @Test
    public void cobrarDespuesDeDiezMinutos() {
        long minutos = 21;
        assertEquals(
                21000,
                servicio.calcularValor(minutos)
        );
    }

    @Test
    public void salidaPagaYLiberaCupo() {
        LocalDateTime ingreso =
                LocalDateTime.of(
                        2026,
                        9,
                        8,
                        8,
                        0
                );

        LocalDateTime salida =
                LocalDateTime.of(
                        2026,
                        9,
                        8,
                        8,
                        25
                );

        servicio.registrarIngreso(
                "999",
                "Ana",
                "B002",
                "Ruta",
                ingreso
        );

        double valor =
                servicio.registrarSalidaYCalcularPago(
                        "999",
                        salida,
                        "EFECTIVO"
                );

        assertEquals(
                25000,
                valor
        );

        assertEquals(
                0,
                servicio
                        .obtenerParqueadero()
                        .obtenerCantidadOcupadas()
        );

        assertEquals(
                20,
                servicio
                        .obtenerParqueadero()
                        .obtenerCuposLibres()
        );

        assertEquals(
                1,
                servicio.obtenerBicicletasIngresadas(
                        LocalDate.of(
                                2026,
                                9,
                                8
                        )
                )
        );
    }
}