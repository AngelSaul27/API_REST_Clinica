package med.clinica.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.clinica.api.domain.consulta.DatosRegistroConsulta;
import med.clinica.api.infra.exceptions.ValidacionDeIntegridad;
import med.clinica.api.services.AgendarConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final AgendarConsultaService service;

    @Autowired
    public ConsultaController(AgendarConsultaService service){
        this.service = service;
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosRegistroConsulta datosAgendarConsulta) throws ValidacionDeIntegridad {
        var response = service.agendar(datosAgendarConsulta);
        return ResponseEntity.ok(response);
    }

}
