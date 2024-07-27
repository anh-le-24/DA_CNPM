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
public interface DonDatBanRepository extends JpaRepository<DonDatBan,Long> {
    List<DonDatBan> findByNguoiDung(NguoiDung nguoiDung);
    List<DonDatBan> findByDoiTac(DoiTac doiTac);

     @Query("SELECT YEAR(d.ngaydat) as year, MONTH(d.ngaydat) as month, COUNT(d) as orderCount, SUM(d.sotien) as totalRevenue " +
           "FROM DonDatBan d " +
           "WHERE d.madt = :doiTacId " +
           "GROUP BY YEAR(d.ngaydat), MONTH(d.ngaydat) " +
           "ORDER BY YEAR(d.ngaydat), MONTH(d.ngaydat)")
    List<Object[]> getMonthlyStatsForDoiTac(@Param("doiTacId") Long doiTacId);
}
