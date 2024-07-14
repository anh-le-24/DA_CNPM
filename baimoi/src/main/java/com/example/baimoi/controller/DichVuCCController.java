package com.example.baimoi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.model.DichVuCC;
import com.example.baimoi.service.DichVuCCService;

@Controller
@RequestMapping("/dichvu")
public class DichVuCCController {
    
    @Autowired
    private DichVuCCService dichVuCCService;

    @GetMapping("")
    public String getViewDV(){
        return "dichvu";
    }

    @GetMapping("/tatcadv")
    public String allDichVu(Model model){
        model.addAttribute("dichvuccs",dichVuCCService.getAllDichVuCC());
        model.addAttribute("dichvucc",new DichVuCC());
        return "dichvu";
    }

    @GetMapping("/tatcadv/{id}")
    public String getDichVuById(@PathVariable("id") Long id, Model model) {
        Optional<DichVuCC> dichvucc = dichVuCCService.getDichVuCCById(id);
        model.addAttribute("dichvucc", dichvucc.get());
        return "dichvu";
    }
}
