package pl.edu.pk.ztpai_projekt.service;

import org.springframework.stereotype.Service;
import pl.edu.pk.ztpai_projekt.dto.CustomerRequest;
import pl.edu.pk.ztpai_projekt.dto.CustomerResponse;
import pl.edu.pk.ztpai_projekt.mapper.CustomerMapper;
import pl.edu.pk.ztpai_projekt.model.Customer;
import pl.edu.pk.ztpai_projekt.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        if (repository.existsByEmailAddress(request.getEmailAddress())) {
            throw new IllegalArgumentException("Klient z takim adresem email już istnieje");
        }

        Customer customer = mapper.toEntity(request);
        Customer savedCustomer = repository.save(customer);
        return mapper.toResponse(savedCustomer);
    }

    public List<CustomerResponse> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return mapper.toResponse(customer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getEmailAddress().equals(request.getEmailAddress())
                && repository.existsByEmailAddress(request.getEmailAddress())) {
            throw new IllegalArgumentException("Klient z takim adresem email już istnieje");
        }

        mapper.updateEntity(customer, request);

        Customer updatedCustomer = repository.save(customer);
        return mapper.toResponse(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}