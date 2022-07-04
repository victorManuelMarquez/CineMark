package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Procedimiento almacenado en la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ProcedimientoListarFunciones extends ProcedimientoAlmacenado<List<List<Object>>> {

    /**
     * Constructor por defecto.
     *
     * @param fila  posición en el registro.
     * @param nroFilas total de filas a recuperar.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoListarFunciones(@NotNull Integer fila, Integer nroFilas) throws IllegalArgumentException {
        super("listarFunciones(?,?)", fila, nroFilas);
        if (fila < 0) throw new IllegalArgumentException("número de fila < 0");
        if (nroFilas <= 0) throw new IllegalArgumentException("filas a recopilar <= 0");
    }

    @Override
    public List<List<Object>> obtener(@NotNull Connection connection) throws RuntimeException {
        List<List<Object>> filas = new ArrayList<>(Collections.emptyList());
        try (CallableStatement statement = connection.prepareCall(consulta())) {
            cargarArgumentos(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                cargarResultados(filas, resultSet);
                cargarColumnas(resultSet.getMetaData());
            }
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
        return filas;
    }

}
