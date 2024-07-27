package com.example.baimoi.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "THONGBAO")
public class ThongBao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matb;
    private Long mand;
    private String tieude;
    private String noidung;
    private Date ngaygui;
    private Time thoigian;
    
    // Kết nối với bảng người dùng
    @ManyToOne
    @JoinColumn(name = "mand", insertable = false, updatable = false)
    private NguoiDung nguoiDung;

    // Kết nối với bảng người nhận thông báo
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "THONGBAO_NGUOINHAN",
        joinColumns = @JoinColumn(name = "matb"),
        inverseJoinColumns = @JoinColumn(name = "mand")
    )
    private Set<NguoiDung> thongbaNguoiDungs;

    public ThongBao() {
    }

    public ThongBao(Long matb, Long mand, String tieude, String noidung, Date ngaygui, Time thoigian, NguoiDung nguoiDung,
                    Set<NguoiDung> thongbaNguoiDungs) {
        this.matb = matb;
        this.mand = mand;
        this.tieude = tieude;
        this.noidung = noidung;
        this.ngaygui = ngaygui;
        this.thoigian = thoigian;
        this.nguoiDung = nguoiDung;
        this.thongbaNguoiDungs = thongbaNguoiDungs;
    }

    public Long getMatb() {
        return matb;
    }

    public void setMatb(Long matb) {
        this.matb = matb;
    }

    public Long getMand() {
        return mand;
    }

    public void setMand(Long mand) {
        this.mand = mand;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Date getNgaygui() {
        return ngaygui;
    }

    public void setNgaygui(Date ngaygui) {
        this.ngaygui = ngaygui;
    }

    public Time getThoigian() {
        return thoigian;
    }

    public void setThoigian(Time thoigian) {
        this.thoigian = thoigian;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public Set<NguoiDung> getThongbaNguoiDungs() {
        return thongbaNguoiDungs;
    }

    public void setThongbaNguoiDungs(Set<NguoiDung> thongbaNguoiDungs) {
        this.thongbaNguoiDungs = thongbaNguoiDungs;
    }
}
