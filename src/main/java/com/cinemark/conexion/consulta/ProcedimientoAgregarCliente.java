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
public final class ProcedimientoAgregarCliente extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param dni               dni del cliente.
     * @param fechaDeNacimiento fecha de nacimiento del cliente.
     * @param fechaDeAlta       fechad de registro del cliente.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoAgregarCliente(
            @NotNull final String dni,
            @NotNull final Date fechaDeNacimiento,
            @NotNull final Date fechaDeAlta
    ) throws IllegalArgumentException {
        super("agregarCliente(?,?,?)", dni, fechaDeNacimiento, fechaDeAlta);
        if (dni.isBlank()) throw new IllegalArgumentException("dni en blanco.");
        if (fechaDeNacimiento.after(fechaDeAlta)) throw new IllegalArgumentException("fecha de nacimiento es mayor a la fecha de alta.");
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
