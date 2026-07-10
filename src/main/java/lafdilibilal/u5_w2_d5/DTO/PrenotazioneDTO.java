package lafdilibilal.u5_w2_d5.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class PrenotazioneDTO {

    @NotNull(message = "Username del dipendente è obbligatorio")
    private String dipendenteUsername;

    @NotNull(message = "ID del viaggio è obbligatorio")  // ← Usa @NotNull, NON @NotBlank
    private Long viaggioId;

    @NotNull(message = "Data è obbligatoria")
    @Future(message = "La data deve essere futura")
    private LocalDate data;

    private String note;
}