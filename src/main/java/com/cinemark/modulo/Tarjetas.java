package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarTarjeta;
import com.cinemark.conexion.consulta.ProcedimientoAgregarTarjeta;
import com.cinemark.conexion.consulta.ProcedimientoBorrarTarjeta;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar una tarjeta de cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Tarjetas {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevaTarjeta(@NotNull final ProcedimientoAgregarTarjeta consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarTarjeta(@NotNull final ProcedimientoActualizarTarjeta consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarTarjeta(@NotNull final ProcedimientoBorrarTarjeta consulta);

}
