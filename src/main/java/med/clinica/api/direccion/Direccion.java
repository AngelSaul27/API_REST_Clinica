package med.clinica.api.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Direccion {

    private String calle;
    private Integer numero;
    private String complemento;
    private String distrito;
    private String ciudad;


    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.numero = Integer.valueOf(direccion.numero());
        this.complemento = direccion.complemento();
        this.distrito = direccion.distrito();
        this.ciudad = direccion.ciudad();
    }
}
