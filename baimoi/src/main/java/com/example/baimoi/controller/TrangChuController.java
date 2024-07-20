package com.example.baimoi.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
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

import com.example.baimoi.model.ComBoMonAn;
import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.LoaiNhaHangService;
import com.example.baimoi.service.NguoiDungService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class TrangChuController {
    
    @Autowired
    private DoiTacService doiTacService;
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
    private String getViewCTNhaHang(@PathVariable("id") Long id, Model model)
                                        throws IOException, ParseException{
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());

        model.addAttribute("images", doiTacService.getDoiTacWithImages(id).getImgDoiTacs());
        model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
        model.addAttribute("comBoMonAn", new ComBoMonAn());

        
        return "trangchu/chitiet";
    }

    @GetMapping("/info")
    private String getViewInfo(){
        return "trangchu/info";
    }

    // -------- Đăng Nhập Tài khoản --------// 
    @GetMapping("/login")
    public String getViewLogin() {
        return "trangchu/Login";
    }

    @PostMapping("/login/check")
    public String login(@RequestParam("sdt") String sdt, 
                        @RequestParam("password") String password, 
                        Model model, HttpSession session) {
        Optional<NguoiDung> nguoiDungOtp = nguoiDungService.findBySdtAndPassword(sdt, password);
        if (nguoiDungOtp.isPresent()) {
            NguoiDung nguoiDung = nguoiDungOtp.get();
            session.setAttribute("loggedInUser", nguoiDung);

            Long mand = nguoiDung.getMand();
            session.setAttribute("mand", mand);

            return "redirect:/trangchu";
        } else {
            model.addAttribute("error", "Số điện thoại hoặc mật khẩu không đúng");
            return "trangchu/Login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/trangchu";
    }
    // --------- Đăng Ký Tài Khoản -------//
    @GetMapping("/register")
    private String getViewRegister(Model model){
        model.addAttribute("nguoiDung", new NguoiDung());
        return "trangchu/register";
    }

    @PostMapping("/register")
    private String registerUser(@ModelAttribute("nguoiDung") NguoiDung nguoiDung) {
        // Đặt các giá trị mặc định
        Date ngayHienTai = new Date(Calendar.getInstance().getTimeInMillis());
        nguoiDung.setNgaytaotk(ngayHienTai);
        
        nguoiDung.setSolandatban(0);
        nguoiDung.setMapq(2);

        // Lưu người dùng mới
        nguoiDungService.saveOrUpdate(nguoiDung);

        // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
        return "redirect:/login";
    }

    @GetMapping("/resetpw")
    private String getViewRepass(){
        return "trangchu/resetpw";
    }
}
