package med.clinica.api.medico;

import med.clinica.api.direccion.DatosDireccion;

@SuppressWarnings("all")
public record DatosRegistroMedico(
        String nombre,
        String email,
        String documento,
        Especialidad especialidad,
        DatosDireccion direccion)
{
}
