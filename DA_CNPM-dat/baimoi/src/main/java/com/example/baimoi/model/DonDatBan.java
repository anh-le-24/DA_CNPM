package com.example.baimoi.model;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DONDATBAN")
public class DonDatBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maddb;
    private Long mand;
    private Long madt;
    private Date ngaydat;
    private Time thoigiandat;
    private int solgnguoi;
    private String yeucaudacbiet;
    private String ghichu;
    private double sotien;
    private Long macn;
    private Long mattd;
    private Long macb;
    private Long madg;
    // Kết nối với bảng đối tác
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "madt", referencedColumnName = "madt",insertable=false, updatable=false)
    private DoiTac doiTac;

    // Kết nối với bảng combo mon an
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "macb", referencedColumnName = "macb",insertable=false, updatable=false)
    private ComBoMonAn comBoMonAn;

    // Kết nối với bảng nguoi dung
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mand", referencedColumnName = "mand",insertable=false, updatable=false)
    private NguoiDung nguoiDung;

    // Kết nối với bảng Trang Thai don hang
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mattd", referencedColumnName = "mattd",insertable=false, updatable=false )
    private TrangThaiDon trangThaiDon;

    // Kết nối với bảng Chi nhanh
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "macn", referencedColumnName = "macn",insertable=false, updatable=false )
    private ChiNhanh chiNhanh;

    // Kết nối với bảng Danh gia
    @OneToOne
    @JoinColumn(name = "madg", referencedColumnName = "madg", insertable = false, updatable = false)
    private DanhGia danhGia;
 
    public DanhGia getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(DanhGia danhGia) {
        this.danhGia = danhGia;
    }

    public DonDatBan() {
    }

    public DonDatBan(String ghichu, Long macb, Long macn, Long maddb, Long madt, Long mand, Long mattd, Date ngaydat, int solgnguoi, double sotien, Time thoigiandat, String yeucaudacbiet, Long madg) {
        this.ghichu = ghichu;
        this.macb = macb;
        this.macn = macn;
        this.maddb = maddb;
        this.madt = madt;
        this.mand = mand;
        this.mattd = mattd;
        this.ngaydat = ngaydat;
        this.solgnguoi = solgnguoi;
        this.sotien = sotien;
        this.thoigiandat = thoigiandat;
        this.yeucaudacbiet = yeucaudacbiet;
        this.madg = madg;
    }

    public Long getMaddb() {
        return maddb;
    }

    public void setMaddb(Long maddb) {
        this.maddb = maddb;
    }

    public Long getMand() {
        return mand;
    }

    public void setMand(Long mand) {
        this.mand = mand;
    }

    public Long getMadt() {
        return madt;
    }

    public void setMadt(Long madt) {
        this.madt = madt;
    }

    public Date getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(Date ngaydat) {
        this.ngaydat = ngaydat;
    }

    public Time getThoigiandat() {
        return thoigiandat;
    }

    public void setThoigiandat(Time thoigiandat) {
        this.thoigiandat = thoigiandat;
    }

    public int getSolgnguoi() {
        return solgnguoi;
    }

    public void setSolgnguoi(int solgnguoi) {
        this.solgnguoi = solgnguoi;
    }

    public String getYeucaudacbiet() {
        return yeucaudacbiet;
    }

    public void setYeucaudacbiet(String yeucaudacbiet) {
        this.yeucaudacbiet = yeucaudacbiet;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public double getSotien() {
        return sotien;
    }

    public void setSotien(double sotien) {
        this.sotien = sotien;
    }

    public Long getMacn() {
        return macn;
    }

    public void setMacn(Long macn) {
        this.macn = macn;
    }

    public Long getMattd() {
        return mattd;
    }

    public void setMattd(Long mattd) {
        this.mattd = mattd;
    }

    public Long getMacb() {
        return macb;
    }

    public void setMacb(Long macb) {
        this.macb = macb;
    }

    public DoiTac getDoiTac() {
        return doiTac;
    }

    public void setDoiTac(DoiTac doiTac) {
        this.doiTac = doiTac;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public ComBoMonAn getComBoMonAn() {
        return comBoMonAn;
    }

    public void setComBoMonAn(ComBoMonAn comBoMonAn) {
        this.comBoMonAn = comBoMonAn;
    }

    public TrangThaiDon getTrangThaiDon() {
        return trangThaiDon;
    }

    public void setTrangThaiDon(TrangThaiDon trangThaiDon) {
        this.trangThaiDon = trangThaiDon;
    }

    public ChiNhanh getChiNhanh() {
        return chiNhanh;
    }

    public void setChiNhanh(ChiNhanh chiNhanh) {
        this.chiNhanh = chiNhanh;
    }

    public Long getMadg() {
        return madg;
    }

    public void setMadg(Long madg) {
        this.madg = madg;
    }
    public String soTienDepHon() {
        return formatCurrency(this.sotien);
    }

    public static String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(amount);
    }
}