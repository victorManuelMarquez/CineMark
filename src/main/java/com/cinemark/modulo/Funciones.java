package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarFuncion;
import com.cinemark.conexion.consulta.ProcedimientoAgregarFuncion;
import com.cinemark.conexion.consulta.ProcedimientoBorrarFuncion;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar una función de cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Funciones {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevaFuncion(@NotNull final ProcedimientoAgregarFuncion consulta) throws RuntimeException;

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarFuncion(@NotNull final ProcedimientoActualizarFuncion consulta) throws RuntimeException;

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarFuncion(@NotNull final ProcedimientoBorrarFuncion consulta) throws RuntimeException;

}
