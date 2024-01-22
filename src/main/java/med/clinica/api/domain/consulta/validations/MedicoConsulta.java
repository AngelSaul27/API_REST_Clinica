package med.clinica.api.domain.consulta.validations;

import med.clinica.api.domain.consulta.ConsultaRepository;
import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConsulta implements ValidadorDeConsultas{

    private final ConsultaRepository repository;

    @Autowired
    public MedicoConsulta(ConsultaRepository repository){
        this.repository = repository;
    }

    @Override
    public void validar(DatosRegistroConsulta datosRegistroConsulta) {
        if(datosRegistroConsulta.idMedico()==null)
            return;

        var medicoConConsulta= repository.existsByMedicoIdAndFecha(datosRegistroConsulta.idMedico(), datosRegistroConsulta.fecha());
        if(medicoConConsulta){
            throw new ValidacionDeIntegridad("este medico ya tiene una consulta en ese horario");
        }
    }
}
