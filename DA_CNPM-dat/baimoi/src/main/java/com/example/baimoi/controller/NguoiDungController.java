package com.example.baimoi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.NguoiDungService;
import com.example.baimoi.service.ThongBaoService;

@Controller
@RequestMapping("/nguoidung")
public class NguoiDungController {
    
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private ThongBaoService thongBaoService;
    
    @GetMapping("/tatca")
    public String allNguoiDung(Model model){
        model.addAttribute("nguoidungs", nguoiDungService.getAllNguoiDung());
        model.addAttribute("nguoidung", new NguoiDung());
        return "quanly/khachhang";
    }

    @PostMapping("/them")
    public String themKhachHang(@ModelAttribute("nguoidung") NguoiDung nguoiDung, RedirectAttributes redirectAttributes) {
        nguoiDungService.saveOrUpdate(nguoiDung);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm khách hàng thành công!");
        return "redirect:/nguoidung/tatca";
    }
    
    @GetMapping("/suanguoidung/{mand}")
    public String showEditForm(@PathVariable("mand") Long mand, Model model) {
        NguoiDung nguoiDung = nguoiDungService.findByIdND(mand);
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("isEditing", true);
        model.addAttribute("nguoidungs", nguoiDungService.getAllNguoiDung());
        return "quanly/khachhang";
    }

    @PostMapping("/sua")
    public String updateNguoiDung(@ModelAttribute("nguoiDung") NguoiDung nguoiDung) {
        if (nguoiDung.getPassword() == null || nguoiDung.getPassword().isEmpty()) {
            // Xử lý giá trị password nếu không được cung cấp
        }
        nguoiDungService.saveOrUpdate(nguoiDung);
        return "redirect:/nguoidung/tatca";
    }

    @GetMapping("/chitiet/{mand}")
    public String showUserDetails(@PathVariable("mand") Long mand, Model model) {
    NguoiDung nguoiDung = nguoiDungService.findByIdND(mand);
        model.addAttribute("nguoiDung", nguoiDung);
        model.addAttribute("isDetails",true);
        model.addAttribute("nguoidungs", nguoiDungService.getAllNguoiDung());
        return "quanly/khachhang"; 
   
    }

 
    @DeleteMapping("/xoa/{mand}")
    public ResponseEntity<Void> xoaNguoidung(@PathVariable Long mand) {
        try {
            // Xóa các thông báo liên quan đến người dùng
            thongBaoService.deleteByNguoiDungMand(mand);
            // Xóa người dùng
            nguoiDungService.deleteNguoiDung(mand);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "hoten", required = false, defaultValue = "") String hoten, Model model) {
        List<NguoiDung> results = nguoiDungService.searchByHoten(hoten); // Đảm bảo phương thức tìm kiếm đúng tên
        model.addAttribute("nguoidungs", results);
        return "quanly/khachhang"; // Thay đổi tên view cho phù hợp
    }

    //đổi mật khẩu

    
}
