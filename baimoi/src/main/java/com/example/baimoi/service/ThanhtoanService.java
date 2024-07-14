package com.example.baimoi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.Thanhtoan;
import com.example.baimoi.repository.ThanhtoanRepository;

@Service
public class ThanhtoanService {
    
    @Autowired

    private ThanhtoanRepository thanhtoanRepository;

    public Optional<Thanhtoan> getThanhtoanByID(int id){
            return thanhtoanRepository.findById(id);
    }
}
