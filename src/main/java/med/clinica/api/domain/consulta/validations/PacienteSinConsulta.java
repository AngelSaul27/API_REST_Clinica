package med.clinica.api.domain.consulta.validations;

import med.clinica.api.domain.consulta.ConsultaRepository;
import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    private final ConsultaRepository repository;

    @Autowired
    public PacienteSinConsulta(ConsultaRepository repository){
        this.repository = repository;
    }

    @Override
    public void validar(DatosRegistroConsulta datosRegistroConsulta) {
        var primerHorario = datosRegistroConsulta.fecha().withHour(7);
        var ultimoHorario = datosRegistroConsulta.fecha().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteIdAndFechaBetween(datosRegistroConsulta.idPaciente()
            ,primerHorario,ultimoHorario);

        if(pacienteConConsulta){
            throw new ValidacionDeIntegridad("El paciente ya tiene una consulta para ese día");
        }
    }
}
