package epita.tp.dto.flightDTO;

import java.time.LocalDateTime;

public record FlightRequestDTO(
        Long id,
        LocalDateTime dateTime,
        Long shuttleId
) {}
