package med.clinica.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.clinica.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private final MedicoRepository repository;

    @Autowired
    public MedicoController(MedicoRepository repository){
        this.repository = repository;
    }


    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        repository.save(new Medico(datosRegistroMedico));
    }

    /**
    @GetMapping
    public List<DatosListadoMedico> listarMedico(){
        return repository.findAll().stream().map(DatosListadoMedico::new).toList();
    }*/

    @GetMapping
    public Page<DatosListadoMedico> listadoMedico(Pageable paginacion){
        return repository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
    }

    @PutMapping
    @Transactional
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = repository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }

    /**
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        repository.delete(medico);
    }*/

    //Deleted Logic
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        medico.desahbilitar();
    }

}
