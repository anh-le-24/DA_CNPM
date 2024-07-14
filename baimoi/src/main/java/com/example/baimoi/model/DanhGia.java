package com.example.baimoi.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DANHGIA")
public class DanhGia {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY )
    private Long madg;
    private int sosao;
    private String binhluan;
    private String lydohuy;
    private Date ngaydg;

    // Kết nối với bảng Danh gia
    @OneToOne(mappedBy = "danhGia")
    private DonDatBan donDatBan;
   
    public DanhGia() {
    }

    public DanhGia(String binhluan, String lydohuy, Long madg, Date ngaydg, int sosao) {
        this.binhluan = binhluan;
        this.lydohuy = lydohuy;
        this.madg = madg;
        this.ngaydg = ngaydg;
        this.sosao = sosao;
    }

    public Long getMadg() {
        return madg;
    }

    public void setMadg(Long madg) {
        this.madg = madg;
    }

    public int getSosao() {
        return sosao;
    }

    public void setSosao(int sosao) {
        this.sosao = sosao;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public String getLydohuy() {
        return lydohuy;
    }

    public void setLydohuy(String lydohuy) {
        this.lydohuy = lydohuy;
    }

    public Date getNgaydg() {
        return ngaydg;
    }

    public void setNgaydg(Date ngaydg) {
        this.ngaydg = ngaydg;
    }

    public DonDatBan getDonDatBan() {
        return donDatBan;
    }

    public void setDonDatBan(DonDatBan donDatBan) {
        this.donDatBan = donDatBan;
    }
    

    
}
