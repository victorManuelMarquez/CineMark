package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarReserva;
import com.cinemark.conexion.consulta.ProcedimientoAgregarReserva;
import com.cinemark.conexion.consulta.ProcedimientoBorrarReserva;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar una reserva del cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Reservas {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevaReserva(@NotNull final ProcedimientoAgregarReserva consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarReserva(@NotNull final ProcedimientoActualizarReserva consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarReserva(@NotNull final ProcedimientoBorrarReserva consulta);

}
