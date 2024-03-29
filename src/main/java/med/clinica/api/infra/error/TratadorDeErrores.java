package med.clinica.api.infra.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SuppressWarnings("all")
@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    public record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
