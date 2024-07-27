package com.example.baimoi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.NguoiDung;

@Repository
public interface NguoidungRepository extends JpaRepository<NguoiDung, Long> {
    Optional<NguoiDung> findBySdtAndPassword(String sdt, String password);
    List<NguoiDung> findByHotenContainingIgnoreCase(String hoten);

}
