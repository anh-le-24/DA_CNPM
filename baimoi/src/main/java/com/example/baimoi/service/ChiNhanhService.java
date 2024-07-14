package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.ChiNhanh;
import com.example.baimoi.repository.ChiNhanhRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ChiNhanhService {

    @Autowired
    private ChiNhanhRepository chiNhanhRepository;

    public List<ChiNhanh> getAllChiNhanhs() {
        return chiNhanhRepository.findAll();
    }

    public Optional <ChiNhanh> getChiNhanhById(Long id) {
        return chiNhanhRepository.findById(id);
    }

    public ChiNhanh saveChiNhanh(ChiNhanh chiNhanh) {
        return chiNhanhRepository.save(chiNhanh);
    }

    public void deleteChiNhanh(Long id) {
        chiNhanhRepository.deleteById(id);
    }
}
