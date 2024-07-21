package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DichVuCC;

@Repository
public interface DichVuCCRepository extends JpaRepository<DichVuCC, Long> {
    
}
