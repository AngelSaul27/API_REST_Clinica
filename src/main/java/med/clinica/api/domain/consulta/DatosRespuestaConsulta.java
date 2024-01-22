package med.clinica.api.domain.consulta;

import java.time.LocalDateTime;

public record DatosRespuestaConsulta (
        Long id,
        Long paciente_id,
        Long medico_id,
        LocalDateTime fecha
){
    public DatosRespuestaConsulta(Consulta consulta) {
        this(consulta.getId(),consulta.getPaciente().getId(),consulta.getMedico().getId(),consulta.getFecha());
    }
}
