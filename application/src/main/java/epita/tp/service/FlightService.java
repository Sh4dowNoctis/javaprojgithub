package epita.tp.service;

import epita.tp.dto.flightDTO.FlightRequestDTO;
import epita.tp.dto.flightDTO.FlightResponseDTO;
import epita.tp.model.Flight;
import epita.tp.model.Shuttle;
import epita.tp.repository.FlightRepository;
import epita.tp.repository.ShuttleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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

    public List<FlightResponseDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<FlightResponseDTO> getFlightById(Long id) {
        return flightRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    @Transactional
    public FlightResponseDTO createFlight(FlightRequestDTO flightRequestDTO) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());

        int flightCount = flightRepository.countFlightsInMonth(startOfMonth, endOfMonth);
        if (flightCount >= 1) {
            throw new RuntimeException("Cannot schedule more than one flight per month.");
        }

        Shuttle shuttle = shuttleRepository.findById(flightRequestDTO.shuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));

        Flight flight = new Flight();
        flight.setDateTime(flightRequestDTO.dateTime());
        flight.setShuttle(shuttle);
        flight.setStatus(determineStatus(flight));

        Flight savedFlight = flightRepository.save(flight);
        return convertToResponseDTO(savedFlight);
    }

    @Transactional
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flightRepository.delete(flight);
        notifyUsers(flight, "Flight has been canceled.");
    }

    @Transactional
    public FlightResponseDTO updateFlight(Long id, FlightRequestDTO flightRequestDTO) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setDateTime(flightRequestDTO.dateTime());
        flight.setShuttle(shuttleRepository.findById(flightRequestDTO.shuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found")));
        flight.setStatus(determineStatus(flight));

        Flight updatedFlight = flightRepository.save(flight);
        return convertToResponseDTO(updatedFlight);
    }

    @Transactional
    public FlightResponseDTO bookFlight(String travelerEmail, Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getDateTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Cannot book a past flight.");
        }

        if (flight.getTravelers().contains(travelerEmail)) {
            throw new RuntimeException("You are already booked on this flight.");
        }

        if (flight.getTravelers().size() >= flight.getShuttle().getCapacity()) {
            throw new RuntimeException("No available seats on this flight.");
        }

        flight.getTravelers().add(travelerEmail);
        Flight updatedFlight = flightRepository.save(flight);
        return convertToResponseDTO(updatedFlight);
    }

    public List<FlightResponseDTO> getFutureFlightsWithRemainingSeats() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> futureFlights = flightRepository.findByDateTimeAfter(now);

        return futureFlights.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private String determineStatus(Flight flight) {
        LocalDateTime now = LocalDateTime.now();
        if (flight.getDateTime().isBefore(now)) {
            return "PASSED";
        }

        List<Flight> shuttleFlights = flightRepository.findByShuttleId(flight.getShuttle().getId());
        boolean needsGearCheck = shuttleFlights.stream()
                .anyMatch(f -> f.getDateTime().isAfter(now.minusMonths(1)));

        if (needsGearCheck) {
            return "WAITING_FOR_GEARCHECK";
        }

        return "OK";
    }

    private void notifyUsers(Flight flight, String message) {
        System.out.println("Notifying users of flight ID " + flight.getId() + ": " + message);
    }

    private FlightResponseDTO convertToResponseDTO(Flight flight) {
        int remainingSeats = flight.getShuttle().getCapacity() - flight.getTravelers().size();
        return new FlightResponseDTO(flight.getId(), flight.getDateTime(), flight.getShuttle().getId(), flight.getStatus(), flight.getTravelers(), remainingSeats);
    }

    private Flight convertToEntity(FlightRequestDTO flightRequestDTO) {
        Flight flight = new Flight();
        flight.setId(flightRequestDTO.id());
        flight.setDateTime(flightRequestDTO.dateTime());
        Shuttle shuttle = shuttleRepository.findById(flightRequestDTO.shuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        flight.setShuttle(shuttle);
        return flight;
    }
}
