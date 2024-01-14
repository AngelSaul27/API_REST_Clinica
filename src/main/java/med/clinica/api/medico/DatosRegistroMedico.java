package med.clinica.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.clinica.api.direccion.DatosDireccion;

@SuppressWarnings("all")
public record DatosRegistroMedico(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4}")
        String documento,

        @NotNull
        Especialidad especialidad,
        @NotNull
        @Valid
        DatosDireccion direccion)
{


}
