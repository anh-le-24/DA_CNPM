package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.NguoiDung;

@Repository
public interface NguoidungRepository extends JpaRepository<NguoiDung, Long> {
    
}
