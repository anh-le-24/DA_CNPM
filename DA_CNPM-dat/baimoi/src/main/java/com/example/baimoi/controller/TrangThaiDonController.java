package com.example.baimoi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.model.TrangThaiDon;
import com.example.baimoi.service.TrangThaiDonService;

@Controller
@RequestMapping("/trangthaidon")
public class TrangThaiDonController {
    @Autowired
    private TrangThaiDonService trangThaiDonService;

    @GetMapping("")
    public String getViewTrangThai(){
        return "trangthai";
    }
    @GetMapping("/alltrangthai")
    public String allTrangThai(Model model){
        model.addAttribute("trangthaidons",trangThaiDonService.getAllTrangThaiDon());
        model.addAttribute("trangthaidon",new TrangThaiDon());
        return "trangthai";
    }

    @GetMapping("/{id}")
    public String getPhanquyenbyID(@PathVariable("id") Long id,Model model){
        Optional<TrangThaiDon> trangthai = trangThaiDonService.getTrangThaiDonByID(id);
        model.addAttribute("trangthai", trangthai.get());
        return "trangthai";
    }
}
