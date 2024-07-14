package com.example.baimoi.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.baimoi.model.LoaiNhaHang;
import com.example.baimoi.service.LoaiNhaHangService;
import com.example.baimoi.service.StorageService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/loainhahang")
public class LoaiNhaHangController {
    @Autowired
    private LoaiNhaHangService loaiNhaHangService;
    @Autowired
    private StorageService storageService;

    @GetMapping("")
    public String allLoaiNhaHang(Model model){
        model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
        model.addAttribute("loainhahang", new LoaiNhaHang());
        return "plnh";
    }

    @GetMapping("/{id}")
    public String getLoaiNhaHangById(@PathVariable("id") Long id, Model model) {
        Optional<LoaiNhaHang> loainhahang = loaiNhaHangService.getLoaiNhaHangById(id);
        model.addAttribute("loainhahangid", loainhahang.get());
        return "plnh";
    }

    @PostMapping("/save")
    public String saveLoaiNhaHang(@Valid @ModelAttribute("loainhahang") LoaiNhaHang loaiNhaHang,
                                @RequestParam("file") MultipartFile file) throws IOException {

        this.storageService.store(file);

        loaiNhaHang.setImgmota(file.getOriginalFilename());
        loaiNhaHangService.save(loaiNhaHang);
        return "redirect:/loainhahang";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteLoaiNhaHang(@PathVariable("id") Long id) {
        loaiNhaHangService.deleteById(id);
        return "redirect:/loainhahang";
    }
}
