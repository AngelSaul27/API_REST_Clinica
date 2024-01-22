package med.clinica.api.domain.consulta.validations;

import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{

    @Override
    public void validar(DatosRegistroConsulta datosRegistroConsulta) {
        var ahora = LocalDateTime.now();
        var horaDeConsulta= datosRegistroConsulta.fecha();

        var diferenciaDe30Min= Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if(diferenciaDe30Min){
            throw new ValidacionDeIntegridad("Las consultas deben programarse con al menos 30 minutos de anticipación");
        }
    }
}
