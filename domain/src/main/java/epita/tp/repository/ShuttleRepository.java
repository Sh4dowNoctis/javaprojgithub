package epita.tp.repository;

import epita.tp.model.Shuttle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShuttleRepository extends JpaRepository<Shuttle, Long> {
}
