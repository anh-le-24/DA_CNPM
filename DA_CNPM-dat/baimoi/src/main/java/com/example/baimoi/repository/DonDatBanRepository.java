package com.example.baimoi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.model.NguoiDung;

@Repository
public interface DonDatBanRepository extends JpaRepository<DonDatBan,Long> {
    List<DonDatBan> findByNguoiDung(NguoiDung nguoiDung);
    List<DonDatBan> findByDoiTac(DoiTac doiTac);
}
