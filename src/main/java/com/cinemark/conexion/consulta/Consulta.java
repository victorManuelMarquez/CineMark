package com.cinemark.conexion.consulta;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

/**
 * Cuerpo básico para una consulta SQL.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 * @param <T> tipo de dato que se espera como resultado.
 */
public interface Consulta<T> {

    /**
     * La consulta SQL debe especificarse aquí.
     *
     * @return la consulta SQL para ejecutar.
     */
    String consulta();

    /**
     * Los parámetros o argumentos para esta consulta deben estar aquí.
     *
     * @return un {@code array} de valores para la consulta.
     */
    Object[] argumentos();

    /**
     * Los resultados que se deseen guardar deben especificarse aquí.
     *
     * @param connection la conexión establecida con la base de datos.
     * @return los resultados de la ejecución de la consulta.
     * @throws RuntimeException error en tiempo de ejecución.
     */
    T obtener(@NotNull final Connection connection) throws RuntimeException;

}
