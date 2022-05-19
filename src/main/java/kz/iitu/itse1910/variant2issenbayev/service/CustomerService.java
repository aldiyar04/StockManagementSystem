package kz.iitu.itse1910.variant2issenbayev.service;

import kz.iitu.itse1910.variant2issenbayev.dto.request.CustomerCreationReq;
import kz.iitu.itse1910.variant2issenbayev.dto.request.CustomerUpdateReq;
import kz.iitu.itse1910.variant2issenbayev.dto.response.CustomerResp;
import kz.iitu.itse1910.variant2issenbayev.entity.Customer;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordAlreadyExistsException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordNotFoundException;
import kz.iitu.itse1910.variant2issenbayev.exception.RecordUndeletableException;
import kz.iitu.itse1910.variant2issenbayev.mapper.CustomerMapper;
import kz.iitu.itse1910.variant2issenbayev.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerResp> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public CustomerResp getCustomerById(long id) {
        Customer customer = getByIdOrThrow(id);
        return CustomerMapper.INSTANCE.toDto(customer);
    }

    public CustomerResp createCustomer(CustomerCreationReq creationReq) {
        throwIfAlreadyTaken(creationReq.getCardNumber());
        Customer customer = CustomerMapper.INSTANCE.toEntity(creationReq);
        return saveCustomerAndMapToDto(customer);
    }

    public CustomerResp updateCustomer(long id, CustomerUpdateReq updateReq) {
        Customer customer = getByIdOrThrow(id);

        String newCardNumber = updateReq.getCardNumber();
        if (!customer.getCardNumber().equals(newCardNumber)) {
            throwIfAlreadyTaken(newCardNumber);
        }

        CustomerMapper.INSTANCE.updateEntityFromDto(customer, updateReq);
        return saveCustomerAndMapToDto(customer);
    }

    public void deleteCustomer(long id) {
        Customer customer = getByIdOrThrow(id);

        if (customer.hasAssociatedTransactions()) {
            throw new RecordUndeletableException("Customer " + customer.getFirstLastName() + " cannot be deleted " +
                    "since there are transactions associated with them");
        }

        customerRepository.delete(customer);
    }

    Customer getByIdOrThrow(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Customer with id " + id + " does not exist"));
    }

    private void throwIfAlreadyTaken(String cardNumber) {
        if (customerRepository.findByCardNumber(cardNumber).isPresent()) {
            throw new RecordAlreadyExistsException("Customer with card number " + cardNumber + " already exists");
        }
    }

    private CustomerResp saveCustomerAndMapToDto(Customer customer) {
        customer = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.toDto(customer);
    }
}
