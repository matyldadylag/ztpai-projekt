package pl.edu.pk.ztpai_projekt.mapper;

import org.springframework.stereotype.Component;
import pl.edu.pk.ztpai_projekt.dto.CustomerRequest;
import pl.edu.pk.ztpai_projekt.dto.CustomerResponse;
import pl.edu.pk.ztpai_projekt.model.Customer;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {
        return new Customer(
                request.getFirstName(),
                request.getLastName(),
                request.getEmailAddress()
        );
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmailAddress()
        );
    }

    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmailAddress(request.getEmailAddress());
    }
}