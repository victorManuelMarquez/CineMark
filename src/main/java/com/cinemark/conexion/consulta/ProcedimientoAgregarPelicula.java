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
public final class ProcedimientoAgregarPelicula extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param imdb         código de la película en la página IMDB.
     * @param titulo       título.
     * @param director     director o directores.
     * @param reparto      reparto o elenco.
     * @param fechaEstreno fecha de estreno mundial.
     * @param duracion     duración de la película en minutos.
     * @param cartelera    está en cartelera.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoAgregarPelicula(
            @NotNull final String imdb,
            @NotNull final String titulo,
            @NotNull final String director,
            @NotNull final String reparto,
            @NotNull final Date fechaEstreno,
            @NotNull final Integer duracion,
            @NotNull final Boolean cartelera
    ) throws IllegalArgumentException {
        super("agregarPelicula(?,?,?,?,?,?,?)", imdb, titulo, director, reparto, fechaEstreno, duracion, cartelera);
        if (imdb.isBlank() || titulo.isBlank() || director.isBlank() || reparto.isBlank()) {
            throw new IllegalArgumentException("valor en blanco.");
        }
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
