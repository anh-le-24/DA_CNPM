package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.ImgDoiTac;
import com.example.baimoi.repository.ImgDoiTacRepository;

@Service
public class ImgDoiTacService {
    @Autowired
    private ImgDoiTacRepository imgDoiTacRepository;

    public List<ImgDoiTac> getAllImgDoiTac() {
        return imgDoiTacRepository.findAll();
    }

    public Optional<ImgDoiTac> getImgDoiTacById(Long id) {
        return imgDoiTacRepository.findById(id);
    }

    public void save(ImgDoiTac imgDoiTac) {
        imgDoiTacRepository.save(imgDoiTac);
    }

    public void deleteById(Long id) {
        imgDoiTacRepository.deleteById(id);
    }
}
