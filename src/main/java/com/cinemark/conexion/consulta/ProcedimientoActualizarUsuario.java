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
public final class ProcedimientoActualizarUsuario extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param dni    dni del cliente.
     * @param nombre nombre del usuario.
     * @param clave  contraseña del usuario.
     * @param modulo módulo al que tendrá acceso.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoActualizarUsuario(
            @NotNull final String dni,
            @NotNull final String nombre,
            @NotNull final String clave,
            @NotNull final String modulo
    ) throws IllegalArgumentException {
        super("actualizarUsuario(?,?,?,?)", dni, nombre, clave, modulo);
        if (dni.isBlank() || nombre.isBlank() || clave.isBlank() || modulo.isBlank()) {
            throw new IllegalArgumentException("valor en blanco.");
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
