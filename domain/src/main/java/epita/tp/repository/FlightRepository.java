package epita.tp.repository;

import epita.tp.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT COUNT(f) FROM Flight f WHERE f.dateTime BETWEEN :startOfMonth AND :endOfMonth")
    int countFlightsInMonth(@Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    List<Flight> findByShuttleId(Long shuttleId);

    List<Flight> findByDateTimeBefore(LocalDateTime dateTime);

    List<Flight> findByDateTimeAfter(LocalDateTime dateTime);

    List<Flight> findByTravelersContaining(String travelerEmail);
}
