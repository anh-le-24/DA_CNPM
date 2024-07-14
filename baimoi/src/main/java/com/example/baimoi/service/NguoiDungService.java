package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.repository.NguoidungRepository;

@Service
public class NguoiDungService {
    @Autowired
    private NguoidungRepository nguoidungRepository;

    public List<NguoiDung> getAllNguoiDung(){
        return nguoidungRepository.findAll();
    }

    public Optional<NguoiDung> getNguoiDungById(Long id){
        return nguoidungRepository.findById(id);
    }

    public void saveOrUpdate(NguoiDung nguoiDung) {
        nguoidungRepository.save(nguoiDung);
    }

    public void deleteNguoiDung(Long id) {
        nguoidungRepository.deleteById(id);
    }
    
}
