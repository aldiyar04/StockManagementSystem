package kz.iitu.itse1910.variant2issenbayev;

import kz.iitu.itse1910.variant2issenbayev.entity.User;
import kz.iitu.itse1910.variant2issenbayev.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Variant2issenbayevApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Variant2issenbayevApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		User admin = User.builder()
				.role(User.Role.ADMIN)
				.email("admin@stock.kz")
				.username("admin")
				.password("1234567890123456789012345678")
				.firstName("Bakhyt")
				.lastName("Bakhytzhhanovich")
				.build();
		userRepository.save(admin);
	}

}
