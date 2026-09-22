package co.vinni.gui;

import co.vinni.datos.Bicicleta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class GeneradorReporte {

    private final DateTimeFormatter formato;

    public GeneradorReporte(DateTimeFormatter formato) {
        this.formato = formato;
    }

    public String generarReporteDia(
            LocalDate dia,
            List<Bicicleta> bicicletas,
            double totalRecaudado) {

        StringBuilder sb = new StringBuilder();

        sb.append("Reporte del dia de hoy\n");
        sb.append("Fecha: ").append(dia).append("\n");
        sb.append("Bicicletas Cobradas/Retiradas: ")
                .append(bicicletas.size())
                .append("\n");
        sb.append("Total Recaudado: $")
                .append(String.format("%.0f", totalRecaudado))
                .append("\n");

        if (bicicletas.isEmpty()) {
            sb.append("\nNo se han registrado bicicletas ")
                    .append("retiradas durante el día de hoy\n");
            return sb.toString();
        }

        int numero = 1;
        for (Bicicleta bicicleta : bicicletas) {
            sb.append("\nRegistro ").append(numero++).append("\n");
            sb.append("Nombre del Dueño: ")
                    .append(bicicleta.obtenerNombreDueno())
                    .append("\n");
            sb.append("C.C. del Dueño: ")
                    .append(bicicleta.obtenerCedulaDueno())
                    .append("\n");
            sb.append("Número Serial: ")
                    .append(bicicleta.obtenerPlaca())
                    .append("\n");
            sb.append("Color: ")
                    .append(bicicleta.obtenerTipo())
                    .append("\n");
            sb.append("Hora de Ingreso: ")
                    .append(bicicleta.obtenerFechaIngreso().format(formato))
                    .append("\n");
            sb.append("Hora de Salida: ")
                    .append(bicicleta.obtenerFechaSalida().format(formato))
                    .append("\n");
            sb.append("Permanencia: ")
                    .append(bicicleta.obtenerMinutosEstadia())
                    .append(" minutos\n");
            sb.append("Valor cobrado: $")
                    .append(String.format("%.0f", bicicleta.obtenerValorPagado()))
                    .append("\n");
            sb.append("Método de pago: ")
                    .append(bicicleta.obtenerMetodoPago())
                    .append("\n");
        }

        return sb.toString();
    }

    public String generarEstado(
            int capacidad,
            int ocupados,
            int libres,
            Bicicleta[] bicicletas) {

        StringBuilder sb = new StringBuilder();

        sb.append("Parqueadero de bicicletas\n");
        sb.append("\nCapacidad total : ")
                .append(capacidad)
                .append("\n");
        sb.append("Parqueaderos ocupados : ")
                .append(ocupados)
                .append("\n");
        sb.append("Parqueaderos libres : ")
                .append(libres)
                .append("\n\n");

        boolean hayBicicletas = false;
        int numero = 1;

        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta == null) {
                continue;
            }

            hayBicicletas = true;

            sb.append("\n\nBicicleta #")
                    .append(numero++)
                    .append("\n");
            sb.append("\nNombre del Dueño : ")
                    .append(bicicleta.obtenerNombreDueno())
                    .append("\n");
            sb.append("C.C. del Dueño : ")
                    .append(bicicleta.obtenerCedulaDueno())
                    .append("\n");
            sb.append("Número Serial : ")
                    .append(bicicleta.obtenerPlaca())
                    .append("\n");
            sb.append("Color : ")
                    .append(bicicleta.obtenerTipo())
                    .append("\n");
            sb.append("Hora de ingreso : ")
                    .append(bicicleta.obtenerFechaIngreso().format(formato))
                    .append("\n");
        }

        if (!hayBicicletas) {
            sb.append("\nNo hay bicicletas actualmente en el parqueadero\n");
        }

        return sb.toString();
    }
}
