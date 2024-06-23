package epita.tp.service;

import epita.tp.dto.FlightDTO;
import epita.tp.model.Flight;
import epita.tp.model.Shuttle;
import epita.tp.repository.FlightRepository;
import epita.tp.repository.ShuttleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final ShuttleRepository shuttleRepository;

    public FlightService(FlightRepository flightRepository, ShuttleRepository shuttleRepository) {
        this.flightRepository = flightRepository;
        this.shuttleRepository = shuttleRepository;
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<FlightDTO> getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(this::convertToDTO);
    }

    public FlightDTO createFlight(FlightDTO flightDTO) {
        Flight flight = convertToEntity(flightDTO);
        flight = flightRepository.save(flight);
        return convertToDTO(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        flight.setDateTime(flightDTO.getDateTime());
        flight.setStatus(flightDTO.getStatus());
        Shuttle shuttle = shuttleRepository.findById(flightDTO.getShuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        flight.setShuttle(shuttle);
        flight = flightRepository.save(flight);
        return convertToDTO(flight);
    }

    private FlightDTO convertToDTO(Flight flight) {
        return new FlightDTO(flight.getId(), flight.getDateTime(), flight.getStatus(), flight.getShuttle().getId());
    }

    private Flight convertToEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setDateTime(flightDTO.getDateTime());
        flight.setStatus(flightDTO.getStatus());
        Shuttle shuttle = shuttleRepository.findById(flightDTO.getShuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        flight.setShuttle(shuttle);
        return flight;
    }
}
