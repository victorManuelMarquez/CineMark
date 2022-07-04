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
public final class ModuloAdministrador extends ModuloGerente implements Salas {

    /**
     * Constructor por defecto.
     *
     * @param conexion la conexión establecida.
     */
    @SuppressWarnings("SpellCheckingInspection")
    public ModuloAdministrador(@NotNull Conexion conexion) {
        super(conexion);
        setNombre("Administrador");
    }

    @Override
    public String nuevaReserva(@NotNull final ProcedimientoAgregarReserva consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Reserva exitosa.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarReserva(@NotNull ProcedimientoActualizarReserva consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Reserva actualizada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarReserva(@NotNull ProcedimientoBorrarReserva consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Reserva borrada correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String nuevoUsuario(@NotNull ProcedimientoAgregarUsuario consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Usuario agregado correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarUsuario(@NotNull ProcedimientoActualizarUsuario consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Usuario actualizado correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarUsuario(@NotNull ProcedimientoBorrarUsuario consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Usuario borrado correctamente\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarFuncion(@NotNull ProcedimientoBorrarFuncion consulta) throws RuntimeException {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Función eliminada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarPelicula(@NotNull ProcedimientoBorrarPelicula consulta) throws RuntimeException {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Película eliminada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String nuevaSala(@NotNull ProcedimientoAgregarSala consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Sala agregada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String actualizarSala(@NotNull ProcedimientoActualizarSala consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Sala actualizada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public String borrarSala(@NotNull ProcedimientoBorrarSala consulta) {
        try {
            int filas = getConexion().ejecutar(consulta);
            return String.format("Sala eliminada correctamente.\n%d filas modificadas.", filas);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}
