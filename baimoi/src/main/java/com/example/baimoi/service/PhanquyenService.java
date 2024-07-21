package com.example.baimoi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.Phanquyen;
import com.example.baimoi.repository.PhanquyenRepository;


@Service
public class PhanquyenService {
    
    @Autowired
    private PhanquyenRepository phanquyenRepository;

    public Optional<Phanquyen> getPhanquyenbyID(Long id){
        return phanquyenRepository.findById(id);
    }
}
