    package epita.tp.controller;

    import epita.tp.dto.shuttleDTO.ShuttleDTO;
    import epita.tp.dto.shuttleDTO.ShuttleStatusDTO;
    import epita.tp.service.ShuttleService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/v1/shuttles")
    public class ShuttleController {
        private final ShuttleService shuttleService;

        @Autowired
        public ShuttleController(ShuttleService shuttleService) {
            this.shuttleService = shuttleService;
        }

        @GetMapping
        public List<ShuttleDTO> getAllShuttles() {
            return shuttleService.getAllShuttles();
        }

        @GetMapping("/{id}")
        public ResponseEntity<ShuttleDTO> getShuttleById(@PathVariable Long id) {
            Optional<ShuttleDTO> shuttleDTO = shuttleService.getShuttleById(id);
            return shuttleDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<ShuttleDTO> createShuttle(@Valid @RequestBody ShuttleDTO shuttleDTO) {
            ShuttleDTO createdShuttle = shuttleService.createShuttle(shuttleDTO);
            return new ResponseEntity<>(createdShuttle, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ShuttleDTO> updateShuttle(@PathVariable Long id, @RequestBody ShuttleStatusDTO shuttleStatusDTO) {
            ShuttleDTO updatedShuttle = shuttleService.updateShuttle(id, shuttleStatusDTO);
            return ResponseEntity.ok(updatedShuttle);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteShuttle(@PathVariable Long id) {
            shuttleService.deleteShuttle(id);
            return ResponseEntity.noContent().build();
        }
    }
