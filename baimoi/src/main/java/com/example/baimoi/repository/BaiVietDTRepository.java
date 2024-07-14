package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.BaiVietDT;

@Repository
public interface BaiVietDTRepository extends JpaRepository<BaiVietDT, Long> {
    
}
