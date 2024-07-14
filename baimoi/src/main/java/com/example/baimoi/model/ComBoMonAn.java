package com.example.baimoi.model;

import java.text.DecimalFormat;
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
@Table(name = "COMBOMONAN")
public class ComBoMonAn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long macb;
    private String tencb;
    private String mota;
    private double giatien;
    private int solgngdung;
    private Long madt;

     // Kết nối với bảng đối tác
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "madt", referencedColumnName = "madt",insertable=false, updatable=false)
    private DoiTac doiTac;

    // kết nối với bảng don dat ban
    @OneToMany(mappedBy = "comBoMonAn")
    private Set<DonDatBan> donDatBans;

    public ComBoMonAn() {
    }

    public ComBoMonAn(double giatien, Long macb, Long madt, String mota, int solgngdung, String tencb) {
        this.giatien = giatien;
        this.macb = macb;
        this.madt = madt;
        this.mota = mota;
        this.solgngdung = solgngdung;
        this.tencb = tencb;
    }

    public Long getMacb() {
        return macb;
    }

    public void setMacb(Long macb) {
        this.macb = macb;
    }

    public String getTencb() {
        return tencb;
    }

    public void setTencb(String tencb) {
        this.tencb = tencb;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public double getGiatien() {
        return giatien;
    }

    public void setGiatien(double giatien) {
        this.giatien = giatien;
    }

    public int getSolgngdung() {
        return solgngdung;
    }

    public void setSolgngdung(int solgngdung) {
        this.solgngdung = solgngdung;
    }

    public Long getMadt() {
        return madt;
    }

    public void setMadt(Long madt) {
        this.madt = madt;
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
    public String soTienDepHon() {
        return formatCurrency(this.giatien);
    }

    public static String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(amount);
    }
}
