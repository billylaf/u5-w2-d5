package lafdilibilal.u5_w2_d5.controllers;

import lafdilibilal.u5_w2_d5.DTO.PrenotazioneDTO;
import lafdilibilal.u5_w2_d5.entities.Prenotazione;
import lafdilibilal.u5_w2_d5.exceptions.ValidationException;
import lafdilibilal.u5_w2_d5.services.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @GetMapping
    public List<Prenotazione> findAll() {
        return this.prenotazioneService.findAll();
    }

    @GetMapping("/dipendente/{username}")
    public List<Prenotazione> findByDipendente(@PathVariable String username) {
        return this.prenotazioneService.findByDipendenteUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione create(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }
        return this.prenotazioneService.save(body);
    }

    @GetMapping("/{id}")
    public Prenotazione findById(@PathVariable Long id) {
        return this.prenotazioneService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.prenotazioneService.findByIdAndDelete(id);
    }
}
