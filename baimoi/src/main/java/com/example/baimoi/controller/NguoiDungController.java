package com.example.baimoi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.NguoiDungService;

@Controller
@RequestMapping("/nguoidung")
public class NguoiDungController {
    
    @Autowired
    private NguoiDungService nguoiDungService;
    
    @GetMapping("/tatca")
    public String allNguoiDung(Model model){
        model.addAttribute("nguoidungs", nguoiDungService.getAllNguoiDung());
        model.addAttribute("nguoidung", new NguoiDung());
        return "khachhang";
    }

    @PostMapping("/them")
    public String themKhachHang(@ModelAttribute("nguoidung") NguoiDung nguoiDung, RedirectAttributes redirectAttributes) {
        nguoiDungService.saveOrUpdate(nguoiDung);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm khách hàng thành công!");
        return "redirect:/nguoidung/tatca";
    }

    @GetMapping("/chitiet/{mand}")
    public ResponseEntity<?> getNguoidungChiTiet(@PathVariable Long mand) {
        Optional<NguoiDung> optionalNguoidung = nguoiDungService.getNguoiDungById(mand);
        if (optionalNguoidung.isPresent()) {
            NguoiDung nguoidung = optionalNguoidung.get();
            return ResponseEntity.ok(nguoidung);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/sua/{mand}")
    @ResponseBody
    public Optional<NguoiDung> suaNguoiDung(@PathVariable Long mand) {
        return nguoiDungService.getNguoiDungById(mand);
    }
 
    @PostMapping("/sua")
    public String processEditForm(@ModelAttribute("nguoidung") NguoiDung nguoidung, RedirectAttributes redirectAttributes) {
        nguoiDungService.saveOrUpdate(nguoidung);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin khách hàng thành công!");
        return "redirect:/nguoidung/tatca";
    }
 
    @DeleteMapping("/xoa/{mand}")
    public ResponseEntity<Void> xoaNguoidung(@PathVariable Long mand, RedirectAttributes redirectAttributes) {
        nguoiDungService.deleteNguoiDung(mand);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa khách hàng thành công!");
        return ResponseEntity.ok().build();
    }
}
