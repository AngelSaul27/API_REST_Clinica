package med.clinica.api.medico;

import jakarta.validation.constraints.NotNull;
import med.clinica.api.direccion.DatosDireccion;

public record DatosActualizarMedico (
        @NotNull
        Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
