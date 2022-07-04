package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Esta consulta sirve para comprobar la conexión con el servidor sin iniciar sesión.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ConsultaObtenerVersionSQL extends ConsultaPreparada<String> {

    /**
     * Constructor por defecto.
     */
    public ConsultaObtenerVersionSQL() {
        super("SELECT VERSION() AS 'versión de SQL';");
    }

    @Override
    public String obtener(@NotNull Connection connection) throws RuntimeException {
        String version = null;
        try (
                PreparedStatement statement = connection.prepareStatement(consulta());
                ResultSet resultSet = statement.executeQuery()
        ) {
            if (resultSet.next()) {
                version = resultSet.getString(1);
            }
            cargarColumnas(resultSet.getMetaData());
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
        return version;
    }

}
