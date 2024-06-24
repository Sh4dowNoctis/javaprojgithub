package epita.tp.dto.appuserDTO;

public record AppUserDTO(
        Long id,
        String username,
        String password,
        String role
) {}