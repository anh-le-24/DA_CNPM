package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DoiTac;

@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac, Long> {
    @Query("SELECT d FROM DoiTac d LEFT JOIN FETCH d.imgDoiTacs WHERE d.madt = :id")
    DoiTac findDoiTacWithImages(@Param("id") Long id);
}
