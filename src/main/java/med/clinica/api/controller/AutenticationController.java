package med.clinica.api.controller;

import jakarta.validation.Valid;
import med.clinica.api.domain.users.DatosAutenticacionUsuario;
import med.clinica.api.domain.users.Usuario;
import med.clinica.api.infra.security.DatosJWTtoken;
import med.clinica.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AutenticationController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication authenticationTokem = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authenticationTokem);

        String jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTtoken(jwtToken));
    }

}
