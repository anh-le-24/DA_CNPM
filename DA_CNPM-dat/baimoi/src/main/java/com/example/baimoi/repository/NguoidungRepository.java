package com.example.baimoi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.NguoiDung;

@Repository
public interface NguoidungRepository extends JpaRepository<NguoiDung, Long> {
    Optional<NguoiDung> findBySdtAndPassword(String sdt, String password);
    List<NguoiDung> findByHotenContainingIgnoreCase(String hoten);
    NguoiDung findByEmail(String email);
    //thống kê
    @Query("SELECT MONTH(nd.ngaytaotk) AS month, COUNT(nd) AS count FROM NguoiDung nd GROUP BY MONTH(nd.ngaytaotk)")
    List<Object[]> countUsersByMonth();
}
