package co.vinni.operaciones;
import co.vinni.datos.Bicicleta;
import co.vinni.datos.Parqueadero;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
public class GestionParqueadero {
    public static final long MINUTOS_BASE = 0;
    public static final double VALOR_POR_MINUTO = 1000;
    private final Parqueadero parqueadero;
    private final List<Bicicleta> historial;
    public GestionParqueadero() {
        parqueadero = new Parqueadero();
        historial = new ArrayList<>();
    }
    public Parqueadero obtenerParqueadero() {
        return parqueadero;
    }
    public Bicicleta registrarIngreso(
            String cedula,
            String nombre,
            String placa,
            String tipo) {

        return registrarIngreso(
                cedula,
                nombre,
                placa,
                tipo,
                LocalDateTime.now()
        );
    }

    public Bicicleta registrarIngreso(
            String cedula,
            String nombre,
            String placa,
            String tipo,
            LocalDateTime fechaIngreso) {

        validarTexto(
                cedula,
                "La cédula del dueño es obligatoria."
        );

        validarTexto(
                nombre,
                "El nombre del dueño es obligatorio."
        );

        validarTexto(
                placa,
                "El número serial de la bicicleta es obligatorio."
        );

        validarTexto(
                tipo,
                "El color de la bicicleta es obligatorio."
        );

        if (fechaIngreso == null) {
            throw new IllegalArgumentException(
                    "La fecha de ingreso es obligatoria."
            );
        }

        if (parqueadero.obtenerCantidadOcupadas()
                >= Parqueadero.CAPACIDAD_MAXIMA) {

            throw new IllegalStateException(
                    "El parqueadero está lleno. "
                            + "No hay cupos disponibles."
            );
        }

        if (parqueadero.buscarPorCedula(cedula) != null) {

            throw new IllegalArgumentException(
                    "La cédula ya tiene una bicicleta "
                            + "dentro del parqueadero."
            );
        }

        if (parqueadero.buscarPorPlaca(placa) != null) {

            throw new IllegalArgumentException(
                    "La bicicleta ya está registrada "
                            + "dentro del parqueadero."
            );
        }

        Bicicleta bicicleta =
                new Bicicleta(
                        cedula.trim(),
                        nombre.trim(),
                        placa.trim().toUpperCase(),
                        tipo.trim(),
                        fechaIngreso
                );

        if (!parqueadero.agregarBicicleta(bicicleta)) {

            throw new IllegalStateException(
                    "No fue posible registrar el ingreso."
            );
        }

        return bicicleta;
    }

    public double calcularValor(long minutos) {

        if (minutos <= 0) {
            return 0;
        }

        return minutos * VALOR_POR_MINUTO;
    }

    public double calcularValor(
            Bicicleta bicicleta,
            LocalDateTime salida) {

        if (bicicleta == null || salida == null) {

            throw new IllegalArgumentException(
                    "La bicicleta y la salida son obligatorias."
            );
        }

        long minutos =
                Duration.between(
                        bicicleta.obtenerFechaIngreso(),
                        salida
                ).toMinutes();

        if (minutos < 0) {

            throw new IllegalArgumentException(
                    "La salida no puede ser anterior al ingreso."
            );
        }

        return calcularValor(minutos);
    }

    public double registrarSalidaYCalcularPago(
            String cedula,
            LocalDateTime salida,
            String metodoPago) {

        Bicicleta bicicleta =
                parqueadero.buscarPorCedula(cedula);

        if (bicicleta == null) {

            throw new IllegalArgumentException(
                    "No existe una bicicleta activa "
                            + "para esa cédula."
            );
        }

        validarTexto(
                metodoPago,
                "Debe seleccionar un método de pago."
        );

        double valor =
                calcularValor(
                        bicicleta,
                        salida
                );

        bicicleta.establecerFechaSalida(salida);

        bicicleta.establecerValorPagado(valor);

        bicicleta.establecerMetodoPago(metodoPago);

        bicicleta.establecerPagada(true);

        historial.add(bicicleta);

        parqueadero.liberarBicicleta(bicicleta);

        return valor;
    }

    public List<Bicicleta> obtenerHistorial() {
        return List.copyOf(historial);
    }

    public List<Bicicleta> obtenerReportePorDia(
            LocalDate dia) {

        List<Bicicleta> resultado =
                new ArrayList<>();

        for (Bicicleta bicicleta : historial) {

            if (dia.equals(
                    bicicleta.obtenerFechaIngresoDia())) {

                resultado.add(bicicleta);
            }
        }

        return resultado;
    }

    public int obtenerBicicletasIngresadas(
            LocalDate dia) {

        return obtenerReportePorDia(dia).size();
    }

    public double obtenerValorIngresado(
            LocalDate dia) {

        double total = 0;

        for (Bicicleta bicicleta :
                obtenerReportePorDia(dia)) {

            total += bicicleta.obtenerValorPagado();
        }

        return total;
    }
    private void validarTexto(
            String valor,
            String mensaje) {

        if (valor == null || valor.isBlank()) {

            throw new IllegalArgumentException(
                    mensaje
            );
        }
    }
}