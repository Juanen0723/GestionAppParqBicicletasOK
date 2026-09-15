package co.vinni.gui;
import co.vinni.datos.Bicicleta;
import co.vinni.operaciones.GestionParqueadero;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class VentanaGral extends JFrame {
    private final GestionParqueadero servicio = new GestionParqueadero();
    private JTextField txtCedulaIngreso;
    private JTextField txtNombre;
    private JTextField txtSerial;
    private JTextField txtColor;
    private JTextField txtCedulaSalida;
    private JTextArea txtArea;
    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VentanaGral() {

        setTitle("Gestión Parqueadero de Bicicletas");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initComponentes();
    }
    private void initComponentes() {
        JPanel izquierdo = new JPanel();
        izquierdo.setLayout(new BoxLayout(izquierdo, BoxLayout.Y_AXIS));
        izquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel ingreso = new JPanel(new GridLayout(5, 2, 6, 6));
        ingreso.setBorder(BorderFactory.createTitledBorder("1. Registrar Ingreso"));
        ingreso.add(new JLabel("Nombre del Dueño:"));
        txtNombre = new JTextField();
        ingreso.add(txtNombre);
        ingreso.add(new JLabel("C.C. del Dueño:"));
        txtCedulaIngreso = new JTextField();
        ingreso.add(txtCedulaIngreso);
        ingreso.add(new JLabel("Número Serial de la Bicicleta:"));
        txtSerial = new JTextField();
        ingreso.add(txtSerial);
        ingreso.add(new JLabel("Color:"));
        txtColor = new JTextField();
        ingreso.add(txtColor);
        JButton btnIngreso = new JButton("Registrar Ingreso");
        estiloBoton(btnIngreso);
        ingreso.add(new JLabel());
        ingreso.add(btnIngreso);
        JPanel salida = new JPanel(new GridLayout(2, 2, 6, 6));
        salida.setBorder(BorderFactory.createTitledBorder("2. Registrar salida y cobrar"));
        salida.add(new JLabel("C.C. del Dueño:"));
        txtCedulaSalida = new JTextField();
        salida.add(txtCedulaSalida);
        JButton btnSalida = new JButton("Cobrar Y Liberar");
        estiloBoton(btnSalida);
        salida.add(new JLabel());
        salida.add(btnSalida);
        JPanel consultas = new JPanel(new GridLayout(2, 1, 6, 6));

        consultas.setBorder(
                BorderFactory.createTitledBorder("3. Reportes"));
        JButton btnReporte = new JButton("Reporte de hoy");
        JButton btnEstado = new JButton("Estado del Parqueadero");
        estiloBoton(btnReporte);
        estiloBoton(btnEstado);
        consultas.add(btnReporte);
        consultas.add(btnEstado);
        izquierdo.add(ingreso);
        izquierdo.add(Box.createVerticalStrut(10));
        izquierdo.add(salida);
        izquierdo.add(Box.createVerticalStrut(10));
        izquierdo.add(consultas);
        JPanel derecho = new JPanel(new BorderLayout());
        derecho.setBorder(BorderFactory.createTitledBorder("Información del sistema"));
        txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        derecho.add(new JScrollPane(txtArea), BorderLayout.CENTER);
        add(izquierdo, BorderLayout.WEST);
        add(derecho, BorderLayout.CENTER);
        btnIngreso.addActionListener(e -> registrarIngreso());
        btnSalida.addActionListener(
                e -> registrarSalida());
        btnReporte.addActionListener(e -> mostrarReporte());
        btnEstado.addActionListener(e -> mostrarEstado());
        mostrarEstado();
    }

    private void registrarIngreso() {
        try {

            servicio.registrarIngreso(txtCedulaIngreso.getText(), txtNombre.getText(), txtSerial.getText(), txtColor.getText());
            JOptionPane.showMessageDialog(
                    this,

                    "Ingreso registrado correctamente\n"
                            + "Cupos libres: "
                            + servicio
                            .obtenerParqueadero()
                            .obtenerCuposLibres(),

                    "Ingreso",

                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarIngreso();

            mostrarEstado();

        } catch (
                IllegalArgumentException |
                IllegalStateException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void registrarSalida() {

        try {
            String cedula =
                    txtCedulaSalida
                            .getText()
                            .trim();

            Bicicleta bicicleta =
                    servicio
                            .obtenerParqueadero()
                            .buscarPorCedula(
                                    cedula
                            );

            if (bicicleta == null) {

                throw new IllegalArgumentException(
                        "No existe una bicicleta registrada actualmente"
                                + "con esa cédula"
                );
            }

            LocalDateTime horaSalida =
                    LocalDateTime.now();

            long minutos =
                    bicicleta.obtenerMinutosEstadia();

            double valor =
                    servicio.calcularValor(
                            bicicleta,
                            horaSalida
                    );

            String[] metodosPago = {

                    "EFECTIVO",

                    "NEQUI",

                    "DAVIPLATA",

                    "TARJETA CREDITO",

                    "TARJETA DEBITO"
            };

            String metodoPago =
                    (String) JOptionPane.showInputDialog(

                            this,

                            "Nombre del Dueño: "
                                    + bicicleta
                                    .obtenerNombreDueno()

                                    + "\nC.C. del Dueño: "
                                    + bicicleta
                                    .obtenerCedulaDueno()

                                    + "\nNúmero Serial: "
                                    + bicicleta
                                    .obtenerPlaca()

                                    + "\nColor: "
                                    + bicicleta
                                    .obtenerTipo()

                                    + "\n\nPermanencia: "
                                    + minutos
                                    + " minutos"

                                    + "\nValor a pagar: $"
                                    + String.format(
                                    "%.0f",
                                    valor
                            )

                                    + "\n\nSeleccione el método de pago:",

                            "Salida y cobro",

                            JOptionPane.QUESTION_MESSAGE,

                            null,

                            metodosPago,

                            metodosPago[0]
                    );

            if (metodoPago == null) {

                return;
            }

            double valorFinal =
                    servicio
                            .registrarSalidaYCalcularPago(
                                    cedula,
                                    horaSalida,
                                    metodoPago
                            );

            int cuposDisponibles =
                    servicio
                            .obtenerParqueadero()
                            .obtenerCuposLibres();

            JOptionPane.showMessageDialog(

                    this,

                    "Pago Exitoso\n"

                            + "\nBicicleta retirada correctamente"

            );

            txtCedulaSalida.setText("");

            mostrarEstado();

        } catch (
                IllegalArgumentException |
                IllegalStateException ex
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Salida",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
    private void mostrarReporte() {
        LocalDate hoy =
                LocalDate.now();
        List<Bicicleta> lista =
                servicio.obtenerReportePorDia(hoy);
        StringBuilder sb =
                new StringBuilder();

        sb.append(
                "Reporte del dia de hoy\n"
        );
        sb.append(
                        "Fecha: "
                )
                .append(hoy)
                .append("\n");

        sb.append(
                        "Bicicletas Cobradas/Retiradas: "
                )
                .append(lista.size())
                .append("\n");

        sb.append(
                        "Total Recaudado: $"
                )
                .append(
                        String.format(
                                "%.0f",
                                servicio
                                        .obtenerValorIngresado(hoy)
                        )
                )
                .append("\n");
        if (lista.isEmpty()) {

            sb.append(
                    "\nNo se han registrado bicicletas "
                            + "retiradas durante el día de hoy\n"
            );

        } else {

            int numero = 1;

            for (Bicicleta b : lista) {

                sb.append(
                                "\nRegistro "
                        )
                        .append(numero++)
                        .append("\n");

                sb.append(
                                "Nombre del Dueño: "
                        )
                        .append(
                                b.obtenerNombreDueno()
                        )
                        .append("\n");

                sb.append(
                                "C.C. del Dueño: "
                        )
                        .append(
                                b.obtenerCedulaDueno()
                        )
                        .append("\n");

                sb.append(
                                "Número Serial: "
                        )
                        .append(
                                b.obtenerPlaca()
                        )
                        .append("\n");

                sb.append(
                                "Color: "
                        )
                        .append(
                                b.obtenerTipo()
                        )
                        .append("\n");

                sb.append(
                                "Hora de Ingreso: "
                        )
                        .append(
                                b.obtenerFechaIngreso()
                                        .format(formato)
                        )
                        .append("\n");

                sb.append(
                                "Hora de Salida: "
                        )
                        .append(
                                b.obtenerFechaSalida()
                                        .format(formato)
                        )
                        .append("\n");

                sb.append(
                                "Permanencia: "
                        )
                        .append(
                                b.obtenerMinutosEstadia()
                        )
                        .append(
                                " minutos\n"
                        );

                sb.append(
                                "Valor cobrado: $"
                        )
                        .append(
                                String.format(
                                        "%.0f",
                                        b.obtenerValorPagado()
                                )
                        )
                        .append("\n");

                sb.append(
                                "Método de pago: "
                        )
                        .append(
                                b.obtenerMetodoPago()
                        )
                        .append("\n");

            }
        }

        txtArea.setText(
                sb.toString()
        );
    }
    private void mostrarEstado() {

        int capacidad = 20;

        int ocupados =
                servicio
                        .obtenerParqueadero()
                        .obtenerCantidadOcupadas();

        int libres =
                servicio
                        .obtenerParqueadero()
                        .obtenerCuposLibres();

        StringBuilder sb =
                new StringBuilder();

         sb.append(
                "Parqueadero de bicicletas\n"
        );

        sb.append(
                        "\nCapacidad total : "
                )
                .append(capacidad)
                .append("\n");

        sb.append(
                        "Parqueaderos ocupados : "
                )
                .append(ocupados)
                .append("\n");

        sb.append(
                        "Parqueaderos libres : "
                )
                .append(libres)
                .append("\n\n");

        boolean hayBicicletas = false;
        int numero = 1;
        for (Bicicleta b :
                servicio
                        .obtenerParqueadero()
                        .obtenerBicicletas()) {

            if (b != null) {

                hayBicicletas = true;

                sb.append(
                                "\n\nBicicleta #"
                        )
                        .append(numero++)
                        .append("\n");

                sb.append(
                                "\nNombre del Dueño : "
                        )
                        .append(
                                b.obtenerNombreDueno()
                        )
                        .append("\n");

                sb.append(
                                "C.C. del Dueño : "
                        )
                        .append(
                                b.obtenerCedulaDueno()
                        )
                        .append("\n");

                sb.append(
                                "Número Serial : "
                        )
                        .append(
                                b.obtenerPlaca()
                        )
                        .append("\n");

                sb.append(
                                "Color : "
                        )
                        .append(
                                b.obtenerTipo()
                        )
                        .append("\n");

                sb.append(
                                "Hora de ingreso : "
                        )
                        .append(
                                b.obtenerFechaIngreso()
                                        .format(formato)
                        )
                        .append("\n");
            }
        }

        if (!hayBicicletas) {
            sb.append("\nNo hay bicicletas actualmente en el parqueadero\n");
        }
        txtArea.setText(sb.toString()
        );
    }
    private void limpiarIngreso() {
        txtNombre.setText("");
        txtCedulaIngreso.setText("");
        txtSerial.setText("");
        txtColor.setText("");
    }
    private void estiloBoton(
            JButton boton) {
        boton.setBackground(new Color(51, 111, 158));
        boton.setForeground(Color.WHITE);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
    }
    public static void main(
            String[] args) {
        SwingUtilities.invokeLater(
                () ->
                        new VentanaGral()
                                .setVisible(true)
        );
    }
}