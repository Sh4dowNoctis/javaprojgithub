package epita.tp.service;

import epita.tp.dto.RevisionDTO;
import epita.tp.model.Revision;
import epita.tp.model.Shuttle;
import epita.tp.repository.RevisionRepository;
import epita.tp.repository.ShuttleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RevisionService {
    private final RevisionRepository revisionRepository;
    private final ShuttleRepository shuttleRepository;

    public RevisionService(RevisionRepository revisionRepository, ShuttleRepository shuttleRepository) {
        this.revisionRepository = revisionRepository;
        this.shuttleRepository = shuttleRepository;
    }

    public List<RevisionDTO> getAllRevisions() {
        return revisionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RevisionDTO> getRevisionById(Long id) {
        return revisionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public RevisionDTO createRevision(RevisionDTO revisionDTO) {
        Revision revision = convertToEntity(revisionDTO);
        revision = revisionRepository.save(revision);
        return convertToDTO(revision);
    }

    public void deleteRevision(Long id) {
        revisionRepository.deleteById(id);
    }

    public RevisionDTO updateRevision(Long id, RevisionDTO revisionDTO) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Revision not found"));
        revision.setDate(revisionDTO.getDate());
        Shuttle shuttle = shuttleRepository.findById(revisionDTO.getShuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        revision.setShuttle(shuttle);
        revision = revisionRepository.save(revision);
        return convertToDTO(revision);
    }

    private RevisionDTO convertToDTO(Revision revision) {
        return new RevisionDTO(revision.getId(), revision.getDate(), revision.getShuttle().getId());
    }

    private Revision convertToEntity(RevisionDTO revisionDTO) {
        Revision revision = new Revision();
        revision.setId(revisionDTO.getId());
        revision.setDate(revisionDTO.getDate());
        Shuttle shuttle = shuttleRepository.findById(revisionDTO.getShuttleId())
                .orElseThrow(() -> new RuntimeException("Shuttle not found"));
        revision.setShuttle(shuttle);
        return revision;
    }
}
