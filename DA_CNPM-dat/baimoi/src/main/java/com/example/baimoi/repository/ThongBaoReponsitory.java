package com.example.baimoi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.ThongBao;

import jakarta.transaction.Transactional;

@Repository
public interface ThongBaoReponsitory extends JpaRepository<ThongBao, Long>{
    List<ThongBao> findByMand(Long mand);
    @Modifying
    @Transactional
    @Query("DELETE FROM ThongBao tb WHERE tb.mand = :mand")
    void deleteByNguoiDungMand(@Param("mand") Long mand);
}
