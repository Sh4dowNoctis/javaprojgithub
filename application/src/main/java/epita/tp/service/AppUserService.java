package epita.tp.service;

import epita.tp.dto.appuserDTO.AppUserDTO;
import epita.tp.model.AppUser;
import epita.tp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AppUserDTO> getAllUsers() {
        return appUserRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AppUserDTO> getUserById(Long id) {
        return appUserRepository.findById(id)
                .map(this::convertToDTO);
    }

    public AppUserDTO createUser(AppUserDTO appUserDTO) {
        AppUser appUser = convertToEntity(appUserDTO);
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser = appUserRepository.save(appUser);
        return convertToDTO(appUser);
    }

    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }

    public AppUserDTO updateUser(Long id, AppUserDTO appUserDTO) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        appUser.setUsername(appUserDTO.username());
        appUser.setPassword(appUserDTO.password());
        appUser.setRole(appUserDTO.role());
        appUser = appUserRepository.save(appUser);
        return convertToDTO(appUser);
    }

    private AppUserDTO convertToDTO(AppUser appUser) {
        return new AppUserDTO(appUser.getId(), appUser.getUsername(), appUser.getPassword(), appUser.getRole());
    }

    private AppUser convertToEntity(AppUserDTO appUserDTO) {
        AppUser appUser = new AppUser();
        appUser.setId(appUserDTO.id());
        appUser.setUsername(appUserDTO.username());
        appUser.setPassword(appUserDTO.password());
        appUser.setRole(appUserDTO.role());
        return appUser;
    }
}
