package co.vinni.datos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
public class Bicicleta {
    private String cedulaDueno;
    private String nombreDueno;
    private String placa;
    private String tipo;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private double valorPagado;
    private boolean pagada;
    private String metodoPago;

    public Bicicleta() {
    }

    public Bicicleta(String cedulaDueno, String nombreDueno,
                     String placa, String tipo,
                     LocalDateTime fechaIngreso) {

        this.cedulaDueno = cedulaDueno;
        this.nombreDueno = nombreDueno;
        this.placa = placa;
        this.tipo = tipo;
        this.fechaIngreso = fechaIngreso;
        this.valorPagado = 0;
        this.pagada = false;
        this.metodoPago = "";
    }

    public String obtenerCedulaDueno() {
        return cedulaDueno;
    }

    public void establecerCedulaDueno(String cedulaDueno) {
        this.cedulaDueno = cedulaDueno;
    }

    public String obtenerNombreDueno() {
        return nombreDueno;
    }

    public void establecerNombreDueno(String nombreDueno) {
        this.nombreDueno = nombreDueno;
    }

    public String obtenerPlaca() {
        return placa;
    }

    public void establecerPlaca(String placa) {
        this.placa = placa;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public void establecerTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime obtenerFechaIngreso() {
        return fechaIngreso;
    }

    public void establecerFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime obtenerFechaSalida() {
        return fechaSalida;
    }

    public void establecerFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double obtenerValorPagado() {
        return valorPagado;
    }

    public void establecerValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public boolean estaPagada() {
        return pagada;
    }

    public void establecerPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public String obtenerMetodoPago() {
        return metodoPago;
    }

    public void establecerMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public long obtenerMinutosEstadia() {

        LocalDateTime fin =
                fechaSalida == null
                        ? LocalDateTime.now()
                        : fechaSalida;

        return Math.max(
                0,
                Duration.between(
                        fechaIngreso,
                        fin
                ).toMinutes()
        );
    }

    public LocalDate obtenerFechaIngresoDia() {
        return fechaIngreso.toLocalDate();
    }

    @Override
    public String toString() {

        return placa
                + " - "
                + nombreDueno
                + " - C.C. "
                + cedulaDueno;
    }
}