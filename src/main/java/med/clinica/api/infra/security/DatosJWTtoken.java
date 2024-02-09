package med.clinica.api.infra.security;

import java.util.Date;

public record DatosJWTtoken(String token, Date exp) {
}
