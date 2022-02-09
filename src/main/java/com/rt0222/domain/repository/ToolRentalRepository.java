package com.rt0222.domain.repository;

import com.rt0222.domain.model.ToolRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRentalRepository extends CrudRepository<ToolRental, Long> {
}