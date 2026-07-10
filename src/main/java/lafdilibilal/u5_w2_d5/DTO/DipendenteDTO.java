package lafdilibilal.u5_w2_d5.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@ToString
public class DipendenteDTO {

    @NotBlank(message = "Username è obbligatorio")
    @Size(min = 3, max = 20, message = "Username deve avere tra 3 e 20 caratteri")
    private String username;

    @NotBlank(message = "Nome è obbligatorio")
    @Size(min = 2, max = 40, message = "Nome deve avere tra 2 e 40 caratteri")
    private String nome;

    @NotBlank(message = "Cognome è obbligatorio")
    @Size(min = 2, max = 40, message = "Cognome deve avere tra 2 e 40 caratteri")
    private String cognome;

    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Email deve essere in un formato valido")
    private String email;

    private MultipartFile avatar;
}