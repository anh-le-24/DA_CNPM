package com.example.baimoi.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="DICHVUCC")
public class DichVuCC {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY )
    private Long madv;
    private String noidung;
    private int soluong;
    private double giadv;
    
    @OneToMany(mappedBy = "dichVuCC",cascade = CascadeType.ALL)
    private Set<DoiTac> doiTacs;

    public DichVuCC() {
    }
    
    public DichVuCC(Long madv, String noidung, int soluong, double giadv) {
        this.madv = madv;
        this.noidung = noidung;
        this.soluong = soluong;
        this.giadv = giadv;
    }

    public Long getMadv() {
        return madv;
    }
    public void setMadv(Long madv) {
        this.madv = madv;
    }
    public String getNoidung() {
        return noidung;
    }
    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
    public int getSoluong() {
        return soluong;
    }
    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
    public double getGiadv() {
        return giadv;
    }
    public void setGiadv(double giadv) {
        this.giadv = giadv;
    }

    public Set<DoiTac> getDoiTacs() {
        return doiTacs;
    }

    public void setDoiTacs(Set<DoiTac> doiTacs) {
        this.doiTacs = doiTacs;
    }

}
