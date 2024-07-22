package com.example.baimoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.baimoi.model.DichVuCC;
import com.example.baimoi.service.DichVuCCService;

@Controller
@RequestMapping("/dichvu")
public class DichVuController {
    
    @Autowired
    private DichVuCCService dichVuCCService;

    @GetMapping("/tatca")
    public String getDichVuCCList(Model model) {
        model.addAttribute("dichVuCCList", dichVuCCService.getAllDichVuCC());
        return "quanly/dichvu";
    }

    @PostMapping("/them")
    public String themDichVuCC(@RequestParam String noidung,
                               @RequestParam int soluong,
                               @RequestParam double giadv) {
        DichVuCC dichVuCC = new DichVuCC();
        dichVuCC.setNoidung(noidung);
        dichVuCC.setSoluong(soluong);
        dichVuCC.setGiadv(giadv);
        dichVuCCService.saveDichVuCC(dichVuCC);
        return "redirect:/dichvu/tatca";
    }

    @GetMapping("/sua/{madv}")
    public String suaDichVuCC(@PathVariable Long madv, Model model) {
        DichVuCC dichVuCC = dichVuCCService.getDichVuById(madv);
        model.addAttribute("dichVuCC", dichVuCC);
        model.addAttribute("isedit",true);
        return "quanly/dichvu";
    }

    @PostMapping("/sua")
    public String suaDichVuCC(@RequestParam Long madv,
                              @RequestParam String noidung,
                              @RequestParam int soluong,
                              @RequestParam double giadv) {
        DichVuCC dichVuCC = dichVuCCService.getDichVuById(madv);
        dichVuCC.setNoidung(noidung);
        dichVuCC.setSoluong(soluong);
        dichVuCC.setGiadv(giadv);
        dichVuCCService.saveDichVuCC(dichVuCC);
        return "redirect:/dichvu/tatca";
    }

    @GetMapping("/xoa/{madv}")
    public String xoaDichVuCC(@PathVariable Long madv) {
        dichVuCCService.deleteDichVuCC(madv);
        return "redirect:/dichvu/tatca";
    }
}