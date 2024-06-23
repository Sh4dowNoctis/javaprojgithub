package epita.tp.dto;

import epita.tp.model.Shuttle;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShuttleDTO {
    private Long id;
    private String name;
    private Integer capacity;
    private Shuttle.ShuttleStatus status;
}
