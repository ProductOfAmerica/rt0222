package com.rt0222.service.impl;

import com.rt0222.domain.model.Clerk;
import com.rt0222.domain.model.Customer;
import com.rt0222.domain.repository.ClerkRepository;
import com.rt0222.domain.repository.CustomerRepository;
import com.rt0222.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class UserServiceImpl implements UserService {
    private final CustomerRepository customerRepository;
    private final ClerkRepository clerkRepository;

    @Override
    public Customer createCustomer() {
        Customer customer = new Customer();
        customer.setCreatedAt(Timestamp.from(Instant.now()));
        customer = customerRepository.save(customer);
        return customer;
    }

    @Override
    public Customer getCustomer(Long id) {
        Customer customer = customerRepository.findCustomerById(id);
        if (customer == null) {
            throw new EmptyResultDataAccessException("Customer \"%s\" not found.".formatted(id), 1);
        }
        return customer;
    }

    @Override
    public Clerk createClerk() {
        Clerk clerk = new Clerk();
        clerk.setCreatedAt(Timestamp.from(Instant.now()));
        clerk = clerkRepository.save(clerk);
        return clerk;
    }

    @Override
    public Clerk getClerk(Long id) {
        Clerk Clerk = clerkRepository.findClerkById(id);
        if (Clerk == null) {
            throw new EmptyResultDataAccessException("Clerk \"%s\" not found.".formatted(id), 1);
        }
        return Clerk;
    }
}
