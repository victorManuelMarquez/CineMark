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
public class ModuloBoletero extends ModuloCliente implements Tickets, Clientes, Tarjetas {

    /**
     * Constructor por defecto.
     *
     * @param conexion la conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ModuloBoletero(@NotNull Conexion conexion) {
        super(conexion);
        setNombre("Boletero");
    }

    @Override
    public String nuevaReserva(@NotNull ProcedimientoAgregarReserva consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String actualizarReserva(@NotNull ProcedimientoActualizarReserva consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String borrarReserva(@NotNull ProcedimientoBorrarReserva consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String nuevoUsuario(@NotNull ProcedimientoAgregarUsuario consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String actualizarUsuario(@NotNull ProcedimientoActualizarUsuario consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String venderTicket(@NotNull final ProcedimientoAgregarTicket consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Ticket vendido.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarTicket(@NotNull ProcedimientoActualizarTicket consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String borrarTicket(@NotNull ProcedimientoBorrarTicket consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String nuevoCliente(@NotNull ProcedimientoAgregarCliente consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Nuevo cliente agregado correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarCliente(@NotNull ProcedimientoActualizarCliente consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Cliente actualizado correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarCliente(@NotNull ProcedimientoBorrarCliente consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String nuevaTarjeta(@NotNull ProcedimientoAgregarTarjeta consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Tarjeta agregada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarTarjeta(@NotNull ProcedimientoActualizarTarjeta consulta) {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String borrarTarjeta(@NotNull ProcedimientoBorrarTarjeta consulta) {
        return "No es posible realizar esta operación.";
    }

}
