package lafdilibilal.u5_w2_d5.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class StatoViaggioDTO {

    @NotBlank(message = "Stato è obbligatorio")
    private String stato;
}
