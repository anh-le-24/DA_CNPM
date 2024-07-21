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

    public void updateMapq(Long mand, int newMapq) {
        Optional<NguoiDung> optionalNguoidung = nguoidungRepository.findById(mand);
        if (optionalNguoidung.isPresent()) {
            NguoiDung nguoidung = optionalNguoidung.get();
            nguoidung.setMapq(newMapq);
            nguoidungRepository.save(nguoidung);
        } else {
            // Xử lý khi không tìm thấy người dùng
            throw new RuntimeException("Không tìm thấy người dùng với mã: " + mand);
        }
    }
    
}
