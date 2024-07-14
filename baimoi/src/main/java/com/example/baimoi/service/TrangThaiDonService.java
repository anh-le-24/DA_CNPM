package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.TrangThaiDon;
import com.example.baimoi.repository.TrangThaiDonRepository;

@Service
public class TrangThaiDonService {
    @Autowired
    private TrangThaiDonRepository trangThaiDonRepository;
    
    public Optional<TrangThaiDon> getTrangThaiDonByID(Long id){
            return trangThaiDonRepository.findById(id);
    }
    public List<TrangThaiDon> getAllTrangThaiDon() {
        return trangThaiDonRepository.findAll();
    }
}
