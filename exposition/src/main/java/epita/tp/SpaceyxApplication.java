package epita.tp;

import epita.tp.common.RoleConstants;
import epita.tp.dto.appuserDTO.AppUserDTO;
import epita.tp.service.AppUserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import java.util.stream.Stream;

@SecurityScheme(name = "auth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication(scanBasePackages = {"epita.tp"}, exclude = {ErrorMvcAutoConfiguration.class})
public class SpaceyxApplication implements CommandLineRunner {
	@Autowired
	private AppUserService appUserService;

	public static void main(String[] args) {
		SpringApplication.run(SpaceyxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Stream.of(
				new AppUserDTO(null, "mrbricolage@spacyx.com", "33raptor", RoleConstants.TECHNICIEN_ROLE),
				new AppUserDTO(null, "voyagevoyage@spaceyx.com", "ihaveaplan", RoleConstants.PLANIFICATEUR_ROLE),
				new AppUserDTO(null, "romain@mail.com", "çavaaller", RoleConstants.VOYAGEUR_ROLE)
				).forEach(appUserService::createUser);

	}
}
