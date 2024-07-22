package com.example.baimoi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.model.NguoiDung;

@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac, Long> {
    @Query("SELECT d FROM DoiTac d LEFT JOIN FETCH d.imgDoiTacs WHERE d.madt = :id")
    DoiTac findDoiTacWithImages(@Param("id") Long id);

    List<DoiTac> findByMadt(Long madt);
    
    @Query("SELECT dt FROM DoiTac dt JOIN FETCH dt.donDatBans ddb WHERE dt.madt = :id")
    DonDatBan findDonDatBansByDoiTacId(@Param("id") Long id);
}
