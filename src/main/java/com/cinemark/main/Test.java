package com.cinemark.main;

import com.cinemark.conexion.Conexion;
import com.cinemark.conexion.consulta.*;
import com.cinemark.modulo.ModuloAdministrador;
import com.cinemark.modulo.ModuloBoletero;
import com.cinemark.modulo.ModuloCliente;
import com.cinemark.modulo.ModuloGerente;
import de.vandermeer.asciitable.AsciiTable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase para iniciar el sistema del cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public class Test {

    /**
     * Método principal del sistema.
     *
     * @param args argumentos recibidos como comandos (no se utilizan para este programa).
     */
    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/cinemark_bd?user=root&password=12345678";
        try (
                Conexion conexion = new Conexion(url);
                Scanner scanner = new Scanner(System.in)
        ) {
            boolean continuar = true;
            boolean logeado = false;
            String modulo = null;
            while (continuar) {
                while (!logeado && continuar) {
                    System.out.println("Iniciar Sesión");
                    System.out.print("Ingrese su nombre de usuario »");
                    String nombre = scanner.next();
                    System.out.print("Ingrese la clave »");
                    String clave = scanner.next();
                    ProcedimientoLogearUsuario logearUsuario = new ProcedimientoLogearUsuario(nombre, clave);
                    modulo = conexion.ejecutar(logearUsuario);
                    try {
                        Class.forName(modulo);
                        logeado = true;
                    } catch (NullPointerException | ClassNotFoundException e) {
                        System.out.printf("El módulo %s no existe.\n", modulo);
                    }
                    if (!logeado) {
                        System.out.println("¿Desea continuar? 0 para salir | o ingrese cualquier valor para omitir.");
                        String valor = scanner.next();
                        continuar = !valor.equals("0");
                    } else {
                        System.out.println("Bienvenido!!!");
                    }
                }
                // con acceso concedido
                try {
                    if (modulo != null) {
                        Class<?> clase = Class.forName(modulo);
                        Object instancia = clase.getDeclaredConstructor(Conexion.class).newInstance(conexion);
                        if (instancia instanceof ModuloAdministrador) {
                            continuar = menuAdmin((ModuloAdministrador) instancia, scanner);
                        } else if (instancia instanceof ModuloGerente) {
                            continuar = menuGerente((ModuloGerente) instancia, scanner);
                        } else if (instancia instanceof ModuloBoletero) {
                            continuar = menuBoletero((ModuloBoletero) instancia, scanner);
                        } else {
                            continuar = menuCliente((ModuloCliente) instancia, scanner);
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                         InvocationTargetException | NoSuchMethodException | SecurityException |
                         ClassNotFoundException e) {
                    System.out.printf("El sistema falló inesperadamente. error ocurrido: %s\n", e.getMessage());
                    continuar = false;
                }
            }
            System.out.println("Sistema finalizado.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * se explica solo.
     *
     * @param modulo  módulo del usuario.
     * @param scanner entrada del teclado.
     * @return continuar con el programa o no.
     */
    private static boolean menuAdmin(@NotNull ModuloAdministrador modulo, @NotNull Scanner scanner) {
        System.out.printf("**** %s ****\n", modulo);
        System.out.println("1  » para crear un nuevo cliente");
        System.out.println("2  » para crear un nuevo usuario");
        System.out.println("3  » para crear una nueva tarjeta");
        System.out.println("4  » para agregar una nueva película");
        System.out.println("5  » para crear una nueva sala");
        System.out.println("6  » para crear una nueva función");
        System.out.println("7  » para crear una reserva");
        System.out.println("8  » para vender ticket/s");
        System.out.println("9  » para actualizar los datos de un cliente");
        System.out.println("10 » para actualizar los datos de un usuario");
        System.out.println("11 » para actualizar los datos de una tarjeta");
        System.out.println("12 » para actualizar los datos de una película");
        System.out.println("13 » para actualizar los datos de una sala");
        System.out.println("14 » para actualizar una función");
        System.out.println("15 » para actualizar una reserva");
        System.out.println("16 » para actualizar un ticket");
        System.out.println("17 » para borrar un cliente");
        System.out.println("18 » para borrar un usuario");
        System.out.println("19 » para borrar una tarjeta");
        System.out.println("20 » para borrar una película");
        System.out.println("21 » para borrar una sala");
        System.out.println("22 » para borrar una función");
        System.out.println("23 » para borrar una reserva");
        System.out.println("24 » para borrar un ticket");
        System.out.println("25 » listar películas");
        System.out.println("26 » listar salas");
        System.out.println("27 » listar funciones");
        System.out.println("0  » para salir del programa");
        System.out.print("Ingrese una opción »");
        String valor = scanner.next();
        switch (valor) {
            case "1" -> crearCliente(modulo, scanner);
            case "2" -> nuevoUsuario(modulo, scanner);
            case "3" -> crearTarjeta(modulo, scanner);
            case "4" -> {
                listarPeliculas(modulo.getConexion());
                nuevaPelicula(modulo, scanner);
            }
            case "5" -> {
                listarSalas(modulo.getConexion());
                nuevaSala(modulo, scanner);
            }
            case "6" -> {
                listarFunciones(modulo.getConexion());
                nuevaFuncion(modulo, scanner);
            }
            case "7" -> {
                listarFunciones(modulo.getConexion());
                nuevaReserva(modulo, scanner);
            }
            case "8" -> {
                listarFuncionesEnVenta(modulo.getConexion());
                venderTicket(modulo, scanner);
            }
            case "9" -> actualizarCliente(modulo, scanner);
            case "10" -> actualizarUsuario(modulo, scanner);
            case "11" -> actualizarTarjeta(modulo, scanner);
            case "12" -> actualizarPelicula(modulo, scanner);
            case "13" -> actualizarSala(modulo, scanner);
            case "14" -> actualizarFuncion(modulo, scanner);
            case "15" -> actualizarReserva(modulo, scanner);
            case "16" -> actualizarTicket(modulo, scanner);
            case "17" -> borrarCliente(modulo, scanner);
            case "18" -> borrarUsuario(modulo, scanner);
            case "19" -> borrarTarjeta(modulo, scanner);
            case "20" -> borrarPelicula(modulo, scanner);
            case "21" -> borrarSala(modulo, scanner);
            case "22" -> borrarFuncion(modulo, scanner);
            case "23" -> borrarReserva(modulo, scanner);
            case "24" -> borrarTicket(modulo, scanner);
            case "25" -> {
                System.out.println("PELÍCULAS");
                listarPeliculas(modulo.getConexion());
            }
            case "26" -> {
                System.out.println("SALAS");
                listarSalas(modulo.getConexion());
            }
            case "27" -> {
                System.out.println("FUNCIONES");
                listarFunciones(modulo.getConexion());
            }
            default -> System.out.printf("No existe la opción %s", valor);
        }
        return !valor.equals("0");
    }

    /**
     * se explica solo.
     *
     * @param modulo  módulo del usuario.
     * @param scanner entrada del teclado.
     * @return continuar con el programa o no.
     */
    private static boolean menuGerente(@NotNull ModuloGerente modulo, @NotNull Scanner scanner) {
        System.out.printf("**** %s ****\n", modulo);
        System.out.println("1  » para crear un nuevo cliente");
        System.out.println("2  » para crear una nueva tarjeta");
        System.out.println("3  » para agregar una nueva película");
        System.out.println("4  » para crear una nueva función");
        System.out.println("5  » para vender ticket/s");
        System.out.println("6  » para actualizar los datos de un cliente");
        System.out.println("7 » para actualizar los datos de una tarjeta");
        System.out.println("8 » para actualizar los datos de una película");
        System.out.println("9 » para actualizar una función");
        System.out.println("10 » para actualizar un ticket");
        System.out.println("11 » para borrar un cliente");
        System.out.println("12 » para borrar una tarjeta");
        System.out.println("13 » para borrar un ticket");
        System.out.println("14 » listar películas");
        System.out.println("15 » listar salas");
        System.out.println("16 » listar funciones");
        System.out.println("0 » para salir del programa");
        System.out.print("Ingrese una opción »");
        String valor = scanner.next();
        switch (valor) {
            case "1" -> crearCliente(modulo, scanner);
            case "2" -> crearTarjeta(modulo, scanner);
            case "3" -> {
                listarPeliculas(modulo.getConexion());
                nuevaPelicula(modulo, scanner);
            }
            case "4" -> {
                listarFunciones(modulo.getConexion());
                nuevaFuncion(modulo, scanner);
            }
            case "5" -> {
                listarFuncionesEnVenta(modulo.getConexion());
                venderTicket(modulo, scanner);
            }
            case "6" -> actualizarCliente(modulo, scanner);
            case "7" -> actualizarTarjeta(modulo, scanner);
            case "8" -> actualizarPelicula(modulo, scanner);
            case "9" -> actualizarFuncion(modulo, scanner);
            case "10" -> actualizarTicket(modulo, scanner);
            case "11" -> borrarCliente(modulo, scanner);
            case "12" -> borrarTarjeta(modulo, scanner);
            case "13" -> borrarTicket(modulo, scanner);
            case "14" -> {
                System.out.println("PELÍCULAS");
                listarPeliculas(modulo.getConexion());
            }
            case "15" -> {
                System.out.println("SALAS");
                listarSalas(modulo.getConexion());
            }
            case "16" -> {
                System.out.println("FUNCIONES");
                listarFunciones(modulo.getConexion());
            }
            default -> System.out.printf("No existe la opción %s", valor);
        }
        return !valor.equals("0");
    }

    /**
     * se explica solo.
     *
     * @param modulo  módulo del usuario.
     * @param scanner entrada del teclado.
     * @return continuar con el programa o no.
     */
    private static boolean menuBoletero(@NotNull ModuloBoletero modulo, @NotNull Scanner scanner) {
        System.out.printf("**** %s ****\n", modulo);
        System.out.println("1 » para crear un nuevo cliente");
        System.out.println("2 » para crear una nueva tarjeta");
        System.out.println("3 » para vender ticket/s");
        System.out.println("4 » para actualizar los datos de un cliente");
        System.out.println("5 » listar películas");
        System.out.println("6 » listar salas");
        System.out.println("7 » listar funciones en venta");
        System.out.println("0 » para salir del programa");
        System.out.print("Ingrese una opción »");
        String valor = scanner.next();
        switch (valor) {
            case "1" -> crearCliente(modulo, scanner);
            case "2" -> crearTarjeta(modulo, scanner);
            case "3" -> {
                listarFuncionesEnVenta(modulo.getConexion());
                venderTicket(modulo, scanner);
            }
            case "4" -> actualizarCliente(modulo, scanner);
            case "5" -> {
                System.out.println("PELÍCULAS");
                listarPeliculas(modulo.getConexion());
            }
            case "6" -> {
                System.out.println("SALAS");
                listarSalas(modulo.getConexion());
            }
            case "7" -> {
                System.out.println("FUNCIONES EN VENTA");
                listarFuncionesEnVenta(modulo.getConexion());
            }
            default -> System.out.printf("No existe la opción %s", valor);
        }
        return !valor.equals("0");
    }

    /**
     * se explica solo.
     *
     * @param modulo  módulo del usuario.
     * @param scanner entrada del teclado.
     * @return continuar con el programa o no.
     */
    private static boolean menuCliente(@NotNull ModuloCliente modulo, @NotNull Scanner scanner) {
        System.out.printf("**** %s ****\n", modulo);
        System.out.println("1 » para crear un nuevo usuario");
        System.out.println("2 » para crear una reserva");
        System.out.println("3 » para actualizar los datos de un usuario");
        System.out.println("4 » para actualizar una reserva");
        System.out.println("5 » para borrar una reserva");
        System.out.println("6 » listar películas");
        System.out.println("7 » listar funciones en venta");
        System.out.println("0 » para salir del programa");
        System.out.print("Ingrese una opción »");
        String valor = scanner.next();
        switch (valor) {
            case "1" -> nuevoUsuario(modulo, scanner);
            case "2" -> nuevaReserva(modulo, scanner);
            case "3" -> actualizarUsuario(modulo, scanner);
            case "4" -> actualizarReserva(modulo, scanner);
            case "5" -> borrarReserva(modulo, scanner);
            case "6" -> {
                System.out.println("PELÍCULAS");
                listarPeliculas(modulo.getConexion());
            }
            case "7" -> {
                System.out.println("FUNCIONES EN VENTA");
                listarFuncionesEnVenta(modulo.getConexion());
            }
            default -> System.out.printf("No existe la opción %s", valor);
        }
        return !valor.equals("0");
    }

    /**
     * Genera una fecha aleatoria para simplificar el ingreso de datos.
     *
     * @return fecha de nacimiento.
     */
    protected static Date fechaNacRandom() {
        long anios = 31536000000L * (new Random()).nextLong(18, 101);
        return new Date(System.currentTimeMillis()-anios);
    }

    /**
     * vencimiento en 2 años a partir de hoy.
     *
     * @return fecha de vencimiento de la tarjeta.
     */
    protected static Timestamp fechaVenc() {
        return new Timestamp(System.currentTimeMillis() + 63072000000L); // hoy + 2 años de vida
    }

    /**
     * crea un código para el boleto o entrada.
     *
     * @param tres tres últimas cifras del dni.
     * @return el código para el ticket.
     */
    protected static String generarCodigoTicket(String tres) {
        return (new Date(System.currentTimeMillis())).toString().replaceAll("-", "").concat(tres).substring(0, 11);
    }

    /**
     * imprime la tabla en pantalla.
     *
     * @param columnas títulos para las columnas.
     * @param datos los datos para la tabla.
     */
    public static void mostrarTabla(@NotNull final List<String> columnas, @NotNull final List<List<Object>> datos) {
        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow(columnas);
        for (List<Object> fila : datos) {
            asciiTable.addRule();
            asciiTable.addRow(fila);
        }
        asciiTable.addRule();
        System.out.println(asciiTable.render());
    }

    /**
     * se explica solo.
     *
     * @param conexion conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    protected static void listarPeliculas(@NotNull Conexion conexion) {
        ProcedimientoListarPeliculas listarPeliculas = new ProcedimientoListarPeliculas(0, 1000);
        List<List<Object>> datos = conexion.ejecutar(listarPeliculas);
        List<String> columnas = listarPeliculas.columnas();
        mostrarTabla(columnas, datos);
    }

    /**
     * se explica solo.
     *
     * @param conexion conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    protected static void listarSalas(@NotNull Conexion conexion) {
        ProcedimientoListarSalas listarSalas = new ProcedimientoListarSalas(0, 1000);
        List<List<Object>> datos = conexion.ejecutar(listarSalas);
        List<String> columnas = listarSalas.columnas();
        mostrarTabla(columnas, datos);
    }

    /**
     * se explica solo.
     *
     * @param conexion conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    protected static void listarFunciones(@NotNull Conexion conexion) {
        ProcedimientoListarFunciones listarFunciones = new ProcedimientoListarFunciones(0, 1000);
        List<List<Object>> datos = conexion.ejecutar(listarFunciones);
        List<String> columnas = listarFunciones.columnas();
        mostrarTabla(columnas, datos);
    }

    /**
     * se explica solo.
     *
     * @param conexion conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    protected static void listarFuncionesEnVenta(@NotNull Conexion conexion) {
        ProcedimientoListarFuncionesEnVenta listarFuncionesEnVenta = new ProcedimientoListarFuncionesEnVenta(0, 1000);
        List<List<Object>> datos = conexion.ejecutar(listarFuncionesEnVenta);
        List<String> columnas = listarFuncionesEnVenta.columnas();
        mostrarTabla(columnas, datos);
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void nuevaReserva(ModuloCliente modulo, Scanner scanner) {
        System.out.println("___ NUEVA RESERVA ___");
        System.out.print("Ingrese el IMDB de la película: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String sala = scanner.next();
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        try {
            System.out.print("Ingrese la cantidad de butacas a reservar: ");
            int butacas = scanner.nextInt();
            System.out.println(modulo.nuevaReserva(new ProcedimientoAgregarReserva(imdb, sala, dni, butacas)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un numero válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarReserva(ModuloCliente modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR RESERVA ___");
        System.out.print("Ingrese el IMDB: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        try {
            System.out.print("Ingrese la nueva cantidad de butacas para reservar: ");
            int butacas = scanner.nextInt();
            System.out.println(modulo.actualizarReserva(new ProcedimientoActualizarReserva(imdb, nombre, dni, butacas)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un número válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarReserva(ModuloCliente modulo, Scanner scanner) {
        System.out.println("___ BORRAR RESERVA ___");
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        System.out.println(modulo.borrarReserva(new ProcedimientoBorrarReserva(dni)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void nuevoUsuario(ModuloCliente modulo, Scanner scanner) {
        System.out.println("___ CREAR NUEVO USUARIO ___");
        System.out.print("Ingrese su dni: ");
        String dni = scanner.next();
        System.out.print("Ingrese su nombre de usuario: ");
        String nombre = scanner.next();
        System.out.print("Ingrese su clave: ");
        String clave = scanner.next();
        System.out.print("Ingrese el tipo de usuario: ");
        String tipo = scanner.next();
        switch (tipo) {
            case "cli" -> {
                System.out.println(modulo.nuevoUsuario(new ProcedimientoAgregarUsuario(dni, nombre, clave, ModuloCliente.class.getName())));
                modulo.getConexion().aplicar();
            }
            case "bol" -> {
                System.out.println(modulo.nuevoUsuario(new ProcedimientoAgregarUsuario(dni, nombre, clave, ModuloBoletero.class.getName())));
                modulo.getConexion().aplicar();
            }
            case "ger" -> {
                System.out.println(modulo.nuevoUsuario(new ProcedimientoAgregarUsuario(dni, nombre, clave, ModuloGerente.class.getName())));
                modulo.getConexion().aplicar();
            }
            case "adm" -> {
                System.out.println(modulo.nuevoUsuario(new ProcedimientoAgregarUsuario(dni, nombre, clave, ModuloAdministrador.class.getName())));
                modulo.getConexion().aplicar();
            }
            default -> System.out.printf("%s no es un usuario válido", tipo);
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarUsuario(ModuloCliente modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR USUARIO ___");
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        System.out.print("Ingrese el nuevo nombre: ");
        String nombre = scanner.next();
        System.out.print("Ingrese la nueva clave: ");
        String clave = scanner.next();
        System.out.print("Ingrese el tipo de usuario: ");
        String tipo = scanner.next();
        switch (tipo) {
            case "adm" -> {
                String mod = ModuloAdministrador.class.getName();
                System.out.println(modulo.actualizarUsuario(new ProcedimientoActualizarUsuario(dni, nombre, clave, mod)));
                modulo.getConexion().aplicar();
            }
            case "ger" -> {
                String mod = ModuloGerente.class.getName();
                System.out.println(modulo.actualizarUsuario(new ProcedimientoActualizarUsuario(dni, nombre, clave, mod)));
                modulo.getConexion().aplicar();
            }
            case "bol" -> {
                String mod = ModuloBoletero.class.getName();
                System.out.println(modulo.actualizarUsuario(new ProcedimientoActualizarUsuario(dni, nombre, clave, mod)));
                modulo.getConexion().aplicar();
            }
            case "cli" -> {
                String mod = ModuloCliente.class.getName();
                System.out.println(modulo.actualizarUsuario(new ProcedimientoActualizarUsuario(dni, nombre, clave, mod)));
                modulo.getConexion().aplicar();
            }
            default -> System.out.println("usuario no encontrado.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarUsuario(ModuloAdministrador modulo, Scanner scanner) {
        System.out.println("___ BORRAR USUARIO ___");
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        System.out.println(modulo.borrarUsuario(new ProcedimientoBorrarUsuario(dni)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void venderTicket(ModuloBoletero modulo, Scanner scanner) {
        System.out.println("__ VENDER ENTRADA ___");
        System.out.print("Ingrese el IMDB de la película: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String sala = scanner.next();
        System.out.print("Ingrese la butaca: ");
        String butaca = scanner.next();
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        try {
            System.out.print("Ingrese el precio: ");
            float precio = scanner.nextFloat();
            int tam = dni.length();
            String tres = dni.substring(tam-3);
            System.out.println(modulo.venderTicket(new ProcedimientoAgregarTicket(generarCodigoTicket(tres), imdb, sala, butaca, dni, precio)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un numero válido.\noperación cancelada.");
        } catch (IndexOutOfBoundsException boundsException) {
            System.out.println("dni corto.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarTicket(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR TICKET ___");
        System.out.print("Ingrese el código del ticket: ");
        String codigo = scanner.next();
        System.out.print("Ingrese el IMDB: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String nombre = scanner.next();
        System.out.print("Ingrese la nueva butaca: ");
        String butaca = scanner.next();
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        try {
            System.out.print("Ingrese el nuevo precio: ");
            float precio = scanner.nextFloat();
            System.out.println(modulo.actualizarTicket(new ProcedimientoActualizarTicket(codigo, imdb, nombre, butaca, dni, precio)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un número válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarTicket(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ BORRAR TICKET ___");
        System.out.print("Ingrese el código del ticket: ");
        String codigo = scanner.next();
        System.out.println(modulo.borrarTicket(new ProcedimientoBorrarTicket(codigo)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void crearTarjeta(ModuloBoletero modulo, Scanner scanner) {
        System.out.println("___ CREAR UNA TARJETA ___");
        System.out.print("Ingrese el dni del usuario: ");
        String dni = scanner.next();
        System.out.println("verificando los requisitos....");
        int meses = modulo.getConexion().ejecutar(new ProcedimientoVerificarConsumo(dni));
        System.out.printf("El cliente %s tiene %d meses de consumo en este año.", dni, meses);
        if (meses >= 6) {
            System.out.println(modulo.nuevaTarjeta(new ProcedimientoAgregarTarjeta(new Timestamp(System.currentTimeMillis()), fechaVenc(), dni)));
            modulo.getConexion().aplicar();
        } else {
            System.out.println("El cliente no cumple los requisitos!!!");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarTarjeta(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR TARJETA ___");
        System.out.print("Ingrese el dni del usuario: ");
        String dni = scanner.next();
        Timestamp hoy = new Timestamp(System.currentTimeMillis());
        Timestamp venc = fechaVenc();
        try {
            System.out.print("¿Tarjeta habilitada? (true o false): ");
            boolean hab = scanner.nextBoolean();
            System.out.println(modulo.actualizarTarjeta(new ProcedimientoActualizarTarjeta(dni, hoy, venc, hab)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarTarjeta(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ BORRAR TARJETA ___");
        System.out.print("Ingrese el dni del usuario: ");
        String dni = scanner.next();
        System.out.println(modulo.borrarTarjeta(new ProcedimientoBorrarTarjeta(dni)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void crearCliente(ModuloBoletero modulo, Scanner scanner) {
        System.out.println("___ CREAR NUEVO CLIENTE ___");
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();
        Date fechaNacimiento = fechaNacRandom();
        Date hoy = new Date(System.currentTimeMillis());
        System.out.println(modulo.nuevoCliente(new ProcedimientoAgregarCliente(dni, fechaNacimiento, hoy)));
        modulo.getConexion().aplicar();
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarCliente(ModuloBoletero modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR CLIENTE ___");
        System.out.print("Ingrese el dni del cliente: ");
        String dniOrig = scanner.next();
        System.out.print("Ingrese el nuevo dni del cliente (o el mismo): ");
        String dni = scanner.next();
        Date nacimiento = fechaNacRandom();
        Date alta = new Date(System.currentTimeMillis());
        try {
            System.out.print("¿El usuario está activo? (true o false): ");
            boolean activo = scanner.nextBoolean();
            System.out.println(modulo.actualizarCliente(new ProcedimientoActualizarCliente(dniOrig, dni, nacimiento, alta, activo)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste el valor solicitado\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarCliente(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ BORRAR CLIENTE ___");
        System.out.print("Ingrese el dni del cliente: ");
        String dni = scanner.next();

        // elimino las relaciones por clave extranjera o foránea
        System.out.printf("Se borrará el usuario (si tuviera), reservas y todos los tickets vendidos al cliente %s\n", dni);
        System.out.println(modulo.getConexion().ejecutar(new ProcedimientoPurgarCliente(dni)) + " filas modificadas");
        // fin de las relaciones

        System.out.println("Ahora prosigue con el cliente en sí.");
        System.out.println(modulo.borrarCliente(new ProcedimientoBorrarCliente(dni)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void nuevaPelicula(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ NUEVA PELÍCULA ___");
        System.out.print("Ingrese el IMDB: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el título de la obra: ");
        String titulo = scanner.next();
        System.out.print("Ingrese el o los directores: ");
        String director = scanner.next();
        System.out.print("Ingrese a los actores estelares: ");
        String reparto = scanner.next();
        System.out.print("Ingrese la duración (en minutos): ");
        try {
            int duracion = scanner.nextInt();
            System.out.println(modulo.nuevaPelicula(new ProcedimientoAgregarPelicula(imdb, titulo, director, reparto, new Date(System.currentTimeMillis()), duracion, true)));
            listarPeliculas(modulo.getConexion());
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un numero válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarPelicula(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR PELÍCULA ___");
        System.out.print("Ingrese el IMDB: ");
        String imdbAnt = scanner.next();
        System.out.print("Ingrese el nuevo IMDB (o el mismo): ");
        String imdb = scanner.next();
        System.out.print("Ingrese el título de la obra: ");
        String titulo = scanner.next();
        System.out.print("Ingrese el o los directores: ");
        String director = scanner.next();
        System.out.print("Ingrese a los actores estelares: ");
        String reparto = scanner.next();
        try {
            System.out.print("Ingrese la duración (en minutos): ");
            int duracion = scanner.nextInt();
            Date estreno = new Date(System.currentTimeMillis());
            System.out.print("La película sigue en cartelera o no (true o false): ");
            boolean cartelera = scanner.nextBoolean();
            System.out.println(modulo.actualizarPelicula(new ProcedimientoActualizarPelicula(imdbAnt, imdb, titulo, director, reparto, estreno, duracion, cartelera)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarPelicula(ModuloAdministrador modulo, Scanner scanner) {
        System.out.println("___ BORRAR PELÍCULA ___");
        System.out.print("Ingrese el IMDB: ");
        String imdb = scanner.next();
        System.out.println("Se eliminarán todas las funciones y reservas ligadas.");
        System.out.println(modulo.getConexion().ejecutar(new ProcedimientoPurgarPelicula(imdb)) + " filas modificadas");
        System.out.println("Se procederá a eliminar la película.");
        System.out.println(modulo.borrarPelicula(new ProcedimientoBorrarPelicula(imdb)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void nuevaFuncion(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ NUEVA FUNCIÓN ___");
        System.out.print("Ingrese el IMDB de la película: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String sala = scanner.next();
        Timestamp inicio = new Timestamp(System.currentTimeMillis() + 1800000L); // hoy + 30 minutos
        try {
            System.out.print("Ingrese el precio de la entrada: ");
            float precio = scanner.nextFloat();
            System.out.print("Ingrese la duración de la función (en minutos): ");
            int minutos = scanner.nextInt();
            // 1000 * 1seg -> 60000 * 1min
            Timestamp fin = new Timestamp(System.currentTimeMillis() + (minutos*60000L));
            System.out.println(modulo.nuevaFuncion(new ProcedimientoAgregarFuncion(imdb, sala, inicio, fin, precio)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un numero válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarFuncion(ModuloGerente modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR FUNCIÓN ___");
        System.out.print("Ingrese el IMDB: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String nombre = scanner.next();
        Timestamp inicio = new Timestamp(System.currentTimeMillis());
        try {
            System.out.print("Ingrese la duración de la función (en minutos): ");
            int minutos = scanner.nextInt();
            // 1000 * 1seg -> 60000 * 1min
            Timestamp fin = new Timestamp(System.currentTimeMillis() + (minutos * 60000L));
            System.out.print("Ingrese el precio de la entrada: ");
            float precio = scanner.nextFloat();
            System.out.println(modulo.actualizarFuncion(new ProcedimientoActualizarFuncion(imdb, nombre, inicio, fin, precio)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un número válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarFuncion(ModuloAdministrador modulo, Scanner scanner) {
        System.out.println("___ BORRAR FUNCIÓN ___");
        System.out.print("Ingrese el IMDB: ");
        String imdb = scanner.next();
        System.out.print("Ingrese el nombre de la sala: ");
        String nombre = scanner.next();
        System.out.println("Se eliminarán todas las reservas y tickets para esta función.");
        System.out.println(modulo.getConexion().ejecutar(new ProcedimientoPurgarFuncion(imdb, nombre)) + " filas modificadas");
        System.out.println("Ahora se procede a borrar la función.");
        System.out.println(modulo.borrarFuncion(new ProcedimientoBorrarFuncion(imdb, nombre)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void nuevaSala(ModuloAdministrador modulo, Scanner scanner) {
        System.out.println("___ NUEVA SALA ___");
        System.out.print("Ingrese el nombre de la sala: ");
        String nombre = scanner.next();
        System.out.print("Ingrese la capacidad de la sala: ");
        try {
            int capacidad = scanner.nextInt();
            System.out.println(modulo.nuevaSala(new ProcedimientoAgregarSala(nombre, capacidad, true)));
            listarSalas(modulo.getConexion());
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un numero válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void actualizarSala(ModuloAdministrador modulo, Scanner scanner) {
        System.out.println("___ ACTUALIZAR SALA ___");
        System.out.print("Ingrese el nombre de la sala: ");
        String nombreAnt = scanner.next();
        System.out.print("Ingrese el nuevo nombre de la sala: ");
        String nombre = scanner.next();
        try {
            System.out.print("Ingrese la nueva capacidad de la sala: ");
            int capacidad = scanner.nextInt();
            System.out.print("¿La sala está habilitada? (true o false): ");
            boolean habilitada = scanner.nextBoolean();
            System.out.println(modulo.actualizarSala(new ProcedimientoActualizarSala(nombreAnt, nombre, capacidad, habilitada)));
            modulo.getConexion().aplicar();
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

    /**
     * Se explica solo.
     *
     * @param modulo módulo del usuario.
     * @param scanner entrada por teclado.
     */
    protected static void borrarSala(ModuloAdministrador modulo, Scanner scanner) {
        System.out.println("___ BORRAR SALA ___");
        System.out.print("Ingrese el nombre de la sala: ");
        String nombre = scanner.next();
        System.out.println("Se eliminarán todas las funciones, reservas y entradas ligadas.");
        System.out.println(modulo.getConexion().ejecutar(new ProcedimientoPurgarSala(nombre)) + " filas modificadas");
        System.out.printf("Se eliminará la sala %s\n", nombre);
        System.out.println(modulo.borrarSala(new ProcedimientoBorrarSala(nombre)));
        try {
            System.out.print("Confirme (true para continuar, false para deshacer): ");
            if (scanner.nextBoolean()) {
                modulo.getConexion().aplicar();
                System.out.println("CAMBIOS APLICADOS!!!");
            } else {
                modulo.getConexion().deshacer();
                System.out.println("CAMBIOS OLVIDADOS!!!");
            }
        } catch (InputMismatchException mismatchException) {
            System.out.println("no ingresaste un valor válido.\noperación cancelada.");
        }
    }

}
