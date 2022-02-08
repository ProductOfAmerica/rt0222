package com.rt0222.domain.repository;

import com.rt0222.domain.model.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {
}