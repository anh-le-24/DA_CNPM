package com.example.baimoi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class TrangChuController {
    
    @GetMapping("/trangchu")
    private String getViewTrangChu(){
        return "trangchu/index";
    }

    @GetMapping("/ctnhahang")
    private String getViewCTNhaHang(){
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
