package com.example.baimoi.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.DichVuCC;
import com.example.baimoi.repository.DichVuCCRepository;

@Service
public class DichVuCCService {

    @Autowired
    private DichVuCCRepository dichVuCCRepository;

    public Optional<DichVuCC> getDichVuCCById(Long id){
        return dichVuCCRepository.findById(id);
    }
    
    public List<DichVuCC> getAllDichVuCC(){
        return dichVuCCRepository.findAll();
    }
}
