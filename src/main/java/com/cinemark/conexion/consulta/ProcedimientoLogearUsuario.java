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
public final class ProcedimientoLogearUsuario extends ProcedimientoAlmacenado<String> {

    /**
     * Constructor por defecto.
     *
     * @param nombre nombre de usuario.
     * @param clave  contraseña de usuario.
     * @throws IllegalArgumentException valor incorrecto.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ProcedimientoLogearUsuario(
            @NotNull final String nombre,
            @NotNull final String clave
    ) throws IllegalArgumentException {
        super("logearUsuario(?,?)", nombre, clave);
        if (nombre.isBlank() || clave.isBlank()) throw new IllegalArgumentException("valor en blanco.");
    }

    @Override
    public String obtener(@NotNull Connection connection) throws RuntimeException {
        String resultado = null;
        try (CallableStatement statement = connection.prepareCall(consulta())) {
            cargarArgumentos(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    resultado = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
        return resultado;
    }

}
