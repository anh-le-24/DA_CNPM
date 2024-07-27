package com.example.baimoi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.NguoiDungService;

@Controller 
@RequestMapping("/thongke")
public class ThongKeController {

    @Autowired
    private DoiTacService doiTacService;
    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/thang")
    public String thongKeSoLuongDoiTacTheoThang(Model model) {
        Map<Integer, Long> thongKeDoiTacThang = doiTacService.countDoiTacByMonth();
        Map<Integer, Integer> thongKeNguoiDungThang = nguoiDungService.getUserStatisticsByMonth();
        model.addAttribute("thongKeDoiTacThang", thongKeDoiTacThang);
        model.addAttribute("thongKeNguoiDungThang", thongKeNguoiDungThang);
        return "quanly/thongke"; 
    }
    
}
