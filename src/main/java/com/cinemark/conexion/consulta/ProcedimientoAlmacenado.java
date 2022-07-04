package com.cinemark.conexion.consulta;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Construye una consulta o procedimiento almacenado en la base de datos.<br>
 * Tener en cuenta respetar la firma del procedimiento tal cual como está declarado, cabe mencionar
 * que esta clase se creó para reforzar la integridad de los datos que irán a la base de datos.
 *
 * @author victor
 * @version 1.0
 * @since 1.0
 * @param <T> tipo de dato esperado.
 */
public abstract class ProcedimientoAlmacenado<T> extends ConsultaPreparada<T> {

    /**
     * Constructor base.
     *
     * @param consulta consulta o procedimiento.
     * @param argumentos los valores requeridos.
     * @throws IllegalArgumentException valor incorrecto.
     */
    public ProcedimientoAlmacenado(@NotNull final String consulta, final @Nullable Object... argumentos) throws IllegalArgumentException {
        super(consulta, argumentos);
    }

    @Override
    public String consulta() {
        return String.format("{CALL %s}", super.consulta());
    }

}
