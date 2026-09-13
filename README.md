# GestionParqueaderoBicicletas

Aplicación Java Swing basada en la estructura de GestionAppBase:
- `datos`: clases encapsuladas.
- `operaciones`: reglas de negocio.
- `gui`: interfaz gráfica.
- `test`: 4 pruebas JUnit 5.

## Reglas usadas
- Capacidad: 20 bicicletas.
- Hasta 10 minutos: $1.000.
- Después de 10 minutos: se agregan $500 por cada bloque adicional de 10 minutos o fracción.
- La salida se identifica por la cédula del dueño.
- Al confirmar el pago, la bicicleta pasa al historial y el cupo se libera.
- El reporte diario muestra cantidad de bicicletas retiradas e ingresos.
- Se incluye un JComboBox de país, igual que el concepto de `Pais.values()` de GestionAppBase.

## Ejecutar
En IntelliJ IDEA: abrir el proyecto como Maven y ejecutar `VentanaGral`.
Para pruebas: `mvn test`.

## Desplegable de país
La parte clave es:
`cbPais = new JComboBox<>(Pais.values());`

`Pais` es un `enum`, por lo que `Pais.values()` devuelve todos los países definidos. Swing los coloca automáticamente como opciones del `JComboBox`. Luego:
`(Pais) cbPais.getSelectedItem()`
obtiene el país que escogió el usuario.
