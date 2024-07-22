package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DanhGia;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {
    
}
