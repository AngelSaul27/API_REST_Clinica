package med.clinica.api.controller;

import jakarta.validation.Valid;
import med.clinica.api.medico.DatosListadoMedico;
import med.clinica.api.medico.DatosRegistroMedico;
import med.clinica.api.medico.Medico;
import med.clinica.api.medico.MedicoRepository;
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

    /** @GetMapping
    public List<DatosListadoMedico> listarMedico(){
        return repository.findAll().stream().map(DatosListadoMedico::new).toList();
    }*/

    @GetMapping
    public Page<DatosListadoMedico> listadoMedico(Pageable paginacion){
        return repository.findAll(paginacion).map(DatosListadoMedico::new);
    }
}
