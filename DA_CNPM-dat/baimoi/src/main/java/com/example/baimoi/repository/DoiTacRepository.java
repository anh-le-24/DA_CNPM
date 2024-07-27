package com.example.baimoi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;


@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac, Long> {
    @Query("SELECT d FROM DoiTac d LEFT JOIN FETCH d.imgDoiTacs WHERE d.madt = :id")
    DoiTac findDoiTacWithImages(@Param("id") Long id);

    List<DoiTac> findByMadt(Long madt);
    
    @Query("SELECT dt FROM DoiTac dt JOIN FETCH dt.donDatBans ddb WHERE dt.madt = :id")
    DonDatBan findDonDatBansByDoiTacId(@Param("id") Long id);

    //list để tìm kiếm
    List<DoiTac> findByTennhahangContainingIgnoreCase(String tennhahang);
    List<DoiTac> findBySonhanhContainingIgnoreCaseOrDuongnhContainingIgnoreCaseOrQuannhContainingIgnoreCaseOrThanhphonhContainingIgnoreCase(String sonhanh, String duongnh, String quannh, String thanhphonh);

    @Query("SELECT d FROM DoiTac d WHERE "
            + "(:thanhpho IS NULL OR d.thanhphonh = :thanhpho) AND "
            + "(:hoadon IS NULL OR d.hoadontb = :hoadon)")
    List<DoiTac> findByCriteria(
            @Param("thanhpho") String thanhpho,
            @Param("hoadon") String hoadon);

    @Query("SELECT d FROM DoiTac d JOIN d.loaiNhaHangs lnh WHERE lnh.id = :loaiNhaHangId")
    List<DoiTac> findByLoaiNhaHang(@Param("loaiNhaHangId") Long loaiNhaHangId);    
    
    @Query("SELECT d.nguoiDung.mand FROM DoiTac d WHERE d.madt = :madt")
    Long findMaNguoiDungByMaDoiTac(@Param("madt") Long madt);

}
