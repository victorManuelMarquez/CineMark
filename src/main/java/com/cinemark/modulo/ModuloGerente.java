package com.cinemark.modulo;

import com.cinemark.conexion.Conexion;
import com.cinemark.conexion.consulta.*;
import org.jetbrains.annotations.NotNull;

/**
 * Módulo para establecer las acciones que pueden realizar en el sistema.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public class ModuloGerente extends ModuloBoletero implements Peliculas, Funciones {

    /**
     * Constructor por defecto.
     *
     * @param conexion la conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ModuloGerente(@NotNull Conexion conexion) {
        super(conexion);
        setNombre("Gerente");
    }

    @Override
    public String actualizarTicket(@NotNull final ProcedimientoActualizarTicket consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Ticket actualizado correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarTicket(@NotNull final ProcedimientoBorrarTicket consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Ticket eliminado correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarCliente(@NotNull ProcedimientoBorrarCliente consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Cliente borrado correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarTarjeta(@NotNull ProcedimientoActualizarTarjeta consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Tarjeta actualizada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarTarjeta(@NotNull ProcedimientoBorrarTarjeta consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Tarjeta borrada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String nuevaFuncion(@NotNull ProcedimientoAgregarFuncion consulta) throws RuntimeException {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Función agregada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarFuncion(@NotNull ProcedimientoActualizarFuncion consulta) throws RuntimeException {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Función actualizada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarFuncion(@NotNull ProcedimientoBorrarFuncion consulta) throws RuntimeException {
        return "No es posible realizar esta operación.";
    }

    @Override
    public String nuevaPelicula(@NotNull ProcedimientoAgregarPelicula consulta) throws RuntimeException {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Película agregada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarPelicula(@NotNull ProcedimientoActualizarPelicula consulta) throws RuntimeException {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Película eliminada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarPelicula(@NotNull ProcedimientoBorrarPelicula consulta) throws RuntimeException {
        return "No es posible realizar esta operación.";
    }

}
