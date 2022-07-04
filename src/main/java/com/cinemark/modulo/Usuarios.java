package com.cinemark.modulo;

import com.cinemark.conexion.consulta.ProcedimientoActualizarUsuario;
import com.cinemark.conexion.consulta.ProcedimientoAgregarUsuario;
import com.cinemark.conexion.consulta.ProcedimientoBorrarUsuario;
import org.jetbrains.annotations.NotNull;

/**
 * Agrega las operaciones para gestionar un usuario del cine.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public interface Usuarios {

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String nuevoUsuario(@NotNull final ProcedimientoAgregarUsuario consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String actualizarUsuario(@NotNull final ProcedimientoActualizarUsuario consulta);

    /**
     * La lógica para realizar esta operación va aquí.
     *
     * @param consulta consulta SQL.
     * @return los resultados de la operación.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    String borrarUsuario(@NotNull final ProcedimientoBorrarUsuario consulta);

}
