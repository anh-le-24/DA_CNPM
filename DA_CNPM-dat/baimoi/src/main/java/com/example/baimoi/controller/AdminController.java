package com.example.baimoi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.ChiNhanhService;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.NguoiDungService;
import com.example.baimoi.service.ThongBaoService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private DoiTacService doitacService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private ChiNhanhService chiNhanhService;
    @Autowired
    private ThongBaoService thongBaoService;

    @GetMapping("/tatcadoitac")
    public String listDoiTacAll(Model model) {
        List<DoiTac> doiTacList = doitacService.getAllDoitac();
        model.addAttribute("doiTacList", doiTacList);
        return "quanly/tatcadoitac";
    }

    @GetMapping("/chitiet/{madt}")
    public String getDoiTacById(@PathVariable("madt") Long madt, Model model) {
        DoiTac doiTac = doitacService.findByIdDT(madt);
        model.addAttribute("doiTac", doiTac);
        model.addAttribute("isview", true); 
        model.addAttribute("isEditing", false); 
        return "quanly/tatcadoitac";
    }

    //----------------------
    @GetMapping("/tatcadonxindk")
    public String listDoiTac(Model model) {
        List<DoiTac> doiTacList = doitacService.getAllDoitac();
        model.addAttribute("doiTacList", doiTacList);
        return "quanly/doitachtml";
    }

    @GetMapping("/details/{madt}")
    public String showDoiTacDetails(@PathVariable("madt") Long madt, Model model) {
        Optional<DoiTac> doiTac = doitacService.getDoiTacById(madt);
        if (doiTac.isPresent()) {
            model.addAttribute("doiTac", doiTac.get());
        } else {

            return "error";
        }
        List<DoiTac> doiTacList = doitacService.getAllDoitac();
        model.addAttribute("doiTacList", doiTacList);
        return "quanly/doitachtml";
    }

    @GetMapping("/xacnhan/{id}")
    public String xacNhan(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        NguoiDung nguoiDung = nguoiDungService.findByIdND(id);
        if (nguoiDung != null) {
            nguoiDung.setMapq(3); 
            nguoiDungService.saveOrUpdate(nguoiDung);

            // Tạo thông báo cho người dùng
            thongBaoService.createThongBao(id, "Đăng ký đối tác thành công", "Tài khoản của bạn đã được xác nhận và phân quyền thành công.");

            redirectAttributes.addFlashAttribute("message", "Cập nhật mã phân quyền thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại!");
        }
        return "redirect:/admin/tatcadonxindk"; 
    }

    @DeleteMapping("/xoa/{id}")
public ResponseEntity<String> xoaDoiTac(@PathVariable("id") Long id) {
    try {
        chiNhanhService.deleteChiNhanh(id); 
        doitacService.deleteByIdDT(id);
        return ResponseEntity.ok("Xóa thành công");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Xóa thất bại: " + e.getMessage());
    }
}

    @RequestMapping("/error")
    public String handleError() {
        // Trả về tên template error
        return "error";
    }
}
