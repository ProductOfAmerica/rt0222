package com.rt0222.service;

import com.rt0222.domain.model.RentalAgreement;
import com.rt0222.domain.model.request.RentalAgreementDTO;

import java.util.List;

public interface CheckoutService {
    List<RentalAgreement> getAllRentalAgreements();

    RentalAgreement createRentalAgreement(RentalAgreementDTO rentalAgreement);
}
