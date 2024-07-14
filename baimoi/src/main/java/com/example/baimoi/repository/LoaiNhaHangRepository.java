package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.LoaiNhaHang;

@Repository
public interface  LoaiNhaHangRepository extends JpaRepository<LoaiNhaHang, Long>{
    
}
