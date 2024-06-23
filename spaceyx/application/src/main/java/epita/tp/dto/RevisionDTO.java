package epita.tp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RevisionDTO {
    private Long id;
    private LocalDate date;
    private Long shuttleId;
}
