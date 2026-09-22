package co.vinni.operaciones;

import co.vinni.datos.Bicicleta;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Responsabilidad única: calcular el valor a pagar por el tiempo de
 * permanencia de una bicicleta.
 */
public class CalculadorTarifa {

    public static final double VALOR_POR_MINUTO = 1000;

    public double calcularValor(long minutos) {
        if (minutos <= 0) {
            return 0;
        }

        return minutos * VALOR_POR_MINUTO;
    }

    public double calcularValor(Bicicleta bicicleta, LocalDateTime salida) {
        if (bicicleta == null || salida == null) {
            throw new IllegalArgumentException(
                    "La bicicleta y la salida son obligatorias."
            );
        }

        long minutos = Duration.between(
                bicicleta.obtenerFechaIngreso(),
                salida
        ).toMinutes();

        if (minutos < 0) {
            throw new IllegalArgumentException(
                    "La salida no puede ser anterior al ingreso"
            );
        }

        return calcularValor(minutos);
    }
}
