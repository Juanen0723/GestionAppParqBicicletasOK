package co.vinni.operaciones;

/**
 * Responsabilidad única: validar los datos de entrada del parqueadero.
 */
public class ValidadorParqueadero {

    public void validarIngreso(
            String cedula,
            String nombre,
            String placa,
            String tipo) {

        validarTexto(cedula, "La cédula del dueño es obligatoria");
        validarTexto(nombre, "El nombre del dueño es obligatorio.");
        validarTexto(placa, "El número serial de la bicicleta es obligatorio.");
        validarTexto(tipo, "El color de la bicicleta es obligatorio.");
    }

    public void validarFechaIngreso(java.time.LocalDateTime fechaIngreso) {
        if (fechaIngreso == null) {
            throw new IllegalArgumentException(
                    "La fecha de ingreso es obligatoria"
            );
        }
    }

    public void validarMetodoPago(String metodoPago) {
        validarTexto(metodoPago, "Debe seleccionar un método de pago");
    }

    public void validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
