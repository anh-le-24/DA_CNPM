package com.example.baimoi.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.baimoi.model.BaiVietDT;
import com.example.baimoi.model.ChiNhanh;
import com.example.baimoi.model.ComBoMonAn;
import com.example.baimoi.model.DanhGia;
import com.example.baimoi.model.DichVuCC;
import com.example.baimoi.model.DoiTac;
import com.example.baimoi.model.DonDatBan;
import com.example.baimoi.model.ImgDoiTac;
import com.example.baimoi.model.LoaiNhaHang;
import com.example.baimoi.service.BaiVietDTService;
import com.example.baimoi.service.ChiNhanhService;
import com.example.baimoi.service.ComBoMonAnService;
import com.example.baimoi.service.DanhGiaService;
import com.example.baimoi.service.DichVuCCService;
import com.example.baimoi.service.DoiTacService;
import com.example.baimoi.service.DonDatBanService;
import com.example.baimoi.service.ImgDoiTacService;
import com.example.baimoi.service.LoaiNhaHangService;
import com.example.baimoi.service.StorageService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/doitac")
public class DoiTacController {

    @Autowired
    private DoiTacService doiTacService;
    @Autowired
    private DichVuCCService dichVuCCService;
    @Autowired
    private LoaiNhaHangService loaiNhaHangService;
    @Autowired
    private StorageService storageService;

    @GetMapping("/{id}")
    public String getViewDoiTac(@PathVariable("id") Long id, Model model) {
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        return "/doitac/maindoitac";
    }

    @GetMapping("/ctdoitac/{id}")
    public String getDoiTacById(@PathVariable("id") Long id, Model model) {
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        
        return "/doitac/ctdoitac";
    }

    // Chỉnh sửa thông tin 
    @Transactional
    @GetMapping("/ctdoitac/csdoitac/{id}")
    public String getViewChinhsuaDT(@PathVariable("id") Long id, Model model) {
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
        model.addAttribute("dichvuccs", dichVuCCService.getAllDichVuCC());
        return "/doitac/csdoitac";
    }
    @Transactional
    @PostMapping("/update")
    public String updateDoiTac(@Valid @ModelAttribute("doiTac") DoiTac doiTac,
                           BindingResult bindingResult,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam("giomo") String giomo,
                           @RequestParam("giodong") String giodong,
                           @RequestParam("madv") String madvStr,
                           @RequestParam("ngaydk") String ngaydk,
                           @RequestParam(value = "selectedLoaiNhaHangs", required = false) List<String> selectedLoaiNhaHangs) throws IOException, ParseException {
    
    if (bindingResult.hasErrors()) {
        return "doitac/csdoitac";
    }

    Optional<DoiTac> optionalDoiTac = doiTacService.getDoiTacById(doiTac.getMadt());
    if (!optionalDoiTac.isPresent()) {
        throw new EntityNotFoundException("Không tìm thấy đối tác có id: " + doiTac.getMadt());
    }

    DoiTac existingDoiTac = optionalDoiTac.get();

    // Cập nhật các thuộc tính của DoiTac từ form
    existingDoiTac.setTennhahang(doiTac.getTennhahang());
    existingDoiTac.setSonhanh(doiTac.getSonhanh());
    existingDoiTac.setDuongnh(doiTac.getDuongnh());
    existingDoiTac.setQuannh(doiTac.getQuannh());
    existingDoiTac.setSochongoi(doiTac.getSochongoi());
    existingDoiTac.setThanhphonh(doiTac.getThanhphonh());
    existingDoiTac.setMota(doiTac.getMota());
    existingDoiTac.setLinklienket(doiTac.getLinklienket());
    existingDoiTac.setLinkggmap(doiTac.getLinkggmap());
    existingDoiTac.setHoadontb(doiTac.getHoadontb());

    // Xử lý file ảnh
    if (!file.isEmpty()) {
        this.storageService.store(file);
        existingDoiTac.setImgmota(file.getOriginalFilename());
    }

    // Xử lý giờ mở cửa và đóng cửa
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Time giomoTime = new Time(format.parse(giomo).getTime());
    Time giodongTime = new Time(format.parse(giodong).getTime());
    existingDoiTac.setGiomo(giomoTime);
    existingDoiTac.setGiodong(giodongTime);

    // Xử lý ngày đăng ký
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(ngaydk, formatter);
    Date sqlDate = Date.valueOf(localDate);
    existingDoiTac.setNgaydk(sqlDate);

    // Xử lý dịch vụ cung cấp
    Long madv = Long.valueOf(madvStr);
    DichVuCC dichVuCC = dichVuCCService.getDichVuCCById(madv).orElse(null);
    existingDoiTac.setDichVuCC(dichVuCC);

    // Cập nhật loại nhà hàng
    if (selectedLoaiNhaHangs != null && !selectedLoaiNhaHangs.isEmpty()) {
        Set<LoaiNhaHang> loaiNhaHangs = new HashSet<>();
        for (String malnhStr : selectedLoaiNhaHangs) {
            try {
                Long malnh = Long.valueOf(malnhStr);
                LoaiNhaHang loaiNhaHang = loaiNhaHangService.getLoaiNhaHangById(malnh).orElse(null);
                if (loaiNhaHang != null) {
                    loaiNhaHangs.add(loaiNhaHang);
                }
            } catch (NumberFormatException e) {
                // Xử lý nếu chuỗi không thể chuyển đổi thành Long
                System.out.println("Không thể chuyển đổi " + malnhStr + " thành Long");
            }
        }
        existingDoiTac.setLoaiNhaHangs(loaiNhaHangs);
    } else {
        existingDoiTac.getLoaiNhaHangs().clear();
    }

    doiTacService.saveDoiTac(existingDoiTac);

    return "redirect:/doitac/ctdoitac/" + existingDoiTac.getMadt();
}
    
    // -----Đăng ký đối tác --------//
    @Transactional
    @PostMapping("/save")
    public String saveDoiTac(@Valid @ModelAttribute("doitac") DoiTac doitac,
                             BindingResult bindingResult,
                             @RequestParam("giomo") String giomo,
                             @RequestParam("giodong") String giodong,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("madv") Long madv
                             ) throws IOException, ParseException {

        if (bindingResult.hasErrors()) {
            return "dangkydoitac";
        }

        this.storageService.store(file);
        doitac.setImgmota(file.getOriginalFilename());

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Time giomoTime = new Time(format.parse(giomo).getTime());
        Time giodongTime = new Time(format.parse(giodong).getTime());
        doitac.setGiomo(giomoTime);
        doitac.setGiodong(giodongTime);

        Date ngayHienTai = new Date(Calendar.getInstance().getTimeInMillis());
        doitac.setNgaydk(ngayHienTai);

        DichVuCC dichVuCC = dichVuCCService.getDichVuCCById(madv).orElse(null);
        doitac.setDichVuCC(dichVuCC);

        Long mand = (long) 2002;
        doitac.setMand(mand);

        doiTacService.saveDoiTac(doitac);

        return "redirect:/doitac";
    }

    @Transactional
    @GetMapping("/dangkydoitac")
    public String getViewDangKyDoiTac(Model model) {
        model.addAttribute("doitac", new DoiTac());
        model.addAttribute("dichvuccs", dichVuCCService.getAllDichVuCC());
        model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
        return "dangkydoitac";
    }

    //------------ Quản lý đơn đặt bàn---------//

    @Autowired
    private DonDatBanService donDatBanService;
    @Autowired
    private DanhGiaService danhGiaService;

    @Transactional
    @GetMapping("/quanlydon/qldonchoxn/{id}")
    public String getViewQlDonChoXN(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());

        return "/doitac/qldondatban/qldonchoxn";
    }

    @Transactional
    @PostMapping("/quanlydon/capnhat")
    public String capNhatTrangThaiDon(@RequestParam("maddb") Long maddb,
                                      @RequestParam("madt") Long madt,
                                      @RequestParam("trangThai") int trangThai,
                                      @RequestParam(value = "lyDo", required = false) String lyDo,
                                      @RequestParam(value = "soTien", required = false) String soTien) {
        
        Optional<DonDatBan> optionalDonDatBan = donDatBanService.getDonDatBanById(maddb);
        if (!optionalDonDatBan.isPresent()) {
            throw new EntityNotFoundException("Không tìm thấy Don: ");
        }
        DonDatBan donDatBan = optionalDonDatBan.get();
    
        Long matt = (long) trangThai;
        donDatBan.setMattd(matt);

        donDatBanService.saveDonDatBan(donDatBan);

        if (matt == 4 && lyDo != null && !lyDo.isEmpty()) {
            DanhGia danhGia = donDatBan.getDanhGia();
            if (danhGia == null) {
                danhGia = new DanhGia();
                danhGia.setDonDatBan(donDatBan);
                donDatBan.setDanhGia(danhGia);
            }
            danhGia.setLydohuy(lyDo);

            Date ngayHienTai = new Date(Calendar.getInstance().getTimeInMillis());
            danhGia.setNgaydg(ngayHienTai);
            danhGia.setSosao(0);
            danhGia.setBinhluan("");

            danhGiaService.saveDanhGia(danhGia);
            
            // Cập nhật DonDatBan với DanhGia đã được lưu
            donDatBan.setDanhGia(danhGia);
            donDatBan.setMadg(danhGia.getMadg());
            donDatBanService.saveDonDatBan(donDatBan);
        }
        if (matt == 3) {
            double soTienDouble = Double.parseDouble(soTien);
            donDatBan.setSotien(soTienDouble);
        }

        return "redirect:/doitac/quanlydon/qldonchoxn/" + madt;
    }

    @Transactional
    @GetMapping("/quanlydon/qldondaxn/{id}")
    public String getViewQlDonDaXN(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        return "/doitac/qldondatban/qldondaxn";
    }

    @Transactional
    @GetMapping("/quanlydon/qldondaht/{id}")
    public String getViewQlDonDaHT(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());

        return "/doitac/qldondatban/qldondaht";
    }

    @Transactional
    @GetMapping("/quanlydon/qldonhuy/{id}")
    public String getViewQlDonHuy(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        return "/doitac/qldondatban/qldonhuy";
    }

    //-------- quản lý thông tin đối tác -----------//

    @Autowired
    private BaiVietDTService baiVietDTService;
    //----Quản lý bài viết -------//
    @Transactional
    @GetMapping("/qlbaiviet/{id}")
    public String getViewQlBaiViet(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        model.addAttribute("baiVietDT", new BaiVietDT());

        return "/doitac/qlthongtinnh/qlbaiviet";
    }

    @Transactional
    @PostMapping("/baiviet/save")
    public String saveBaiVietDT(@Valid @ModelAttribute("baiVietDT") BaiVietDT baiVietDT, 
                                BindingResult bindingResult,
                                @RequestParam("madt") String doiTacIdStr
                                ) throws IOException, ParseException {
        if (bindingResult.hasErrors()) {
            return "/doitac/qlthongtinnh/qlbaiviet";
        }
        
        Long doiTacId = Long.valueOf(doiTacIdStr.trim());
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(doiTacId);
        DoiTac doiTac = doiTacOpt.get();

        Long maBVXoa = doiTac.getMabv();
        baiVietDT.setDoiTac(doiTac);
        BaiVietDT savedBaiViet = baiVietDTService.saveBaiVietDT(baiVietDT);
        
        doiTac.setBaiVietDT(savedBaiViet);
        doiTac.setMabv(savedBaiViet.getMaBV());
        doiTacService.saveDoiTac(doiTac);
        
        baiVietDTService.deleteBaiVietDT(maBVXoa);
        return "redirect:/doitac/qlbaiviet/" + doiTacId;
    }
    // --------- Thêm Ảnh cho đối tác---------//
   
    @Autowired
    private ImgDoiTacService imgDoiTacService;

    @Transactional
    @GetMapping("/{id}/images")
    public String showImagesForm(@PathVariable("id") Long id, Model model) {
        DoiTac doiTac = doiTacService.getDoiTacWithImages(id);
        model.addAttribute("doiTac", doiTac);
        model.addAttribute("images", doiTac.getImgDoiTacs());
        return "/doitac/qlthongtinnh/images";
    }

    @Transactional
    @PostMapping("/{id}/images")
    public String uploadImages(@PathVariable("id") Long id, 
                               @RequestParam("files") MultipartFile[] files
                               ) throws IOException, ParseException {
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(id);
        if (!doiTacOpt.isPresent()) {
            return "redirect:/doitac";
        }
        DoiTac doiTac = doiTacOpt.get();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = storageService.store(file);
                ImgDoiTac imgDoiTac = new ImgDoiTac();
                imgDoiTac.setImg(fileName);
                imgDoiTacService.save(imgDoiTac);
                doiTac.getImgDoiTacs().add(imgDoiTac);
                imgDoiTac.setMadt(id);
            }
        }
        
        doiTacService.saveDoiTac(doiTac);
        return "redirect:/doitac/" + id + "/images";
    }

    @Transactional
    @PostMapping("/{doiTacId}/images/{imageId}/delete")
    public String deleteImage(@PathVariable("doiTacId") Long doiTacId,
                              @PathVariable("imageId") Long imageId) {
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(doiTacId);
        if (doiTacOpt.isPresent()) {
            DoiTac doiTac = doiTacOpt.get();
            doiTac.getImgDoiTacs().removeIf(img -> img.getMaimg().equals(imageId));
            doiTacService.saveDoiTac(doiTac);
            imgDoiTacService.deleteById(imageId);
        }
        return "redirect:/doitac/" + doiTacId + "/images";
    }


    //---- Quản lý Chi nhánh -----//

    @Autowired
    private ChiNhanhService chiNhanhService;
    @Autowired
    private ComBoMonAnService comBoMonAnService;
    
    @Transactional
    @GetMapping("/qlchinhanh/{id}")
    public String getViewQlChiNhanh(@PathVariable("id") Long id, Model model) {
        Optional<DoiTac> doiTacOtp = doiTacService.getDoiTacById(id);
        if (!doiTacOtp.isPresent()) {
            return "redirect:/doitac";
        }
        DoiTac doiTac = doiTacOtp.get();
        model.addAttribute("doiTac", doiTac);

        Set<ChiNhanh> chiNhanhs = doiTac.getChiNhanhs();
        model.addAttribute("chiNhanhs", chiNhanhs);
        model.addAttribute("chiNhanh", new ChiNhanh());
        return "doitac/qlthongtinnh/qlchinhanh";
    }
    @Transactional
    @PostMapping("/qlchinhanh/save")
    public String saveChiNhanh(@ModelAttribute ChiNhanh chiNhanh,
                                @RequestParam("madt") String doiTacIdStr
                                 ) throws IOException, ParseException {

        Long doiTacId = Long.valueOf(doiTacIdStr.trim());
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(doiTacId);
        DoiTac doiTac = doiTacOpt.get();

        chiNhanh.setDoiTac(doiTac);
        chiNhanhService.saveChiNhanh(chiNhanh);
        return "redirect:/doitac/qlchinhanh/" + doiTac.getMadt();
    }

    @Transactional
    @GetMapping("/qlchinhanh/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional <ChiNhanh> chiNhanhOtp = chiNhanhService.getChiNhanhById(id);
        ChiNhanh chiNhanh = chiNhanhOtp.get();
        
        DoiTac doiTac = chiNhanh.getDoiTac();

        model.addAttribute("doiTac", doiTac);
        model.addAttribute("chiNhanh", chiNhanh);
        return "doitac/qlthongtinnh/qlchinhanh";
    }

    @Transactional
    @GetMapping("/qlchinhanh/delete/{id}")
    public String deleteChiNhanhCtrl(@PathVariable Long id) {
        chiNhanhService.deleteChiNhanh(id);    
        // Optional <ChiNhanh> chiNhanhOtp = chiNhanhService.getChiNhanhById(id);
        // System.out.println(id);
        // if (!chiNhanhOtp.isPresent()) {
        //     System.out.println("Không tìm thấy chi nhánh với id: " + id);
        //     return "redirect:/error";
        // }
        // ChiNhanh chiNhanh =chiNhanhOtp.get();
        // DoiTac doiTac = chiNhanh.getDoiTac();
        // Long doiTacId = doiTac.getMadt();
           
        return "redirect:/doitac/qlchinhanh/";
    }

    //---- Quản lý ComBo -----//
    @Transactional
    @GetMapping("/qlcombo/{id}")
    public String getViewQlCombo(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());

        model.addAttribute("comBoMonAn", new ComBoMonAn());
        return "/doitac/qlthongtinnh/qlcombo";
    }

    @Transactional
    @PostMapping("/qlcombo/save")
    public String saveCombo(@ModelAttribute ComBoMonAn comBoMonAn,
                                @RequestParam("madt") String doiTacIdStr
                                 ) throws IOException, ParseException {

        Long doiTacId = Long.valueOf(doiTacIdStr.trim());
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(doiTacId);
        DoiTac doiTac = doiTacOpt.get();

        comBoMonAn.setDoiTac(doiTac);
        comBoMonAnService.saveComboMonAn(comBoMonAn);
        return "redirect:/doitac/qlcombo/" + doiTac.getMadt();
    }

    @Transactional
    @GetMapping("/qlcombo/edit/{id}")
    public String showEditFormCombo(@PathVariable Long id, Model model) {
        Optional <ComBoMonAn> comBoMonAnOtp = comBoMonAnService.getComboMonAnById(id);
        ComBoMonAn comBoMonAn = comBoMonAnOtp.get();
        
        DoiTac doiTac = comBoMonAn.getDoiTac();

        model.addAttribute("doiTac", doiTac);
        model.addAttribute("comBoMonAn", comBoMonAn);
        return "doitac/qlthongtinnh/qlcombo";
    }

    @Transactional
    @GetMapping("")
    public String deleteComboCtrl(@PathVariable Long id) {
        comBoMonAnService.deleteComboMonAn(id);    
        return "redirect:/doitac/qlchinhanh/";
    }


    @Transactional
    @GetMapping("/showctnhahang/{id}")
    public String getViewemock(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        return "showctnhahang";
    }

    
}
