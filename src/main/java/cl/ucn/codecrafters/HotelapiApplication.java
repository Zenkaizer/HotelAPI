package cl.ucn.codecrafters;

import cl.ucn.codecrafters.user.domain.User;
import cl.ucn.codecrafters.user.application.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@SpringBootApplication
public class HotelapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelapiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserService userService){

		return args -> {

			var admin = User.builder()
					.dni("207236683")
					.email("marcelo.cespedes@alumnos.ucn.cl")
					.password("password")
					.firstName("Marcelo")
					.lastName("Cespedes")
					.phone("977404965")
					.nationality("Chile")
					.birthDate(new Date())
					.build();

			userService.saveAdmin(admin);

			var administrative = User.builder()
					.dni("204166994")
					.email("jorge.rivera01@alumnos.ucn.cl")
					.password("password")
					.firstName("Jorge")
					.lastName("Rivera")
					.phone("123456789")
					.nationality("Chile")
					.birthDate(new Date())
					.build();

			userService.saveAdministrative(administrative);

			var client = User.builder()
					.dni("207676918")
					.email("david.araya@alumnos.ucn.cl")
					.password("password")
					.firstName("David")
					.lastName("Araya")
					.phone("987654321")
					.nationality("Chile")
					.birthDate(new Date())
					.build();

			userService.saveClient(client);

		};

	}

}
