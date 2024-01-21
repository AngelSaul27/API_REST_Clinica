package med.clinica.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.clinica.api.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente (
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion
){
}
