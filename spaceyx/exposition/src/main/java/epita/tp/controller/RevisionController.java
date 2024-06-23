package epita.tp.controller;

import epita.tp.dto.RevisionDTO;
import epita.tp.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/revisions")
public class RevisionController {
    private final RevisionService revisionService;

    @Autowired
    public RevisionController(RevisionService revisionService) {
        this.revisionService = revisionService;
    }

    @GetMapping
    public List<RevisionDTO> getAllRevisions() {
        return revisionService.getAllRevisions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevisionDTO> getRevisionById(@PathVariable Long id) {
        Optional<RevisionDTO> revisionDTO = revisionService.getRevisionById(id);
        return revisionDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RevisionDTO> createRevision(@RequestBody RevisionDTO revisionDTO) {
        RevisionDTO createdRevision = revisionService.createRevision(revisionDTO);
        return new ResponseEntity<>(createdRevision, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevisionDTO> updateRevision(@PathVariable Long id, @RequestBody RevisionDTO revisionDTO) {
        RevisionDTO updatedRevision = revisionService.updateRevision(id, revisionDTO);
        return ResponseEntity.ok(updatedRevision);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevision(@PathVariable Long id) {
        revisionService.deleteRevision(id);
        return ResponseEntity.noContent().build();
    }
}
