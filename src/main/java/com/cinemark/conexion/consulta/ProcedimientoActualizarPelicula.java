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
public final class ProcedimientoActualizarPelicula extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param imdbAnterior código de la película en la página IMDB.
     * @param imdbNuevo    nuevo código de la película en la página IMDB.
     * @param titulo       nuevo título para la película.
     * @param director     director/es de la película.
     * @param reparto      reparto o elenco de la película.
     * @param fechaEstreno fecha de estreno original (primera proyección mundial).
     * @param duracion     duración de la cinta en minutos.
     * @param cartelera    está en cartelera.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoActualizarPelicula(
            @NotNull final String imdbAnterior,
            @NotNull final String imdbNuevo,
            @NotNull final String titulo,
            @NotNull final String director,
            @NotNull final String reparto,
            @NotNull final Date fechaEstreno,
            @NotNull final Integer duracion,
            @NotNull final Boolean cartelera
    ) throws IllegalArgumentException {
        super("actualizarPelicula(?,?,?,?,?,?,?,?)", imdbAnterior, imdbNuevo, titulo, director, reparto, fechaEstreno, duracion, cartelera);
        if (imdbAnterior.isBlank() || imdbNuevo.isBlank() || titulo.isBlank() || director.isBlank() || reparto.isBlank()) {
            throw new RuntimeException("valor en blanco.");
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
