package com.example.baimoi.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="TRANGTHAIDON")
public class TrangThaiDon {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long mattd;
    private String noidung;

    @OneToMany(mappedBy="trangThaiDon")
    private Set <DonDatBan> donDatBans;

    public TrangThaiDon() {
    }

    public TrangThaiDon(Long mattd, String noidung) {
        this.mattd = mattd;
        this.noidung = noidung;
    }

    public Long getMattd() {
        return mattd;
    }

    public void setMattd(Long mattd) {
        this.mattd = mattd;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Set<DonDatBan> getDonDatBans() {
        return donDatBans;
    }

    public void setDonDatBans(Set<DonDatBan> donDatBans) {
        this.donDatBans = donDatBans;
    }

    
}
