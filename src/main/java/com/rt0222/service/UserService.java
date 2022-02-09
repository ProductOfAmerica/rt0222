package com.rt0222.service;

import com.rt0222.domain.model.Clerk;
import com.rt0222.domain.model.Customer;

public interface UserService {
    Customer createCustomer();

    Customer getCustomer(Long id);

    Clerk createClerk();

    Clerk getClerk(Long id);
}
