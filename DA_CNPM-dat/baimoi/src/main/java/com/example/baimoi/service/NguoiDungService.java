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

    public Optional<NguoiDung> getNguoiDungById(Long mnd) {
        return nguoidungRepository.findById(mnd);
    }

    public List<NguoiDung> getAllNguoiDung(){
        return nguoidungRepository.findAll();
    }

    public NguoiDung findByIdND(Long idND) {
        return nguoidungRepository.findById(idND).orElse(null);
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

    public Optional<NguoiDung> findBySdtAndPassword(String sdt, String password) {
        return nguoidungRepository.findBySdtAndPassword(sdt, password);
    }

    public List<NguoiDung> searchByHoten(String hoten) {
        return nguoidungRepository.findByHotenContainingIgnoreCase(hoten);
    }

    //đổi mật khẩu
    public boolean changePassword(Long mand, String newPassword) {
        NguoiDung nguoiDung = nguoidungRepository.findById(mand).orElse(null);
        if (nguoiDung != null) {
            nguoiDung.changePassword(newPassword);
            nguoidungRepository.save(nguoiDung);
            return true;
        }
        return false;
    }
    
}
