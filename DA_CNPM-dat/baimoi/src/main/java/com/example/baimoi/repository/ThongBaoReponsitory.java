package com.example.baimoi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.ThongBao;

@Repository
public interface ThongBaoReponsitory extends JpaRepository<ThongBao, Long>{
    List<ThongBao> findByMand(Long mand);
}
