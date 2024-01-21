package med.clinica.api.domain.paciente;

import med.clinica.api.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente (
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion)
{
    public DatosRespuestaPaciente(Paciente paciente){
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getDocumento(),
                new DatosDireccion(
                        paciente.getDireccion().getCalle(),
                        paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(),
                        paciente.getDireccion().getNumero().toString(),
                        paciente.getDireccion().getComplemento()
                )
        );
    }
}
