package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.ChiNhanh;

@Repository
public interface ChiNhanhRepository extends JpaRepository<ChiNhanh, Long> {
    
}
