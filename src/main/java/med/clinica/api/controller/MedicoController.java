package med.clinica.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.clinica.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@SuppressWarnings("all")
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository repository;

    @Autowired
    public MedicoController(MedicoRepository repository){
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = repository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico respuestaMedico = new DatosRespuestaMedico(medico);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(respuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(Pageable paginacion){
        return ResponseEntity.ok(repository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> obtenerMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico);
        return ResponseEntity.ok(datosRespuestaMedico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = repository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico)); //Evitar enviar la entidad
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        medico.desahbilitar();

        return ResponseEntity.noContent().build();
    }
}
