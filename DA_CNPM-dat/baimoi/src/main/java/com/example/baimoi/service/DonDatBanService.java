package com.example.baimoi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.repository.DoiTacRepository;
import com.example.baimoi.repository.DonDatBanRepository;
import com.example.baimoi.repository.NguoidungRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class DonDatBanService {
    @Autowired
    private DonDatBanRepository donDatBanRepository;
    @Autowired
    private NguoidungRepository nguoidungRepository;
    @Autowired
    private DoiTacRepository doiTacRepository;
    
    @Transactional
    public Optional<DonDatBan> getDonDatBanById(Long id){
        return donDatBanRepository.findById(id);
    }
    @Transactional
    public List<DonDatBan> getAllDonDatBan(){
        return donDatBanRepository.findAll();
    }
    public void saveDonDatBan(DonDatBan donDatBan) {
        donDatBanRepository.save(donDatBan);
    }

    @Transactional
    public List<DonDatBan> getDonDatBansForUser(Long mand) {
        // Fetch the user to ensure that NguoiDung entity is fully initialized
        Optional<NguoiDung> nguoiDungOpt = nguoidungRepository.findById(mand);
        if (nguoiDungOpt.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        NguoiDung nguoiDung = nguoiDungOpt.get();

        // Fetch DonDatBan entries for the user
        return donDatBanRepository.findByNguoiDung(nguoiDung);
    }

    @Transactional
    public List<DonDatBan> getDonDatBansForDoiTac(Long madt) {
        
        Optional<DoiTac> DoiTacOtp = doiTacRepository.findById(madt);
        if (DoiTacOtp.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        DoiTac doiTac = DoiTacOtp.get();

        // Fetch DonDatBan entries for the user
        return donDatBanRepository.findByDoiTac(doiTac);
    }

    public void deleteDonDatBan(Long id) {
        donDatBanRepository.deleteById(id);
    }

    @Transactional
    public void deleteDonDatBanByDoiTacId(Long doiTacId) {
        List<DonDatBan> donDatBans = getDonDatBansForDoiTac(doiTacId);
        donDatBanRepository.deleteAll(donDatBans);
    }
}
