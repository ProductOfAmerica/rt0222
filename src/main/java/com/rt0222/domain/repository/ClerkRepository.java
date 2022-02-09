package com.rt0222.domain.repository;

import com.rt0222.domain.model.Clerk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClerkRepository extends CrudRepository<Clerk, Long> {
    Clerk findClerkById(Long clerkId);
}