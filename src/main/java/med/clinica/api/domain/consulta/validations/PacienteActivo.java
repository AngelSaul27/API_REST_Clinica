package med.clinica.api.domain.consulta.validations;

import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.domain.paciente.PacienteRepository;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    private final PacienteRepository repository;

    @Autowired
    public PacienteActivo(PacienteRepository repository){
        this.repository = repository;
    }

    public void validar(DatosRegistroConsulta datos){
        if(datos.idPaciente()==null){
            return;
        }
        var pacienteActivo = repository.findActivoById(datos.idPaciente());

        if(!pacienteActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas con pacientes inactivos en el sistema");
        }
    }
}
