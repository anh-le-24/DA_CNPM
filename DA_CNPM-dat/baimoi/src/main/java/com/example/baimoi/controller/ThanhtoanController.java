package com.example.baimoi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.model.Thanhtoan;
import com.example.baimoi.service.ThanhtoanService;

@Controller
@RequestMapping("/thanhtoann")
public class ThanhtoanController {
    
    @Autowired
    private ThanhtoanService thanhtoanService;

    @GetMapping("/{id}")
    public String getThanhtoanByID(@PathVariable("id") int id,Model model){
        Optional<Thanhtoan> thanhtoan = thanhtoanService.getThanhtoanByID(id);
        model.addAttribute("thanhtoan", thanhtoan.get());
        return "thanhtoans";
    }

    
}
