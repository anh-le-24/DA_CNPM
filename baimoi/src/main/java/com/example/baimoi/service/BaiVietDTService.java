package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.BaiVietDT;
import com.example.baimoi.repository.BaiVietDTRepository;

@Service
public class BaiVietDTService {
    @Autowired
    private BaiVietDTRepository baiVietDTRepository;

    public List<BaiVietDT> getAllBaiVietDTs() {
        return baiVietDTRepository.findAll();
    }

    public Optional<BaiVietDT> getBaiVietDTById(Long id) {
        return baiVietDTRepository.findById(id);
    }

    public BaiVietDT saveBaiVietDT(BaiVietDT baiVietDT) {
        return baiVietDTRepository.save(baiVietDT);
    }

    public void deleteBaiVietDT(Long id) {
        baiVietDTRepository.deleteById(id);
    }
}
