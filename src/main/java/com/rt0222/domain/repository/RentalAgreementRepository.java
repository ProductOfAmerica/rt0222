package com.rt0222.domain.repository;

import com.rt0222.domain.model.RentalAgreement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalAgreementRepository extends CrudRepository<RentalAgreement, Long> {
}