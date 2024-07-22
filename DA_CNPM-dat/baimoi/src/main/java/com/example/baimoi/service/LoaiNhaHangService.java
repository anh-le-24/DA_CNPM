package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.LoaiNhaHang;
import com.example.baimoi.repository.LoaiNhaHangRepository;

@Service
public class LoaiNhaHangService {

    @Autowired
    private LoaiNhaHangRepository loaiNhaHangRepository;

    public List<LoaiNhaHang> getAllLoaiNhaHang() {
        return loaiNhaHangRepository.findAll();
    }

    public Optional<LoaiNhaHang> getLoaiNhaHangById(Long id) {
        return loaiNhaHangRepository.findById(id);
    }

    public void save(LoaiNhaHang loaiNhaHang) {
        loaiNhaHangRepository.save(loaiNhaHang);
    }

    public void deleteById(Long id) {
        loaiNhaHangRepository.deleteById(id);
    }
}
