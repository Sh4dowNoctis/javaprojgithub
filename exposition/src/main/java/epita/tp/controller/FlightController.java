package epita.tp.controller;

import epita.tp.dto.flightDTO.FlightRequestDTO;
import epita.tp.dto.flightDTO.FlightResponseDTO;
import epita.tp.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightResponseDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> getFlightById(@PathVariable Long id) {
        Optional<FlightResponseDTO> flightDTO = flightService.getFlightById(id);
        return flightDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FlightResponseDTO> createFlight(@RequestBody FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO createdFlight = flightService.createFlight(flightRequestDTO);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDTO> updateFlight(@PathVariable Long id, @RequestBody FlightRequestDTO flightRequestDTO) {
        FlightResponseDTO updatedFlight = flightService.updateFlight(id, flightRequestDTO);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book/{flightId}")
    public ResponseEntity<FlightResponseDTO> bookFlight(Authentication authentication, @PathVariable Long flightId) {
        String travelerEmail = authentication.getName();
        FlightResponseDTO bookedFlight = flightService.bookFlight(travelerEmail, flightId);
        return new ResponseEntity<>(bookedFlight, HttpStatus.CREATED);
    }

    @GetMapping("/future")
    public ResponseEntity<List<FlightResponseDTO>> getFutureFlightsWithRemainingSeats() {
        List<FlightResponseDTO> futureFlights = flightService.getFutureFlightsWithRemainingSeats();
        return ResponseEntity.ok(futureFlights);
    }
}