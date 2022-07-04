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
public final class ProcedimientoAgregarTicket extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param codigo     código del ticket.
     * @param imdb       código de la película en la página IMDB.
     * @param nombreSala sala donde se exhibe.
     * @param butaca     butaca asignada.
     * @param dniCliente dni del cliente.
     * @param monto      precio de la entrada.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoAgregarTicket(
            @NotNull final String codigo,
            @NotNull final String imdb,
            @NotNull final String nombreSala,
            @NotNull final String butaca,
            @NotNull final String dniCliente,
            @NotNull final Float monto
    ) throws IllegalArgumentException {
        super("agregarTicket(?,?,?,?,?,?)", codigo, imdb, nombreSala, butaca, dniCliente, monto);
        if (dniCliente.isBlank() || imdb.isBlank() || nombreSala.isBlank() || butaca.isBlank() || dniCliente.isBlank()) {
            throw new IllegalArgumentException("valor en blanco.");
        }
        if (monto <= 0.f) {
            throw new IllegalArgumentException("precio del ticket menor o igual a $0.00.");
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
