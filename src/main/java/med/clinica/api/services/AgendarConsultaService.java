package med.clinica.api.services;

import med.clinica.api.domain.consulta.Consulta;
import med.clinica.api.domain.consulta.ConsultaRepository;
import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.domain.consulta.DatosRespuestaConsulta;
import med.clinica.api.domain.consulta.validations.ValidadorDeConsultas;
import med.clinica.api.domain.medico.Medico;
import med.clinica.api.domain.medico.MedicoRepository;
import med.clinica.api.domain.paciente.PacienteRepository;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendarConsultaService {

    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final List<ValidadorDeConsultas> validadores;

    @Autowired
    public AgendarConsultaService(
        MedicoRepository medicoRepository,
        PacienteRepository pacienteRepository,
        ConsultaRepository consultaRepository,
        List<ValidadorDeConsultas> validadores)
    {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.validadores = validadores;
    }

    public DatosRespuestaConsulta agendar(DatosRegistroConsulta datosRegistroConsulta){
        if(!pacienteRepository.findById(datosRegistroConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }

        if(datosRegistroConsulta.idMedico()!=null && !medicoRepository.existsById(datosRegistroConsulta.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datosRegistroConsulta));

        var paciente = pacienteRepository.findById(datosRegistroConsulta.idPaciente()).get();
        var medico = seleccionarMedico(datosRegistroConsulta);

        if(medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(null,medico,paciente,datosRegistroConsulta.fecha());
        consultaRepository.save(consulta);

        return new DatosRespuestaConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosRegistroConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }

}
