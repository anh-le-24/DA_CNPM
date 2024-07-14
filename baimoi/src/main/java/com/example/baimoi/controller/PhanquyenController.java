package com.example.baimoi.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.model.Phanquyen;
import com.example.baimoi.service.PhanquyenService;


@Controller
@RequestMapping("/phanquyennns")
public class PhanquyenController {
    
    @Autowired
    private PhanquyenService phanquyenService;



    @GetMapping("/{id}")
    public String getPhanquyenbyID(@PathVariable("id") Long id,Model model){
        Optional<Phanquyen> phanquyen = phanquyenService.getPhanquyenbyID(id);
        model.addAttribute("phanquyen", phanquyen.get());
        return "phanquyenss";
    }

}

