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
public final class ProcedimientoActualizarTicket extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param codigo     código del ticket.
     * @param imdb       código de la película en la página IMDB.
     * @param nombreSala nombre de la sala donde se exhibe.
     * @param butaca     butaca o asiento asignado.
     * @param dni        dni del cliente.
     * @param monto      precio del ticket.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoActualizarTicket(
            @NotNull final String codigo,
            @NotNull final String imdb,
            @NotNull final String nombreSala,
            @NotNull final String butaca,
            @NotNull final String dni,
            @NotNull final Float monto
    ) throws IllegalArgumentException {
        super("actualizarTicket(?,?,?,?,?,?)", codigo, imdb, nombreSala, butaca, dni, monto);
        if (codigo.isBlank() || imdb.isBlank() || nombreSala.isBlank() || butaca.isBlank() || dni.isBlank()) {
            throw new IllegalArgumentException("valor en blanco.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("monto <= 0.");
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
