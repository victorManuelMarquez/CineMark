package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Procedimiento almacenado en la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ProcedimientoActualizarTarjeta extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param dni        dni del cliente.
     * @param alta       fecha de habilitación.
     * @param baja       fecha de vencimiento.
     * @param habilitada tarjeta habilitada.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoActualizarTarjeta(
            @NotNull final String dni,
            @NotNull final Timestamp alta,
            @NotNull final Timestamp baja,
            @NotNull final Boolean habilitada
    ) throws IllegalArgumentException {
        super("actualizarTarjeta(?,?,?,?)", dni, alta, baja, habilitada);
        if (dni.isBlank()) throw new IllegalArgumentException("dni en blanco.");
        if (alta.after(baja)) throw new IllegalArgumentException("fecha de registro es mayor a la de vencimiento.");
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
