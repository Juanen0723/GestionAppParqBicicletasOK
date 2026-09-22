package co.vinni.operaciones;

public class ValidadorParqueadero {

    public void validarIngreso(
            String nombrea,
            String cedula,
            String placa,
            String tipo) {

        validarTexto(nombre, "El nombre del dueño es obligatorio");
        validarTexto(cedula, "La cédula del dueño es obligatoria");
        validarTexto(placa, "El número serial de la bicicleta es obligatorio");
        validarTexto(tipo, "El color de la bicicleta es obligatorio");
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
