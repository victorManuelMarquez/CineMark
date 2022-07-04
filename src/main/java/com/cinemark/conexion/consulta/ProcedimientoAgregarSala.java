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
public final class ProcedimientoAgregarSala extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param nombre     nombre de la sala.
     * @param capacidad  capacidad.
     * @param habilitada está habilitada.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoAgregarSala(
            @NotNull final String nombre,
            @NotNull final Integer capacidad,
            @NotNull final Boolean habilitada
    ) throws IllegalArgumentException {
        super("agregarSala(?,?,?)", nombre, capacidad, habilitada);
        if (nombre.isBlank()) throw new IllegalArgumentException("nombre de la sala en blanco.");
        if (capacidad <= 0) throw new IllegalArgumentException("capacidad <= 0.");
    }

    @Override
    public Integer obtener(@NotNull Connection connection) throws RuntimeException {
        int filas;
        try (CallableStatement callableStatement = connection.prepareCall(consulta())) {
            cargarArgumentos(callableStatement);
            filas = callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
        return filas;
    }

}
