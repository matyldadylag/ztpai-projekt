package pl.edu.pk.ztpai_projekt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pk.ztpai_projekt.dto.CustomerRequest;
import pl.edu.pk.ztpai_projekt.dto.CustomerResponse;
import pl.edu.pk.ztpai_projekt.mapper.CustomerMapper;
import pl.edu.pk.ztpai_projekt.model.Customer;
import pl.edu.pk.ztpai_projekt.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    private CustomerService service;

    @BeforeEach
    void setUp() {
        service = new CustomerService(repository, mapper);
    }

    @Test
    void createCustomer_shouldReturnCustomerResponse_whenRequestIsValid() {
        CustomerRequest request = request("Jan", "Kowalski", "jan.kowalski@example.com");

        Customer customerToSave = new Customer("Jan", "Kowalski", "jan.kowalski@example.com");

        Customer savedCustomer = new Customer("Jan", "Kowalski", "jan.kowalski@example.com");
        savedCustomer.setId(1L);

        CustomerResponse expectedResponse = new CustomerResponse(
                1L,
                "Jan",
                "Kowalski",
                "jan.kowalski@example.com"
        );

        when(repository.existsByEmailAddress("jan.kowalski@example.com")).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(customerToSave);
        when(repository.save(customerToSave)).thenReturn(savedCustomer);
        when(mapper.toResponse(savedCustomer)).thenReturn(expectedResponse);

        CustomerResponse result = service.createCustomer(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
        assertEquals("jan.kowalski@example.com", result.getEmailAddress());

        verify(repository).existsByEmailAddress("jan.kowalski@example.com");
        verify(mapper).toEntity(request);
        verify(repository).save(customerToSave);
        verify(mapper).toResponse(savedCustomer);
    }

    @Test
    void getCustomerById_shouldThrowException_whenCustomerDoesNotExist() {
        Long customerId = 99L;

        when(repository.findById(customerId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.getCustomerById(customerId)
        );

        assertEquals("Customer not found", exception.getMessage());

        verify(repository).findById(customerId);
        verifyNoInteractions(mapper);
    }

    @Test
    void createCustomer_shouldThrowException_whenEmailAlreadyExists() {
        CustomerRequest request = request("Jan", "Kowalski", "jan.kowalski@example.com");

        when(repository.existsByEmailAddress("jan.kowalski@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.createCustomer(request)
        );

        assertEquals("Klient z takim adresem email już istnieje", exception.getMessage());

        verify(repository).existsByEmailAddress("jan.kowalski@example.com");
        verify(repository, never()).save(any(Customer.class));
        verifyNoInteractions(mapper);
    }

    @Test
    void updateCustomer_shouldUpdateCustomer_whenCustomerExistsAndEmailIsNotChanged() {
        Long customerId = 1L;

        CustomerRequest request = request("Anna", "Nowak", "jan.kowalski@example.com");

        Customer existingCustomer = new Customer("Jan", "Kowalski", "jan.kowalski@example.com");
        existingCustomer.setId(customerId);

        Customer updatedCustomer = new Customer("Anna", "Nowak", "jan.kowalski@example.com");
        updatedCustomer.setId(customerId);

        CustomerResponse expectedResponse = new CustomerResponse(
                customerId,
                "Anna",
                "Nowak",
                "jan.kowalski@example.com"
        );

        when(repository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(repository.save(existingCustomer)).thenReturn(updatedCustomer);
        when(mapper.toResponse(updatedCustomer)).thenReturn(expectedResponse);

        CustomerResponse result = service.updateCustomer(customerId, request);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("Anna", result.getFirstName());
        assertEquals("Nowak", result.getLastName());
        assertEquals("jan.kowalski@example.com", result.getEmailAddress());

        verify(repository).findById(customerId);
        verify(repository, never()).existsByEmailAddress(anyString());
        verify(mapper).updateEntity(existingCustomer, request);
        verify(repository).save(existingCustomer);
        verify(mapper).toResponse(updatedCustomer);
    }

    @Test
    void updateCustomer_shouldThrowException_whenNewEmailAlreadyExists() {
        Long customerId = 1L;

        CustomerRequest request = request("Jan", "Kowalski", "anna.nowak@example.com");

        Customer existingCustomer = new Customer("Jan", "Kowalski", "jan.kowalski@example.com");
        existingCustomer.setId(customerId);

        when(repository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(repository.existsByEmailAddress("anna.nowak@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.updateCustomer(customerId, request)
        );

        assertEquals("Klient z takim adresem email już istnieje", exception.getMessage());

        verify(repository).findById(customerId);
        verify(repository).existsByEmailAddress("anna.nowak@example.com");
        verify(mapper, never()).updateEntity(any(Customer.class), any(CustomerRequest.class));
        verify(repository, never()).save(any(Customer.class));
    }

    private CustomerRequest request(String firstName, String lastName, String emailAddress) {
        CustomerRequest request = new CustomerRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmailAddress(emailAddress);
        return request;
    }
}