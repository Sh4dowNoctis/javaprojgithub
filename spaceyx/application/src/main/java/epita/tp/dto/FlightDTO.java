package epita.tp.dto;

import epita.tp.model.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlightDTO {
    private Long id;
    private LocalDateTime dateTime;
    private Flight.FlightStatus status;
    private long shuttleId;
}

