package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Construye la consulta para ejecutarse, facilitando para su uso un constructor.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 * @param <T> tipo de resultado esperado.
 */
public abstract class ConsultaPreparada<T> implements Consulta<T> {

    /**
     * Constructor base.
     *
     * @param consulta la consulta SQL.
     * @param argumentos los valores requeridos por la consulta.
     */
    public ConsultaPreparada(@NotNull final String consulta, @Nullable final Object... argumentos) {
        this.consulta = consulta;
        this.argumentos = argumentos;
        columnas = new ArrayList<>(Collections.emptyList());
    }

    @Override
    public String toString() {
        return consulta();
    }

    @Override
    public String consulta() {
        return consulta;
    }

    @Override
    public Object[] argumentos() {
        return argumentos;
    }

    /**
     * Las columnas generadas por el servidor deben agregarse aquí.<br>
     *
     * @return las columnas de la "tabla" generada.
     */
    public List<String> columnas() {
        return columnas;
    }

    /**
     * Recupera las columnas generadas por el servidor y las almacena en el {@code array} correspondiente.
     *
     * @param metaData los metadatos donde se aloja la información requerida.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    protected final void cargarColumnas(@NotNull final ResultSetMetaData metaData) throws RuntimeException {
        try {
            columnas.clear();
            for (int c = 1; c <= metaData.getColumnCount(); c++) {
                columnas.add(metaData.getColumnName(c));
            }
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
    }

    /**
     * Agrega los valores de cada parámetro al objeto de la consulta.
     *
     * @param statement objeto de consulta.
     * @throws SQLException error en tiempo de ejecución.
     */
    protected final void cargarArgumentos(@NotNull PreparedStatement statement) throws SQLException {
        int arg = 1;
        for (Object valor : argumentos()) {
            statement.setObject(arg, valor);
            arg++;
        }
    }

    /**
     * Recupera los resultados de la ejecución de la consulta.
     *
     * @param filas matriz de datos.
     * @param resultSet objeto resultados de la consulta.
     * @throws SQLException error en tiempo de ejecución.
     */
    protected final void cargarResultados(@NotNull List<List<Object>> filas, final @NotNull ResultSet resultSet) throws SQLException {
        int columnas = resultSet.getMetaData().getColumnCount();
        filas.clear();
        while (resultSet.next()) {
            List<Object> fila = new ArrayList<>();
            for (int i=0; i<columnas; i++) {
                fila.add(resultSet.getObject(i+1));
            }
            filas.add(fila);
        }
    }

    private final String consulta;
    private final Object[] argumentos;
    private final List<String> columnas;

}
