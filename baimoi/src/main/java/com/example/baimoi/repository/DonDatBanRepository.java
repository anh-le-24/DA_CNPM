package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.DonDatBan;

@Repository
public interface DonDatBanRepository extends JpaRepository<DonDatBan,Long> {

}
