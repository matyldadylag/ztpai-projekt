package pl.edu.pk.ztpai_projekt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pk.ztpai_projekt.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmailAddress(String emailAddress);
}