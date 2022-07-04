package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarSala;
import com.cinemark.conexion.consulta.ProcedimientoAgregarSala;
import com.cinemark.conexion.consulta.ProcedimientoBorrarSala;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar una sala de cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Salas {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevaSala(@NotNull final ProcedimientoAgregarSala consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarSala(@NotNull final ProcedimientoActualizarSala consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarSala(@NotNull final ProcedimientoBorrarSala consulta);

}
