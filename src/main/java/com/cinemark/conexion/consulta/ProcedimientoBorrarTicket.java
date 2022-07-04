package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Procedimiento almacenado en la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ProcedimientoBorrarTicket extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param codigo código del ticket.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoBorrarTicket(@NotNull final String codigo) throws IllegalArgumentException {
        super("borrarTicket(?)", codigo);
        if (codigo.isBlank()) throw new IllegalArgumentException("código en blanco.");
    }

    @Override
    public Integer obtener(@NotNull Connection connection) throws RuntimeException {
        int filas;
        try (CallableStatement statement = connection.prepareCall(consulta())) {
            cargarArgumentos(statement);
            filas = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
        return filas;
    }

}
