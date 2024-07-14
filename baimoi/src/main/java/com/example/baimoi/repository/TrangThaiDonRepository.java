package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.TrangThaiDon;

@Repository
public interface TrangThaiDonRepository extends JpaRepository<TrangThaiDon, Long>{
    
}