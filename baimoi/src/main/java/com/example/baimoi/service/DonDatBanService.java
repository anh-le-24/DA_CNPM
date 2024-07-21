package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.repository.DonDatBanRepository;

@Service
public class DonDatBanService {
    @Autowired
    private DonDatBanRepository donDatBanRepository;

    public Optional<DonDatBan> getDonDatBanById(Long id){
        return donDatBanRepository.findById(id);
    }
    public List<DonDatBan> getAllDonDatBan(){
        return donDatBanRepository.findAll();
    }
    public void saveDonDatBan(DonDatBan donDatBan) {
        donDatBanRepository.save(donDatBan);
    }

}
