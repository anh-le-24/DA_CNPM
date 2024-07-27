package com.example.baimoi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.service.DoiTacService;

@Controller
@RequestMapping("/thongke")
public class ThongKeController {

    @Autowired
    private DoiTacService doiTacService;

    @GetMapping("/thang")
    public String thongKeSoLuongDoiTacTheoThang(Model model) {
        Map<Integer, Long> thongKeThang = doiTacService.countDoiTacByMonth();
        model.addAttribute("thongKeThang", thongKeThang);
        return "quanly/thongke"; 
    }
    
}
