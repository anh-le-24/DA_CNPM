package com.example.baimoi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.baimoi.model.ImgDoiTac;

@Repository
public interface ImgDoiTacRepository extends JpaRepository<ImgDoiTac, Long> {
}
