package lafdilibilal.u5_w2_d5.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "email e obbligatoria")
        @Email(message = "email deve essere in un formato valido")
        String email,

        @NotBlank(message = "la password e obbligatoria")
        String password
) {
}
