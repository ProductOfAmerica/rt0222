package com.rt0222.controller;

import com.rt0222.domain.model.Clerk;
import com.rt0222.domain.model.Customer;
import com.rt0222.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/api/user")
public class CustomerServiceController {
    private final UserService userService;

    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable("id") Long id) {
        return userService.getCustomer(id);
    }

    @PostMapping("/customer/")
    public Customer createCustomer() {
        return userService.createCustomer();
    }

    @GetMapping("/clerk/{id}")
    public Clerk getClerk(@PathVariable("id") Long id) {
        return userService.getClerk(id);
    }

    @PostMapping("/clerk/")
    public Clerk createClerk() {
        return userService.createClerk();
    }
}
