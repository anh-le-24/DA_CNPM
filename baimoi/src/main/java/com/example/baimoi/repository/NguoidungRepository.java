package com.example.baimoi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.NguoiDung;

@Repository
public interface NguoidungRepository extends JpaRepository<NguoiDung, Long> {
    Optional<NguoiDung> findBySdtAndPassword(String sdt, String password);
    
    @Query("SELECT nd FROM NguoiDung nd JOIN FETCH nd.donDatBans WHERE nd.mand = :id")
    NguoiDung findByIdWithDonDatBans(@Param("id") Long id);
}
