package com.example.demo.repository;

import com.example.demo.model.DocType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocTypeRepository extends JpaRepository<DocType, Integer> {
}
