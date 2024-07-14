package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.DanhGia;
import com.example.baimoi.repository.DanhGiaRepository;

@Service
public class DanhGiaService {
    @Autowired
    private DanhGiaRepository danhGiaRepository;

    public Optional<DanhGia> getDanhGiaById(Long id){
        return danhGiaRepository.findById(id);
    }
    public List<DanhGia> getAllDanhGia(){
        return danhGiaRepository.findAll();
    }
    public void saveDanhGia(DanhGia danhGia) {
        danhGiaRepository.save(danhGia);
    }
}
