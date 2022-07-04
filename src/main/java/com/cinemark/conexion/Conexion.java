package com.cinemark.conexion;

import com.cinemark.conexion.consulta.Consulta;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase única para la gestión de las conexiones con la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public class Conexion implements AutoCloseable {

    /**
     * Constructor por defecto.<br>
     * El formato de la url es el siguiente:
     * {@code jdbc:mysql://localhost:3306/bd?user=root&amp;password=secret}
     *
     * @param url dirección url completa.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    public Conexion(@NotNull final String url) throws RuntimeException {
        try {
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(mensajeError(e), e);
        }
    }

    @Override
    public String toString() {
        return "Conexión con la base de datos";
    }

    /**
     * Permite recuperar la respuesta del servidor a la consulta ejecutada, y volcarse al tipo
     * de dato requerido.
     *
     * @param consulta consulta en lenguaje SQL.
     * @return resultado de la consulta.
     * @param <T> tipo de resultado esperado.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    public <T> T ejecutar(@NotNull Consulta<T> consulta) throws RuntimeException {
        return consulta.obtener(connection);
    }

    /**
     * Realiza los cambios de forma "permanente" en la base de datos.
     *
     * @throws RuntimeException error en tiempo de ejecución.
     */
    public void aplicar() throws RuntimeException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
    }

    /**
     * Omite u olvida cualquier ejecución que se "realizó" con anterioridad.
     *
     * @throws RuntimeException error en tiempo de ejecución.
     */
    public void deshacer() throws RuntimeException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(Conexion.mensajeError(e), e);
        }
    }

    /**
     * Cierra la conexión establecida, liberando el recurso.
     *
     * @throws RuntimeException error en tiempo de ejecución.
     */
    @Override
    public void close() throws RuntimeException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(mensajeError(e), e);
        }
    }

    /**
     * Adapta el mensaje de error generado por el servidor.
     *
     * @param e error del servidor SQL.
     * @return mensaje para mostrarse al usuario final.
     */
    public static String mensajeError(@NotNull SQLException e) {
        String msg = "Se produjo un error (%sx%d).\nDetalles: %s.";
        return String.format(msg, e.getSQLState(), e.getErrorCode(), e.getMessage());
    }

    private final Connection connection;

}
