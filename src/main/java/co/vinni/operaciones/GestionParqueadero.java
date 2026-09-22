package co.vinni.operaciones;

import co.vinni.datos.Bicicleta;
import co.vinni.datos.Parqueadero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestionParqueadero {

    public static final long minutosBase = 0;
    public static final double valorPorMinuto = CalculadorTarifa.VALOR_POR_MINUTO;

    private final Parqueadero parqueadero;
    private final List<Bicicleta> historial;
    private final ValidadorParqueadero validador;
    private final CalculadorTarifa calculadorTarifa;

    public GestionParqueadero() {
        parqueadero = new Parqueadero();
        historial = new ArrayList<>();
        validador = new ValidadorParqueadero();
        calculadorTarifa = new CalculadorTarifa();
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

        validador.validarIngreso(cedula, nombre, placa, tipo);
        validador.validarFechaIngreso(fechaIngreso);

        if (parqueadero.obtenerCantidadOcupadas()
                >= Parqueadero.CAPACIDAD_MAXIMA) {

            throw new IllegalStateException(
                    "El parqueadero está lleno "
                            + "No hay cupos disponibles"
            );
        }

        if (parqueadero.buscarPorCedula(cedula) != null) {
            throw new IllegalArgumentException(
                    "La cédula ya tiene una bicicleta "
                            + "dentro del parqueadero"
            );
        }

        if (parqueadero.buscarPorPlaca(placa) != null) {
            throw new IllegalArgumentException(
                    "La bicicleta ya está registrada "
                            + "dentro del parqueadero"
            );
        }

        Bicicleta bicicleta = new Bicicleta(
                cedula.trim(),
                nombre.trim(),
                placa.trim().toUpperCase(),
                tipo.trim(),
                fechaIngreso
        );

        if (!parqueadero.agregarBicicleta(bicicleta)) {
            throw new IllegalStateException(
                    "No fue posible registrar el ingreso de la bicicleta"
            );
        }

        return bicicleta;
    }

    public double calcularValor(long minutos) {
        return calculadorTarifa.calcularValor(minutos);
    }

    public double calcularValor(
            Bicicleta bicicleta,
            LocalDateTime salida) {

        return calculadorTarifa.calcularValor(bicicleta, salida);
    }

    public Bicicleta obtenerBicicletaPorCedula(String cedula) {
        return parqueadero.buscarPorCedula(cedula);
    }

    public double registrarSalidaYCalcularPago(
            String cedula,
            LocalDateTime salida,
            String metodoPago) {

        Bicicleta bicicleta = obtenerBicicletaPorCedula(cedula);

        if (bicicleta == null) {
            throw new IllegalArgumentException(
                    "No existe una bicicleta registrada actualmente "
                            + "para esa cédula"
            );
        }

        validador.validarMetodoPago(metodoPago);

        double valor = calcularValor(bicicleta, salida);

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

    public List<Bicicleta> obtenerReportePorDia(LocalDate dia) {
        List<Bicicleta> resultado = new ArrayList<>();

        for (Bicicleta bicicleta : historial) {
            if (dia.equals(bicicleta.obtenerFechaIngresoDia())) {
                resultado.add(bicicleta);
            }
        }

        return resultado;
    }

    public int obtenerBicicletasIngresadas(LocalDate dia) {
        return obtenerReportePorDia(dia).size();
    }

    public double obtenerValorIngresado(LocalDate dia) {
        double total = 0;

        for (Bicicleta bicicleta : obtenerReportePorDia(dia)) {
            total += bicicleta.obtenerValorPagado();
        }

        return total;
    }
}
