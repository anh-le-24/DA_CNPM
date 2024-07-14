package com.example.baimoi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.LoaiNhaHangService;
import com.example.baimoi.service.NguoiDungService;

@Controller
@RequestMapping("")
public class TrangChuController {
    
    @Autowired
    private DoiTacService doiTacService;
    @Autowired 
    private NguoiDungService nguoiDungService;
    @Autowired
    private LoaiNhaHangService loaiNhaHangService;

    @GetMapping("/trangchu")
    private String getViewTrangChu(Model model){
        model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
        model.addAttribute("doiTacs", doiTacService.getAllDoitac());
        return "trangchu/index";
    }

    @GetMapping("/ctnhahang/{id}")
    private String getViewCTNhaHang(@PathVariable("id") Long id, Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        return "trangchu/chitiet";
    }

    @GetMapping("/info")
    private String getViewInfo(){
        return "trangchu/info";
    }

    @GetMapping("/login")
    private String getViewLogin(){
        return "trangchu/Login";
    }

    @GetMapping("/register")
    private String getViewRegister(){
        return "trangchu/register";
    }

    @GetMapping("/resetpw")
    private String getViewRepass(){
        return "trangchu/resetpw";
    }
}
