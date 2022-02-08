package com.rt0222.controller;

import com.rt0222.domain.model.RentalAgreement;
import com.rt0222.domain.model.request.RentalAgreementDTO;
import com.rt0222.service.CheckoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final Logger logger = LoggerFactory.getLogger(CheckoutController.class);

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/create")
    public RentalAgreement createRentalAgreement(@RequestBody RentalAgreementDTO rentalAgreement) {
        try {
            return checkoutService.createRentalAgreement(rentalAgreement);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public List<RentalAgreement> getAllRentalAgreements() {
        try {
            return checkoutService.getAllRentalAgreements();
        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
