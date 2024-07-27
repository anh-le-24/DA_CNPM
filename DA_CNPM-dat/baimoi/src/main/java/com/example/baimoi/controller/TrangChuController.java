package com.example.baimoi.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.baimoi.model.ComBoMonAn;
import com.example.baimoi.model.DanhGia;
import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.model.LoaiNhaHang;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.model.ThongBao;
import com.example.baimoi.service.ChiNhanhService;
import com.example.baimoi.service.ComBoMonAnService;
import com.example.baimoi.service.DanhGiaService;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.DonDatBanService;
import com.example.baimoi.service.LoaiNhaHangService;
import com.example.baimoi.service.NguoiDungService;
import com.example.baimoi.service.ThongBaoService;

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
    @Autowired
    private ComBoMonAnService comBoMonAnService;
    @Autowired 
    private ThongBaoService thongBaoService;
    @Autowired
    private DanhGiaService danhGiaService;
    @Autowired
    private ChiNhanhService chiNhanhService;


    @GetMapping("/")
    public RedirectView redirectToTrangChu() {
        return new RedirectView("/trangchu");
    }
    

    @GetMapping("/trangchu")
    private String getViewTrangChu(Model model, HttpSession session){
        // Lấy mã người dùng từ phiên làm việc
        Long mand = (Long) session.getAttribute("mand");
        
        // Nếu người dùng đã đăng nhập (có mã người dùng)
        if (mand != null) {
            List<ThongBao> thongBaos = thongBaoService.getThongBaosByUserId(mand);
            model.addAttribute("thongBaos", thongBaos);
        }
        
        model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
        model.addAttribute("doiTacs", doiTacService.getAllDoitac());
        model.addAttribute("nguoiDung", nguoiDungService.getNguoiDungById(mand));
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
    public ModelAndView getViewInfo(HttpSession session) {
        Long mand = (Long) session.getAttribute("mand");
        if (mand == null) {
            return new ModelAndView("redirect:/login");
        }
        Optional<NguoiDung> nguoiDungOtp = nguoiDungService.getNguoiDungById(mand);
       
        NguoiDung nguoiDung = nguoiDungOtp.get();

        ModelAndView mav = new ModelAndView("trangchu/info");
        mav.addObject("nguoiDung", nguoiDung);
        return mav;
    }
    
    @PostMapping("/update-info")
    public String updateInfo(@ModelAttribute NguoiDung updatedUser, HttpSession session) {
        Long mand = (Long) session.getAttribute("mand");
        if (mand == null) {
            return "redirect:/login";
        }
        Optional<NguoiDung> nguOptional = nguoiDungService.getNguoiDungById(mand);
        NguoiDung existingUser = nguOptional.get();
        existingUser.setHoten(updatedUser.getHoten());
        existingUser.setSdt(updatedUser.getSdt());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setSonha(updatedUser.getSonha());
        existingUser.setDuong(updatedUser.getDuong());
        existingUser.setQuan(updatedUser.getQuan());
        existingUser.setThanhpho(updatedUser.getThanhpho());

        nguoiDungService.saveOrUpdate(existingUser);
        return "redirect:/info";
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
    
    // -------- Lịch SỬ đặt bàn --------//
    
    @GetMapping("/lsdatban")
    public String getLichSuDatBan(HttpSession session, Model model) {
        Long mand = (Long) session.getAttribute("mand");
        if (mand == null) {
            return "redirect:/login";
        }

        List<DonDatBan> donDatBans = donDatBanService.getDonDatBansForUser(mand);
        model.addAttribute("donDatBans", donDatBans);
        return "trangchu/lsdondatban";
    }

    @GetMapping("/danhgia/{id}")
    public String getDanhGia(@PathVariable("id") Long id, HttpSession session, Model model) {
        Long mand = (Long) session.getAttribute("mand");
        if (mand == null) {
            return "redirect:/login";
        }
        Optional<DonDatBan> donDatBanOtp = donDatBanService.getDonDatBanById(id);
        DonDatBan donDatBan = donDatBanOtp.get();
        model.addAttribute("donDatBan", donDatBan);
        
        return "trangchu/feedback";
    }

    @PostMapping("/danhgia/save/{id}")
    public String postDanhGia(@PathVariable("id") Long id, 
                              @RequestParam("sosao") int sosao,
                              @RequestParam("binhluan") String binhluan,
                              HttpSession session) {
        Long mand = (Long) session.getAttribute("mand");
        if (mand == null) {
            return "redirect:/login";
        }

        Optional<DonDatBan> donDatBanOpt = donDatBanService.getDonDatBanById(id);
        if (donDatBanOpt.isPresent()) {
            DonDatBan donDatBan = donDatBanOpt.get();
            
            DanhGia danhGia = new DanhGia();
            danhGia.setSosao(sosao);
            danhGia.setBinhluan(binhluan);
            danhGia.setNgaydg(new Date(Calendar.getInstance().getTimeInMillis()));
            danhGia.setDonDatBan(donDatBan);
            danhGiaService.saveDanhGia(danhGia);

            donDatBan.setDanhGia(danhGia);
            donDatBan.setMadg(danhGia.getMadg());
            donDatBanService.saveDonDatBan(donDatBan);

            return "redirect:/lsdatban";
        } else {
            return "error";
        }
    }

    @PostMapping("/huy")
    public String postHuy(@RequestParam("maddb") Long maddb,
                        @RequestParam("lyDo") String lyDo) {

        Optional<DonDatBan> donDatBanOpt = donDatBanService.getDonDatBanById(maddb);
        
            DonDatBan donDatBan = donDatBanOpt.get();
            System.out.println(maddb);
            System.out.println(lyDo);

            DanhGia danhGia = new DanhGia();
            danhGia.setLydohuy(lyDo);
            danhGia.setSosao(0);
            danhGia.setNgaydg(new Date(Calendar.getInstance().getTimeInMillis()));
            danhGia.setDonDatBan(donDatBan);
            danhGiaService.saveDanhGia(danhGia);

            donDatBan.setDanhGia(danhGia);
            donDatBan.setMadg(danhGia.getMadg());
            donDatBan.setMattd(4L); 
            donDatBanService.saveDonDatBan(donDatBan);

            return "redirect:/lsdatban";
        
    }



    // Combo
    @GetMapping("/combo/{id}")
    private String getViewCombo(@PathVariable("id") Long id, Model model)
                                        throws IOException, ParseException{
        Optional<ComBoMonAn> monan = comBoMonAnService.getComboMonAnById(id);
        ComBoMonAn combomonan = monan.get();
        model.addAttribute("combo", combomonan);

        
        return "trangchu/combo";
    }
    
    // tìm kiếm
    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false) String query, Model model) {
        List<DoiTac> results;
        if (query != null && !query.isEmpty()) {
            results = doiTacService.searchByName(query);
            results.addAll(doiTacService.searchByFullAddress(query));
        } else {
            results = doiTacService.getAllDoitac();
        }
        model.addAttribute("doiTacs", results);
        return "trangchu/index";
    }

    //lọcs
    @GetMapping("/loc")
    public String loc(
            @RequestParam(value = "thanhpho", required = false) String thanhpho,
            @RequestParam(value = "hoadon", required = false) String hoadon,
            Model model) {

        // Thực hiện lọc dữ liệu
        List<DoiTac> doiTacs = doiTacService.locTheoTieuChi(thanhpho, hoadon);
        
        model.addAttribute("doiTacs", doiTacs);
        
        return "trangchu/index"; 
    }
    
    @GetMapping("/loc-theo-loai/{malnh}")
    public String locTheoLoai(@PathVariable("malnh") Long loaiNhaHangId, Model model, HttpSession session) {
        Long mand = (Long) session.getAttribute("mand");
        
        // Nếu người dùng đã đăng nhập (có mã người dùng)
        if (mand != null) {
            List<ThongBao> thongBaos = thongBaoService.getThongBaosByUserId(mand);
            model.addAttribute("thongBaos", thongBaos);
        }

        // Lấy danh sách đối tác theo mã loại nhà hàng
        List<DoiTac> doiTacs = doiTacService.findByLoaiNhaHang(loaiNhaHangId);
        model.addAttribute("doiTacs", doiTacs);

        // Lấy tất cả các loại nhà hàng
        List<LoaiNhaHang> loaiNhaHangs = loaiNhaHangService.getAllLoaiNhaHang();
        model.addAttribute("loainhahangs", loaiNhaHangs);

        return "trangchu/index"; 
    }

    

}
