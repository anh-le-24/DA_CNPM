package com.example.baimoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.service.DonDatBanService;

@Controller
@RequestMapping("/dondatban")
public class DonDatBanController {
    @Autowired
    private DonDatBanService donDatBanService;

    @GetMapping("")
    public String getTatcaDDB(Model model){
        model.addAttribute("dondatbans",donDatBanService.getAllDonDatBan());
        model.addAttribute("dondatban",new DonDatBan());
        return "testdondatban";
    }
    
}
