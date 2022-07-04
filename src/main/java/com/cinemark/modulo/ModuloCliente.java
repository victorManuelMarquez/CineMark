package com.cinemark.modulo;

import com.cinemark.conexion.Conexion;
import com.cinemark.conexion.consulta.*;
import org.jetbrains.annotations.NotNull;

/**
 * Módulo para establecer las acciones que pueden realizar en el sistema.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public class ModuloCliente implements Reservas, Usuarios {

    /**
     * Constructor por defecto.
     *
     * @param conexion la conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ModuloCliente(@NotNull Conexion conexion) {
        this.conexion = conexion;
        setNombre("Cliente");
    }

    @Override
    public String toString() {
        return "Módulo " + getNombre();
    }

    @Override
    public String nuevaReserva(@NotNull final ProcedimientoAgregarReserva consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Reserva exitosa.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarReserva(@NotNull final ProcedimientoActualizarReserva consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Reserva actualizada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarReserva(@NotNull final ProcedimientoBorrarReserva consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Reserva borrada correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String nuevoUsuario(@NotNull ProcedimientoAgregarUsuario consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Usuario agregado correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarUsuario(@NotNull ProcedimientoActualizarUsuario consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Usuario actualizado correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarUsuario(@NotNull ProcedimientoBorrarUsuario consulta) {
        return "No es posible realizar esta operación.";
    }

    private final Conexion conexion;
    private String nombre;

    /**
     * Devuelve la conexión establecida para el módulo.
     *
     * @return una conexión.
     */
    public Conexion getConexion() {
        return conexion;
    }

    /**
     * Devuelve el nombre a modo de "usuario" que se usará en el sistema.
     *
     * @return el nombre del módulo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre a modo de "usuario" que se usará en el sistema.
     *
     * @param nombre el nombre para el módulo.
     */
    protected void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
