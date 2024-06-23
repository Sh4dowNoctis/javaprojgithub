package epita.tp.service;

import epita.tp.dto.ShuttleDTO;
import epita.tp.model.Shuttle;
import epita.tp.repository.ShuttleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShuttleService {
    private final ShuttleRepository shuttleRepository;

    public ShuttleService(ShuttleRepository shuttleRepository) {
        this.shuttleRepository = shuttleRepository;
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

    public ShuttleDTO createShuttle(ShuttleDTO shuttleDTO) {
        Shuttle shuttle = convertToEntity(shuttleDTO);
        shuttle = shuttleRepository.save(shuttle);
        return convertToDTO(shuttle);
    }

    public void deleteShuttle(Long id) {
        shuttleRepository.deleteById(id);
    }

    public ShuttleDTO updateShuttle(Long id, ShuttleDTO shuttleDTO) {
        Shuttle shuttle = shuttleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        shuttle.setName(shuttleDTO.getName());
        shuttle.setCapacity(shuttleDTO.getCapacity());
        shuttle.setStatus(shuttleDTO.getStatus());
        shuttle = shuttleRepository.save(shuttle);
        return convertToDTO(shuttle);
    }

    private ShuttleDTO convertToDTO(Shuttle shuttle) {
        return new ShuttleDTO(shuttle.getId(), shuttle.getName(), shuttle.getCapacity(), shuttle.getStatus());
    }

    private Shuttle convertToEntity(ShuttleDTO shuttleDTO) {
        Shuttle shuttle = new Shuttle();
        shuttle.setId(shuttleDTO.getId());
        shuttle.setName(shuttleDTO.getName());
        shuttle.setCapacity(shuttleDTO.getCapacity());
        shuttle.setStatus(shuttleDTO.getStatus());
        return shuttle;
    }

}
