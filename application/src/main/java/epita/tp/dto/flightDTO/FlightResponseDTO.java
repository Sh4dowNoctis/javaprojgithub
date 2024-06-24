package epita.tp.dto.flightDTO;

import java.time.LocalDateTime;
import java.util.List;

public record FlightResponseDTO(
        Long id,
        LocalDateTime dateTime,
        Long shuttleId,
        String status,
        List<String> travelers,
        int remainingSeats
) {}