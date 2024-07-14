package com.example.baimoi.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CHINHANH")
public class ChiNhanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long macn;
    private String tencn;
    private String sonhacn;
    private String duongcn;
    private String quancn;
    private String thanhphocn;
    private int sochongoi;
    private Long madt;

    // Kết nối với bảng đối tác
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madt", referencedColumnName = "madt",insertable=false, updatable=false)
    private DoiTac doiTac ;

    // kết nối với bảng đơn đặt bàn
    @OneToMany(mappedBy = "chiNhanh")
    private Set<DonDatBan> donDatBans;


    public ChiNhanh(String duongcn, Long macn, Long madt, String quancn, int sochongoi, String sonhacn, String tencn, String thanhphocn) {
        this.duongcn = duongcn;
        this.macn = macn;
        this.madt = madt;
        this.quancn = quancn;
        this.sochongoi = sochongoi;
        this.sonhacn = sonhacn;
        this.tencn = tencn;
        this.thanhphocn = thanhphocn;
    }

    public ChiNhanh() {
    }

    public Long getMacn() {
        return macn;
    }

    public void setMacn(Long macn) {
        this.macn = macn;
    }

    public String getTencn() {
        return tencn;
    }

    public void setTencn(String tencn) {
        this.tencn = tencn;
    }

    public String getSonhacn() {
        return sonhacn;
    }

    public void setSonhacn(String sonhacn) {
        this.sonhacn = sonhacn;
    }

    public String getDuongcn() {
        return duongcn;
    }

    public void setDuongcn(String duongcn) {
        this.duongcn = duongcn;
    }

    public String getQuancn() {
        return quancn;
    }

    public void setQuancn(String quancn) {
        this.quancn = quancn;
    }

    public String getThanhphocn() {
        return thanhphocn;
    }

    public void setThanhphocn(String thanhphocn) {
        this.thanhphocn = thanhphocn;
    }

    public int getSochongoi() {
        return sochongoi;
    }

    public void setSochongoi(int sochongoi) {
        this.sochongoi = sochongoi;
    }

    public Long getMadt() {
        return madt;
    }

    public void setMadt(Long madt) {
        this.madt = madt;
    }

    public String getFullAddress() {
        return sonhacn + ", " + duongcn + ", " + quancn + ", " + thanhphocn;
    }

    public DoiTac getDoiTac() {
        return doiTac;
    }

    public void setDoiTac(DoiTac doiTac) {
        this.doiTac = doiTac;
    }

    public Set<DonDatBan> getDonDatBans() {
        return donDatBans;
    }

    public void setDonDatBans(Set<DonDatBan> donDatBans) {
        this.donDatBans = donDatBans;
    }


}
