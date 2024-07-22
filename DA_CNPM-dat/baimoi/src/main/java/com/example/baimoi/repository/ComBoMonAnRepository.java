package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.ComBoMonAn;

@Repository
public interface ComBoMonAnRepository extends JpaRepository<ComBoMonAn, Long> {
}