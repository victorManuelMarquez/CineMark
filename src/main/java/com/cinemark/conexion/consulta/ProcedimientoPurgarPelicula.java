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
public final class ProcedimientoPurgarPelicula extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param imdb código de la película en IMDB.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoPurgarPelicula(@NotNull final String imdb) throws IllegalArgumentException {
        super("purgarPelicula(?)", imdb);
        if (imdb.isBlank()) throw new IllegalArgumentException("imdb en blanco.");
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
