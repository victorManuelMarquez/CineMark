/*
 * com.cinemark.conexion.consulta.ProcedimientoAgregarTarjeta.java
 *
 * Copyright 2022 Victor Manuel Márquez <victor>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 *
 */
package com.cinemark.conexion.consulta;

import com.cinemark.conexion.Conexion;
import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Procedimiento almacenado en la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 */
public final class ProcedimientoAgregarTarjeta extends ProcedimientoAlmacenado<Integer> {

    /**
     * Constructor por defecto.
     *
     * @param alta       fecha de habilitación.
     * @param baja       fecha de vencimiento.
     * @param dniCliente dni del cliente.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoAgregarTarjeta(
            @NotNull final Timestamp alta,
            @NotNull final Timestamp baja,
            @NotNull final String dniCliente
    ) throws IllegalArgumentException {
        super("agregarTarjeta(?,?,?)", alta, baja, dniCliente);
        if (dniCliente.isBlank()) {
            throw new IllegalArgumentException("dni en blanco");
        }
        if (alta.after(baja)) {
            throw new IllegalArgumentException("fecha de alta acontece después del vencimiento");
        } else if (baja.equals(alta)) {
            throw new IllegalArgumentException("fecha de alta y de vencimiento son iguales");
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
