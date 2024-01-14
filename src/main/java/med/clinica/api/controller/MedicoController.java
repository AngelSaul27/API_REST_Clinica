package med.clinica.api.controller;

import med.clinica.api.medico.DatosRegistroMedico;
import med.clinica.api.medico.Medico;
import med.clinica.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    public void registrarMedico(@RequestBody DatosRegistroMedico datosRegistroMedico){
        repository.save(new Medico(datosRegistroMedico));
    }

}
