package med.clinica.api.domain.consulta.validations;

import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeFuncionamiento implements ValidadorDeConsultas{
    @Override
    public void validar(DatosRegistroConsulta datosRegistroConsulta) {
        var domingo = DayOfWeek.SUNDAY.equals(datosRegistroConsulta.fecha().getDayOfWeek());

        var antesDeApertura = datosRegistroConsulta.fecha().getHour()<7;
        var depuesDeApertura = datosRegistroConsulta.fecha().getHour()>19;

        if(domingo || antesDeApertura || depuesDeApertura){
            throw new ValidacionDeIntegridad("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
