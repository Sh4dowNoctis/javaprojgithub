package epita.tp.dto.revisionDTO;

import java.time.LocalDate;

public record RevisionDTO(
        Long id,
        LocalDate date,
        Long shuttleId
) {}