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
public final class ProcedimientoActualizarSala extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param nombreAnterior nombre de sala.
     * @param nombreNuevo    nuevo nombre para la sala.
     * @param capacidad      nueva capacidad para la sala.
     * @param habilitada     sala habilitada.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoActualizarSala(
            @NotNull final String nombreAnterior,
            @NotNull final String nombreNuevo,
            @NotNull final Integer capacidad,
            @NotNull final Boolean habilitada
    ) throws IllegalArgumentException {
        super("actualizarSala(?,?,?,?)", nombreAnterior, nombreNuevo, capacidad, habilitada);
        if (nombreAnterior.isBlank() || nombreNuevo.isBlank()) {
            throw new IllegalArgumentException("valor en blanco.");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("capacidad <= 0.");
        }
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
