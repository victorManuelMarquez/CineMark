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
public final class ProcedimientoAgregarReserva extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param imdb       código de la película en la página IMDB.
     * @param nombreSala nombre de la sala donde se exhibe.
     * @param dni        dni del cliente.
     * @param butacas    cantidad de butacas.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoAgregarReserva(
            @NotNull final String imdb,
            @NotNull final String nombreSala,
            @NotNull final String dni,
            @NotNull final Integer butacas
    ) throws IllegalArgumentException {
        super("agregarReserva(?,?,?,?)", imdb, nombreSala, dni, butacas);
        if (imdb.isBlank() || nombreSala.isBlank() || dni.isBlank()) {
            throw new IllegalArgumentException("valor en blanco.");
        }
        if (butacas <= 0) throw new IllegalArgumentException("butacas <= 0");
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
