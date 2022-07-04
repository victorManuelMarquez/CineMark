package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Procedimiento almacenado en la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ProcedimientoVerificarConsumo extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param dni dni del cliente.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoVerificarConsumo(@NotNull final String dni) throws IllegalArgumentException {
        super("verificarConsumo(?)", dni);
        if (dni.isBlank()) throw new IllegalArgumentException("dni en blanco.");
    }

    @Override
    public Integer obtener(@NotNull Connection connection) throws RuntimeException {
        int filas = 0;
        try (CallableStatement statement = connection.prepareCall(consulta())) {
            cargarArgumentos(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    filas++; // solo interesa la cantidad de meses, en este caso 6 meses mínimo en este año.
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
        return filas;
    }

}
