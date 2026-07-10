package lafdilibilal.u5_w2_d5.controllers;

import lafdilibilal.u5_w2_d5.DTO.DipendenteDTO;
import lafdilibilal.u5_w2_d5.entities.Dipendente;
import lafdilibilal.u5_w2_d5.exceptions.ValidationException;
import lafdilibilal.u5_w2_d5.services.DipendenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    private final DipendenteService dipendenteService;

    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    @GetMapping
    public List<Dipendente> findAll() {
        return this.dipendenteService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente create(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }
        return this.dipendenteService.save(body);
    }

    @GetMapping("/{username}")
    public Dipendente findById(@PathVariable String username) {
        return this.dipendenteService.findById(username);
    }

    @PutMapping("/{username}")
    public Dipendente update(@PathVariable String username, @RequestBody @Validated DipendenteDTO body) {
        return this.dipendenteService.findByIdAndUpdate(username, body);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String username) {
        this.dipendenteService.findByIdAndDelete(username);
    }

    @PatchMapping(value = "/{username}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Dipendente uploadAvatar(@PathVariable String username, @RequestParam("avatar") MultipartFile file) {
        return this.dipendenteService.uploadAvatar(username, file);
    }
}