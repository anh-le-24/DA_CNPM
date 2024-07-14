package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.Thanhtoan;

@Repository
public interface ThanhtoanRepository extends JpaRepository<Thanhtoan, Integer>{
    
}
