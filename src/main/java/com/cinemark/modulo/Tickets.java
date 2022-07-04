package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarTicket;
import com.cinemark.conexion.consulta.ProcedimientoAgregarTicket;
import com.cinemark.conexion.consulta.ProcedimientoBorrarTicket;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar un ticket o entrada de cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Tickets {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String venderTicket(@NotNull final ProcedimientoAgregarTicket consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarTicket(@NotNull final ProcedimientoActualizarTicket consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarTicket(@NotNull final ProcedimientoBorrarTicket consulta);

}
