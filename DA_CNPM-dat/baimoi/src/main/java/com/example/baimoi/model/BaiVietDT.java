package com.example.baimoi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BAIVIETDT")
public class BaiVietDT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mabv;
    private String ndtomtat;
    private String ndbanggia;
    private String ndquydinh;
    private String nddexe;
    private String ndtienich;
    private String ndchitiet;

        // Kết nối với bảng Đối Tác
    @OneToOne(mappedBy = "baiVietDT")
    private DoiTac doiTac;
   

    public BaiVietDT() {
    }

    public BaiVietDT(Long mabv, String ndbanggia, String ndchitiet, String nddexe, String ndquydinh, String ndtienich, String ndtomtat) {
        this.mabv = mabv;
        this.ndbanggia = ndbanggia;
        this.ndchitiet = ndchitiet;
        this.nddexe = nddexe;
        this.ndquydinh = ndquydinh;
        this.ndtienich = ndtienich;
        this.ndtomtat = ndtomtat;
    }

    public Long getMaBV() {
        return mabv;
    }

    public void setMaBV(Long mabv) {
        this.mabv = mabv;
    }

    public String getNdtomtat() {
        return ndtomtat;
    }

    public void setNdtomtat(String ndtomtat) {
        this.ndtomtat = ndtomtat;
    }

    public String getNdbanggia() {
        return ndbanggia;
    }

    public void setNdbanggia(String ndbanggia) {
        this.ndbanggia = ndbanggia;
    }

    public String getNdquydinh() {
        return ndquydinh;
    }

    public void setNdquydinh(String ndquydinh) {
        this.ndquydinh = ndquydinh;
    }

    public String getNddexe() {
        return nddexe;
    }

    public void setNddexe(String nddexe) {
        this.nddexe = nddexe;
    }

    public String getNdtienich() {
        return ndtienich;
    }

    public void setNdtienich(String ndtienich) {
        this.ndtienich = ndtienich;
    }

    public String getNdchitiet() {
        return ndchitiet;
    }

    public void setNdchitiet(String ndchitiet) {
        this.ndchitiet = ndchitiet;
    }

    public DoiTac getDoiTac() {
        return doiTac;
    }

    public void setDoiTac(DoiTac doiTac) {
        this.doiTac = doiTac;
    }

}
