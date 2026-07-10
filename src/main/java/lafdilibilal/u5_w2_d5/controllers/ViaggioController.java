package lafdilibilal.u5_w2_d5.controllers;

import lafdilibilal.u5_w2_d5.DTO.StatoViaggioDTO;
import lafdilibilal.u5_w2_d5.DTO.ViaggioDTO;
import lafdilibilal.u5_w2_d5.entities.Viaggio;
import lafdilibilal.u5_w2_d5.exceptions.ValidationException;
import lafdilibilal.u5_w2_d5.services.ViaggioService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    private final ViaggioService viaggioService;

    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    @GetMapping
    public List<Viaggio> findAll() {
        return this.viaggioService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio create(@RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }
        return this.viaggioService.save(body);
    }

    @GetMapping("/{id}")
    public Viaggio findById(@PathVariable Long id) {
        return this.viaggioService.findById(id);
    }

    @PutMapping("/{id}")
    public Viaggio update(@PathVariable Long id, @RequestBody @Validated ViaggioDTO body) {
        return this.viaggioService.findByIdAndUpdate(id, body);
    }

    @PatchMapping("/{id}/stato")
    public Viaggio updateStato(@PathVariable Long id, @RequestBody @Validated StatoViaggioDTO body) {
        return this.viaggioService.updateStato(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.viaggioService.findByIdAndDelete(id);
    }
}