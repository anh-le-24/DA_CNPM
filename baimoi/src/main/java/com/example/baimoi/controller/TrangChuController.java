package com.example.baimoi.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.baimoi.model.ComBoMonAn;
import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.DonDatBanService;
import com.example.baimoi.service.LoaiNhaHangService;
import com.example.baimoi.service.NguoiDungService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class TrangChuController {
    
    @Autowired
    private DoiTacService doiTacService;
    @Autowired 
    private NguoiDungService nguoiDungService;
    @Autowired
    private LoaiNhaHangService loaiNhaHangService;
    @Autowired
    private DonDatBanService donDatBanService;

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

    // ----- Đơn đặt bàn -----//
    
    @GetMapping("/datban/{id}")
    public String getViewDatBan(@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra đăng nhập
        Long mand = (Long) session.getAttribute("mand");
        if (mand == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng đăng nhập để đặt bàn");
            return "redirect:/login";
        }

        // Lấy thông tin đối tác và người dùng
        Optional<DoiTac> doiTacOptional = doiTacService.getDoiTacById(id);
        Optional<NguoiDung> nguoiDungOptional = nguoiDungService.getNguoiDungById(mand);

        // Kiểm tra xem có tìm thấy đối tác và người dùng không
        if (!doiTacOptional.isPresent() || !nguoiDungOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin đối tác hoặc người dùng");
            return "redirect:/error";
        }

        DoiTac doiTac = doiTacOptional.get();
        NguoiDung nguoiDung = nguoiDungOptional.get();

        // Thêm thông tin vào model
        model.addAttribute("doiTac", doiTac);
        model.addAttribute("nguoiDung", nguoiDung);

        // Tạo đơn đặt bàn mới
        DonDatBan donDatBan = new DonDatBan();
        donDatBan.setMadt(id);
        donDatBan.setMand(mand);
        donDatBan.setSolgnguoi(1);
        donDatBan.setThoigiandat(new Time(System.currentTimeMillis()));
        donDatBan.setNgaydat(new Date(Calendar.getInstance().getTimeInMillis()));

        model.addAttribute("donDatBan", donDatBan);

        return "trangchu/dondatban";
    }


    @PostMapping("/save")
    public String datBan(@Valid @ModelAttribute("donDatBan") DonDatBan donDatBan,
                         BindingResult bindingResult,
                         @RequestParam("thoigiandat") String thoigiandat,
                         @RequestParam("ngaydat") String ngaydat,
                         RedirectAttributes redirectAttributes) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    
            donDatBan.setThoigiandat(new Time(timeFormat.parse(thoigiandat).getTime()));
            donDatBan.setNgaydat(dateFormat.parse(ngaydat));
            donDatBan.setMattd(1L); // Assuming 1 is the initial status
            donDatBan.setSotien(0.0); // Initial amount
                    
            donDatBanService.saveDonDatBan(donDatBan);
            redirectAttributes.addFlashAttribute("successMessage", "Đặt bàn thành công");
            return "redirect:/datthanhcong";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi đặt bàn: " + e.getMessage());
            return "redirect:/datban/" + donDatBan.getMadt();
        }
    }

    @GetMapping("/datthanhcong")
    private String getViewThanhCong(){
        return "trangchu/datthanhcong";
    }
}
