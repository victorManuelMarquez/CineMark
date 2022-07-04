package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarCliente;
import com.cinemark.conexion.consulta.ProcedimientoAgregarCliente;
import com.cinemark.conexion.consulta.ProcedimientoBorrarCliente;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar a un cliente del cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Clientes {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevoCliente(@NotNull final ProcedimientoAgregarCliente consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarCliente(@NotNull final ProcedimientoActualizarCliente consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarCliente(@NotNull final ProcedimientoBorrarCliente consulta);

}
