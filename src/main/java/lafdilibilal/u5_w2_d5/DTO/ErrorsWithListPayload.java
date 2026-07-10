package lafdilibilal.u5_w2_d5.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorsWithListPayload {
    private String message;
    private LocalDateTime timestamp;
    private List<String> errorsList;
}
