package epita.tp.dto.shuttleDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ShuttleDTO(
    Long id,
    String name,
    @Min(value = 3, message = "Capacity must be at least 3")
    @Max(value = 5, message = "Capacity must be at most 5")
    Integer capacity,
    String status
){}
