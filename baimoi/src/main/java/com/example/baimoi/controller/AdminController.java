package com.example.baimoi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.NguoiDungService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private DoiTacService doitacService;
    
    @Autowired
    private NguoiDungService nguoidungService;

    @GetMapping("/xindk")
    public String getDonDK(Model model) {
        List<DoiTac> doitacs = doitacService.getAllDoitac();
        model.addAttribute("doitacs", doitacs); 
        return "quanly/doitachtml"; 
    }

    @GetMapping("/tatca")
    public String listDoitac(Model model) {
        List<DoiTac> doitacs = doitacService.getDoitacByMadt((long) 3);
        model.addAttribute("doitacs", doitacs);
        return "quanly/tatcadoitac";
    }



    @PostMapping("/sua")
    public String processEditForm(@ModelAttribute("nguoidung") NguoiDung nguoidung, RedirectAttributes redirectAttributes) {
        try {
            nguoidungService.updateMapq(nguoidung.getMand(), 3); // Cập nhật mapq thành 3
            redirectAttributes.addFlashAttribute("successMessage", "Xác nhận thành công đối tác");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/tatca";
    }
    
    @DeleteMapping("/xoa/{madt}")
    public String xoaDoiTac(@PathVariable Long madt, RedirectAttributes redirectAttributes) {
        doitacService.deleteByIdDT(madt);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa Đối tác thành công!");
        return "redirect:/admin/tatca";
    }

    @RequestMapping("/error")
    public String handleError() {
        // Trả về tên template error
        return "error";
    }
}
