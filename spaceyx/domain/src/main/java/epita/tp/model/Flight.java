package epita.tp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "shuttle_id")
    private Shuttle shuttle;
    private FlightStatus status;
    public enum FlightStatus {
        PASSED,
        OK,
        WAITING_FOR_GEARCHECK,
    }
}
