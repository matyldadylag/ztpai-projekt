package pl.edu.pk.ztpai_projekt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.pk.ztpai_projekt.model.Customer;
import pl.edu.pk.ztpai_projekt.repository.CustomerRepository;

@SpringBootApplication
public class ZtpaiProjektApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZtpaiProjektApplication.class, args);
	}

	@Bean
	CommandLineRunner testDatabase(CustomerRepository customerRepository) {
		return args -> {
			Customer customer = new Customer("John");
			customerRepository.save(customer);

			customerRepository.findAll()
					.forEach(c -> System.out.println(c));
		};
	}
}
