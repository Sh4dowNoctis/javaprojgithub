package epita.tp.service;

import epita.tp.dto.shuttleDTO.ShuttleDTO;
import epita.tp.dto.shuttleDTO.ShuttleStatusDTO;
import epita.tp.model.Flight;
import epita.tp.model.Shuttle;
import epita.tp.repository.FlightRepository;
import epita.tp.repository.ShuttleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShuttleService {
    private final ShuttleRepository shuttleRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public ShuttleService(ShuttleRepository shuttleRepository, FlightRepository flightRepository) {
        this.shuttleRepository = shuttleRepository;
        this.flightRepository = flightRepository;
    }

    public List<ShuttleDTO> getAllShuttles() {
        return shuttleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ShuttleDTO> getShuttleById(Long id) {
        return shuttleRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public ShuttleDTO createShuttle(ShuttleDTO shuttleDTO) {
        Shuttle shuttle = convertToEntity(shuttleDTO);
        shuttle = shuttleRepository.save(shuttle);
        return convertToDTO(shuttle);
    }

    @Transactional
    public void deleteShuttle(Long id) {
        shuttleRepository.deleteById(id);
    }

    @Transactional
    public ShuttleDTO updateShuttle(Long id, ShuttleStatusDTO shuttleStatusDTO) {
        Shuttle shuttle = shuttleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        if (!shuttle.getStatus().equals(shuttleStatusDTO.status())) {
            if (shuttleStatusDTO.status().equals("OBSOLETE")) {
                cancelFlightsForShuttle(shuttle);
            }
            shuttle.setStatus(shuttleStatusDTO.status());
        }

        Shuttle updatedShuttle = shuttleRepository.save(shuttle);
        return convertToDTO(updatedShuttle);
    }

    private void cancelFlightsForShuttle(Shuttle shuttle) {
        List<Flight> flights = flightRepository.findByShuttleId(shuttle.getId());
        for (Flight flight : flights) {
            notifyUsers(flight, "Flight canceled due to shuttle being marked as OBSOLETE.");
            flightRepository.delete(flight);
        }
    }

    private void notifyUsers(Flight flight, String message) {
        System.out.println("Notifying users of flight ID " + flight.getId() + ": " + message);
    }
    private ShuttleDTO  convertToDTO(Shuttle shuttle) {
        return new ShuttleDTO(shuttle.getId(), shuttle.getName(), shuttle.getCapacity(), shuttle.getStatus());
    }

    private Shuttle convertToEntity(ShuttleDTO shuttleDTO) {
        Shuttle shuttle = new Shuttle();
        shuttle.setId(shuttleDTO.id());
        shuttle.setName(shuttleDTO.name());
        shuttle.setCapacity(shuttleDTO.capacity());
        shuttle.setStatus(shuttleDTO.status());
        return shuttle;
    }

}
