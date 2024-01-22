package med.clinica.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.clinica.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosRegistroConsulta(
        @NotNull
        Long idPaciente,
        @NotNull
        Long idMedico,
        @NotNull
        @Future
        LocalDateTime fecha,
        Especialidad especialidad)
{

}
