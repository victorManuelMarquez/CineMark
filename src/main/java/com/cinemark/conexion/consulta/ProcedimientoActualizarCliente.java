package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Procedimiento almacenado en la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ProcedimientoActualizarCliente extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param dniAnterior dni del cliente original.
     * @param dniNuevo    nuevo número de dni.
     * @param nacimiento  fecha de nacimiento.
     * @param alta        fecha de registro del cliente.
     * @param activo      cliente activo.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoActualizarCliente(
            @NotNull final String dniAnterior,
            @NotNull final String dniNuevo,
            @NotNull final Date nacimiento,
            @NotNull final Date alta,
            @NotNull final Boolean activo
    ) throws IllegalArgumentException {
        super("actualizarCliente(?,?,?,?,?)", dniAnterior, dniNuevo, nacimiento, alta, activo);
        if (dniAnterior.isBlank() || dniNuevo.isBlank()) {
            throw new IllegalArgumentException("el dni está en blanco.");
        }
        if (nacimiento.after(alta)) {
            throw new IllegalArgumentException("fecha de nacimiento mayor a la fecha de registro.");
        } else if (nacimiento.equals(alta)) {
            throw new IllegalArgumentException("fecha de nacimiento igual a la fecha de registro.");
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
