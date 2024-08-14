package dev.pichborith.ecommerce.customer;

import dev.pichborith.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        String id = request.id();
        var customer = repository.findById(id).orElseThrow(
            () -> new CustomerNotFoundException(String.format(
                "Cannot update customer:: No customer found with the provided ID:: %s",
                id)));

        mergeCustomer(customer, request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (!request.firstname().isBlank()) {
            customer.setFirstname(request.firstname());
        }

        if (!request.lastname().isBlank()) {
            customer.setLastname(request.lastname());
        }

        if (!request.email().isBlank()) {
            customer.setEmail(request.email());
        }

        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream().map(mapper::fromCustomer).collect(
            Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId).map(mapper::fromCustomer)
                         .orElseThrow(() -> new CustomerNotFoundException(
                             String.format(
                                 "No customer found with the provided ID:: %s",
                                 customerId)));
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
