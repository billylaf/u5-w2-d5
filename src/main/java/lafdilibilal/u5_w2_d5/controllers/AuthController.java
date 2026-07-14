package lafdilibilal.u5_w2_d5.controllers;

import lafdilibilal.u5_w2_d5.DTO.DipendenteDTO;
import lafdilibilal.u5_w2_d5.DTO.LoginDTO;
import lafdilibilal.u5_w2_d5.DTO.LoginReponseDTO;
import lafdilibilal.u5_w2_d5.entities.Dipendente;
import lafdilibilal.u5_w2_d5.exceptions.ValidationException;
import lafdilibilal.u5_w2_d5.services.AuthService;
import lafdilibilal.u5_w2_d5.services.DipendenteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final DipendenteService dipendenteService;

    public AuthController(AuthService authService, DipendenteService dipendenteService) {
        this.authService = authService;
        this.dipendenteService = dipendenteService;
    }

    @PostMapping("/login")
    public LoginReponseDTO login(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }
        String token = this.authService.checkCredentialsAndGenerateToken(body);
        return new LoginReponseDTO(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente register(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }
        return this.dipendenteService.save(body);
    }
}