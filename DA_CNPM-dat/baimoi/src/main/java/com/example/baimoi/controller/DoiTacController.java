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
import java.util.Map;
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
import com.example.baimoi.service.ThongBaoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    private ThongBaoService thongBaoService;

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
                         @RequestParam("madv") Long madv,
                         HttpSession session
                         ) throws IOException, ParseException {

    if (bindingResult.hasErrors()) {
        return "dangkydoitac";
    }

    this.storageService.store(file);
    doitac.setImgmota(file.getOriginalFilename());

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    Time giomoTime = new Time(format.parse(giomo).getTime());
    doitac.setGiomo(giomoTime);
    Time giodongTime = new Time(format.parse(giodong).getTime());
    doitac.setGiodong(giodongTime);

    Date ngayHienTai = new Date(Calendar.getInstance().getTimeInMillis());
    doitac.setNgaydk(ngayHienTai);

    DichVuCC dichVuCC = dichVuCCService.getDichVuCCById(madv).orElse(null);
    doitac.setDichVuCC(dichVuCC);

    Long mand = null;
    Object mandObj = session.getAttribute("mand");
    if (mandObj != null) {
        mand = ((Long) mandObj);
    } else {
        // Xử lý khi không có giá trị trong session
        // Ví dụ: trả về lỗi hoặc redirect đến trang khác
        return "redirect:/error"; // Hoặc trả về một thông báo lỗi tùy chỉnh
    }
    doitac.setMand(mand);

    doiTacService.saveDoiTac(doitac);

    thongBaoService.createThongBao(mand, "Đang chờ xác nhận",
     "Chúng tôi đã nhận được đơn đăng ký của bạn, chúng tôi sẽ liên hệ lại với bạn sớm nhất có thể.");
    return "redirect:/trangchu";
}
@Transactional
@GetMapping("/dangkydoitac")
public String getViewDangKyDoiTac(Model model) {
    model.addAttribute("doitac", new DoiTac());
    model.addAttribute("dichvuccs", dichVuCCService.getAllDichVuCC());
    model.addAttribute("loainhahangs", loaiNhaHangService.getAllLoaiNhaHang());
    return "dangkydoitac";
}
@GetMapping("/login-required")
public String getViewDangKyDoiTac(){
    return "required";
}

    //------------ Quản lý đơn đặt bàn---------//

    @Autowired
    private DonDatBanService donDatBanService;
    @Autowired
    private DanhGiaService danhGiaService;

    @GetMapping("/quanlydon/qldonchoxn/{id}")
    public String getViewQlDonChoXN(@PathVariable("id") Long id,Model model){
        
        List<DonDatBan> donDatBans = donDatBanService.getDonDatBansForDoiTac(id);
        model.addAttribute("donDatBans", donDatBans);

        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());

        
        return "doitac/qldondatban/qldonchoxn";
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

        // Lấy mã người dùng từ Đơn Đặt Bàn
        Long mand = donDatBan.getNguoiDung().getMand();
        if (mand == null) {
            throw new RuntimeException("Không tìm thấy mã người dùng trong Đơn Đặt Bàn.");
        }

    
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

             // Tạo thông báo cho người dùng
             thongBaoService.createThongBao(mand, "Đơn đặt bản bị hủy", "Đơn đặt bàn của bạn đã bị hủy!");

        }
        if (matt == 3) {
            double soTienDouble = Double.parseDouble(soTien);
            donDatBan.setSotien(soTienDouble);
        }
        if (matt == 2){
            // Tạo thông báo cho người dùng
            thongBaoService.createThongBao(mand, "Xác nhận đơn đặt bàn", "Đơn đặt bàn của bạn đã được nhà hàng xác nhận!");
        }

        return "redirect:/doitac/quanlydon/qldonchoxn/" + madt;
    }

    @Transactional
    @GetMapping("/quanlydon/qldondaxn/{id}")
    public String getViewQlDonDaXN(@PathVariable("id") Long id,Model model){
        List<DonDatBan> donDatBans = donDatBanService.getDonDatBansForDoiTac(id);
        model.addAttribute("donDatBans", donDatBans);

        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());

        return "/doitac/qldondatban/qldondaxn";
    }

    @Transactional
    @GetMapping("/quanlydon/qldondaht/{id}")
    public String getViewQlDonDaHT(@PathVariable("id") Long id,Model model){
        List<DonDatBan> donDatBans = donDatBanService.getDonDatBansForDoiTac(id);
        model.addAttribute("donDatBans", donDatBans);

        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());


        return "/doitac/qldondatban/qldondaht";
    }

    @Transactional
    @GetMapping("/quanlydon/qldonhuy/{id}")
    public String getViewQlDonHuy(@PathVariable("id") Long id,Model model){
        List<DonDatBan> donDatBans = donDatBanService.getDonDatBansForDoiTac(id);
        model.addAttribute("donDatBans", donDatBans);

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
                                @RequestParam("madt") String doiTacIdStr) throws IOException, ParseException {
        if (bindingResult.hasErrors()) {
            return "/doitac/qlthongtinnh/qlbaiviet";
        }

        Long doiTacId = Long.valueOf(doiTacIdStr.trim());
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(doiTacId);
        DoiTac doiTac = doiTacOpt.orElseThrow(() -> new RuntimeException("Không tìm thấy đối tác"));
        
        Long maBVXoa = doiTac.getMabv();
        baiVietDT.setDoiTac(doiTac);
        BaiVietDT savedBaiViet = baiVietDTService.saveBaiVietDT(baiVietDT);
        doiTac.setBaiVietDT(savedBaiViet);
        doiTac.setMabv(savedBaiViet.getMaBV());
        doiTacService.saveDoiTac(doiTac);
        
        // Kiểm tra nếu maBVXoa không null và khác với mã bài viết mới
        if (maBVXoa != null && !maBVXoa.equals(savedBaiViet.getMaBV())) {
            baiVietDTService.deleteBaiVietDT(maBVXoa);
        }
    
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
    @GetMapping("/chinhanh/delete/{id}")
    public String deleteChiNhanhCtrl(@PathVariable Long id) {
        
        Optional <ChiNhanh> chiNhanhOtp = chiNhanhService.getChiNhanhById(id);
        ChiNhanh chiNhanh = chiNhanhOtp.get();
        
        DoiTac doiTac = chiNhanh.getDoiTac();
        chiNhanhService.deleteChiNhanh(id);    
        return "redirect:/doitac/qlchinhanh/" + doiTac.getMadt();
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
                                @RequestParam("madt") String doiTacIdStr,
                                @RequestParam("file") MultipartFile file
                                 ) throws IOException, ParseException {

        Long doiTacId = Long.valueOf(doiTacIdStr.trim());
        Optional<DoiTac> doiTacOpt = doiTacService.getDoiTacById(doiTacId);
        DoiTac doiTac = doiTacOpt.get();

        comBoMonAn.setDoiTac(doiTac);
        this.storageService.store(file);
        comBoMonAn.setImg(file.getOriginalFilename());
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
    @GetMapping("/qlcombo/delete/{id}")
    public String deleteComboCtrl(@PathVariable Long id) {
           
        Optional <ComBoMonAn> comBoMonAnOtp = comBoMonAnService.getComboMonAnById(id);
        ComBoMonAn comBoMonAn = comBoMonAnOtp.get();
        
        DoiTac doiTac = comBoMonAn.getDoiTac();
        comBoMonAnService.deleteComboMonAn(id); 
        return "redirect:/doitac/qlcombo/" + doiTac.getMadt();
    }


    @Transactional
    @GetMapping("/showctnhahang/{id}")
    public String getViewemock(@PathVariable("id") Long id,Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        model.addAttribute("doiTac", doiTac.get());
        return "showctnhahang";
    } 

    //--------- Thống kê ------------//

    @GetMapping("/thongke/{id}")
    public String getViewThongKe(@PathVariable("id") Long id, Model model){
        Optional<DoiTac> doiTac = doiTacService.getDoiTacById(id);
        if (doiTac.isPresent()) {
            model.addAttribute("doiTac", doiTac.get());
            
            // Fetch statistics data
            List<Map<String, Object>> monthlyStats = donDatBanService.getMonthlyStatsForDoiTac(id);
            model.addAttribute("monthlyStats", monthlyStats);
        }
        
        return"/doitac/thongkedoitac";
    }

    // Tìm kiếm
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
        return "/doitac/qldondatban/qldondaht";
    }
}
