package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.ComBoMonAn;
import com.example.baimoi.repository.ComBoMonAnRepository;

@Service
public class ComBoMonAnService {

    @Autowired
    private ComBoMonAnRepository repository;

    public List<ComBoMonAn> getAllComboMonAn() {
        return repository.findAll();
    }

    public Optional<ComBoMonAn> getComboMonAnById(Long id) {
        return repository.findById(id);
    }

    public ComBoMonAn saveComboMonAn(ComBoMonAn comboMonAn) {
        return repository.save(comboMonAn);
    }

    public void deleteComboMonAn(Long id) {
        repository.deleteById(id);
    }
}