package med.clinica.api.domain.consulta.validations;

import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.domain.medico.MedicoRepository;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{

    private final MedicoRepository repository;

    @Autowired
    public MedicoActivo(MedicoRepository repository){
        this.repository = repository;
    }

    @Override
    public void validar(DatosRegistroConsulta datosRegistroConsulta) {
        if(datosRegistroConsulta.idMedico()==null){
            return;
        }
        var medicoActivo=repository.findActivoById(datosRegistroConsulta.idMedico());
        if(!medicoActivo){
            throw new ValidacionDeIntegridad("No se puede permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}
