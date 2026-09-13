package co.vinni.datos;

import java.util.Arrays;

public class Parqueadero {
    public static final int CAPACIDAD_MAXIMA = 20;

    private final Bicicleta[] bicicletas;

    public Parqueadero() {
        bicicletas = new Bicicleta[CAPACIDAD_MAXIMA];
    }

    public int obtenerCantidadOcupadas() {
        int cantidad = 0;
        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta != null) cantidad++;
        }
        return cantidad;
    }

    public int obtenerCuposLibres() {
        return CAPACIDAD_MAXIMA - obtenerCantidadOcupadas();
    }

    public boolean agregarBicicleta(Bicicleta bicicleta) {
        if (bicicleta == null || obtenerCantidadOcupadas() >= CAPACIDAD_MAXIMA) return false;
        for (int i = 0; i < bicicletas.length; i++) {
            if (bicicletas[i] == null) {
                bicicletas[i] = bicicleta;
                return true;
            }
        }
        return false;
    }

    public Bicicleta buscarPorCedula(String cedula) {
        if (cedula == null) return null;
        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta != null && cedula.equals(bicicleta.obtenerCedulaDueno())) {
                return bicicleta;
            }
        }
        return null;
    }

    public Bicicleta buscarPorPlaca(String placa) {
        if (placa == null) return null;
        for (Bicicleta bicicleta : bicicletas) {
            if (bicicleta != null && placa.equalsIgnoreCase(bicicleta.obtenerPlaca())) {
                return bicicleta;
            }
        }
        return null;
    }

    public boolean liberarBicicleta(Bicicleta bicicleta) {
        for (int i = 0; i < bicicletas.length; i++) {
            if (bicicletas[i] == bicicleta) {
                bicicletas[i] = null;
                return true;
            }
        }
        return false;
    }

    public Bicicleta[] obtenerBicicletas() {
        return Arrays.copyOf(bicicletas, bicicletas.length);
    }
}
