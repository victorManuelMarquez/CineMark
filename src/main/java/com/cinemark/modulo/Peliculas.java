package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarPelicula;
import com.cinemark.conexion.consulta.ProcedimientoAgregarPelicula;
import com.cinemark.conexion.consulta.ProcedimientoBorrarPelicula;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar una película del cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Peliculas {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevaPelicula(@NotNull final ProcedimientoAgregarPelicula consulta) throws RuntimeException;

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarPelicula(@NotNull final ProcedimientoActualizarPelicula consulta) throws RuntimeException;

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarPelicula(@NotNull final ProcedimientoBorrarPelicula consulta) throws RuntimeException;

}
