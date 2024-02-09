package med.clinica.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.clinica.api.domain.users.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            System.out.println(apiSecret);
            return JWT.create()
                    .withIssuer("clinica")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token){
        if(token == null){
            throw new RuntimeException();
        }

        DecodedJWT verifier = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("clinica")
                    .build()
                    .verify(token);

        } catch (JWTVerificationException exception){
            System.out.println(exception.getMessage());
        }

        if(verifier == null || verifier.getSubject() == null){
            throw new RuntimeException("Verificación invalida");
        }

        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public Date getExpirationDate(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token).getExpiresAt();
        } catch (JWTDecodeException e) {
            throw new RuntimeException("Error al obtener la fecha de expiración del token", e);
        }
    }
}
