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
public final class ProcedimientoAgregarFuncion extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param imdb       código de la película en la página IMDB.
     * @param nombreSala nombre de la sala donde se exhibe.
     * @param inicio     fecha y hora de inicio.
     * @param fin        fecha y hora de fin.
     * @param precio     precio de la entrada.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoAgregarFuncion(
            @NotNull final String imdb,
            @NotNull final String nombreSala,
            @NotNull final Timestamp inicio,
            @NotNull final Timestamp fin,
            @NotNull final Float precio
    ) throws IllegalArgumentException {
        super("agregarFuncion(?,?,?,?,?)", imdb, nombreSala, inicio, fin, precio);
        if (imdb.isBlank() || nombreSala.isBlank()) throw new IllegalArgumentException("valor en blanco.");
        if (inicio.after(fin)) throw new IllegalArgumentException("fecha de inicio es mayor a la de fin.");
        if (precio <= 0) throw new IllegalArgumentException("precio <= 0.");
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
