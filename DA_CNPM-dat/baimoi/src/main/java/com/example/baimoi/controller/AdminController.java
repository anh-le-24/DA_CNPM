package com.example.baimoi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.NguoiDung;
import com.example.baimoi.service.ChiNhanhService;
import com.example.baimoi.service.ComBoMonAnService;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.DonDatBanService;
import com.example.baimoi.service.ImgDoiTacService;
import com.example.baimoi.service.NguoiDungService;
import com.example.baimoi.service.ThongBaoService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private DoiTacService doitacService;
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private ChiNhanhService chiNhanhService;
    @Autowired
    private ThongBaoService thongBaoService;
    @Autowired
    private ComBoMonAnService comBoMonAnService;
    @Autowired
    private DonDatBanService donDatBanService;
    @Autowired
    private ImgDoiTacService imgDoiTacService;


    @GetMapping("/tatcadoitac")
    public String listDoiTacAll(Model model) {
        List<DoiTac> doiTacList = doitacService.getAllDoitac();
        model.addAttribute("doiTacList", doiTacList);
        return "quanly/tatcadoitac";
    }

    @GetMapping("/chitiet/{madt}")
    public String getDoiTacById(@PathVariable("madt") Long madt, Model model) {
        DoiTac doiTac = doitacService.findByIdDT(madt);
        model.addAttribute("doiTac", doiTac);
        model.addAttribute("isview", true); 
        model.addAttribute("isEditing", false); 
        return "quanly/tatcadoitac";
    }

    //----------------------
    @GetMapping("/tatcadonxindk")
    public String listDoiTac(Model model) {
        List<DoiTac> doiTacList = doitacService.getAllDoitac();
        model.addAttribute("doiTacList", doiTacList);
        return "quanly/doitachtml";
    }

    @GetMapping("/details/{madt}")
    public String showDoiTacDetails(@PathVariable("madt") Long madt, Model model) {
        DoiTac doiTac = doitacService.findByIdDT(madt);
        model.addAttribute("doiTac", doiTac);
        return "quanly/doitachtml";
    }

    @GetMapping("/xacnhan/{id}")
    public String xacNhan(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        NguoiDung nguoiDung = nguoiDungService.findByIdND(id);
        if (nguoiDung != null) {
            nguoiDung.setMapq(3); 
            nguoiDungService.saveOrUpdate(nguoiDung);

            // Tạo thông báo cho người dùng
            thongBaoService.createThongBao(id, "Đăng ký đối tác thành công", "Tài khoản của bạn đã được xác nhận và phân quyền thành công.");

            redirectAttributes.addFlashAttribute("message", "Cập nhật mã phân quyền thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại!");
        }
        return "redirect:/admin/tatcadonxindk"; 
    }

    @PostMapping("/xoa/{madt}")
    public String xoaDoiTac(@PathVariable("madt") String madtstr, RedirectAttributes redirectAttributes) {
        try {
            Long madt = Long.valueOf(madtstr);
            DoiTac doiTac = doitacService.findByIdDT(madt);
            if (doiTac == null) {
                redirectAttributes.addFlashAttribute("message", "Đối tác không tồn tại");
                return "redirect:/admin/tatcadoitac";
            }

            // Lấy mã người dùng từ đối tác
            NguoiDung nguoiDung = doiTac.getNguoiDung();
            if (nguoiDung == null) {
                redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại");
                return "redirect:/admin/tatcadoitac";
            }

            // Xóa các thông tin liên quan đến đối tác
            chiNhanhService.deleteChiNhanh(madt);
            comBoMonAnService.deleteComboMonAn(madt);
            donDatBanService.deleteDonDatBanByDoiTacId(madt);
            imgDoiTacService.deleteById(madt);
            doitacService.deleteByIdDT(madt);

            // Cập nhật quyền của người dùng
            nguoiDung.setMapq(2); // Ví dụ: quyền mặc định hoặc quyền thấp hơn
            nguoiDungService.saveOrUpdate(nguoiDung);

            // Tạo thông báo cho người dùng
            thongBaoService.createThongBao(nguoiDung.getMand(), "Bạn mới bị trục xuất", "Tài khoản của bạn đã mất quyền đối tác");

            // Ghi log thông báo xóa thành công
            System.out.println("Đối tác với ID " + madt + " đã được xóa thành công.");

            // Thêm thông báo thành công
            redirectAttributes.addFlashAttribute("message", "Xóa đối tác thành công");

            return "redirect:/admin/tatcadoitac";
        } catch (Exception e) {
            // Ghi log lỗi
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Xóa thất bại: " + e.getMessage());
            return "redirect:/admin/tatcadoitac";
        }
    }


   


    @PostMapping("/delete/{madt}")
    public String deleteDoiTac(@PathVariable("madt") String madtStr) {
        Long madt = Long.valueOf(madtStr);
        Long manguoidung = doitacService.findMaNguoiDungByMaDoiTac(madt);
        doitacService.deleteByIdDT(madt);

        thongBaoService.createThongBao(manguoidung, "Xin đăng ký đối tác không thành công",
        "Nhà hàng của bạn chưa đạt đủ điều kiện để thành đối tác của chúng tôi! Cảm ơn.");

        return "redirect:/admin/tatcadonxindk";
    }

    @RequestMapping("/error")
    public String handleError() {
        // Trả về tên template error
        return "error";
    }
}
