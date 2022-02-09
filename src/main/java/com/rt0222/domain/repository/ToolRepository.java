package com.rt0222.domain.repository;

import com.rt0222.domain.model.Tool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends CrudRepository<Tool, Long> {
    Tool findToolByToolCode(String toolCode);
}