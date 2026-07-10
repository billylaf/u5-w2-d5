package lafdilibilal.u5_w2_d5.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class ViaggioDTO {

    @NotBlank(message = "Destinazione è obbligatoria")
    private String destinazione;

    @NotNull(message = "Data è obbligatoria")
    @Future(message = "La data deve essere futura")
    private LocalDate data;

    private String stato;
}