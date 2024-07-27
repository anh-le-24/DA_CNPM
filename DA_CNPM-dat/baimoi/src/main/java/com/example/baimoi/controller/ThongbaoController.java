package com.example.baimoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.baimoi.service.ThongBaoService;

@Controller
@RequestMapping("")
public class ThongbaoController {

    @Autowired
    private ThongBaoService thongBaoService;

    @DeleteMapping("/thongbao/{id}")
    @ResponseBody
    public String deleteThongBao(@PathVariable Long id) {
        thongBaoService.deleteThongBao(id);
        return "Xóa thông báo thành công";
    }
}
