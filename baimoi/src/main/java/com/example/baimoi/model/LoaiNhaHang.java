package com.example.baimoi.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="LOAI_NH")
public class LoaiNhaHang {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY )
    private Long malnh;
    private String tenlnh;
    private String imgmota; 

    @ManyToMany(mappedBy = "loaiNhaHangs")
    private Set<DoiTac> doiTacs;

    public Set<DoiTac> getDoiTacs() {
        return doiTacs;
    }

    public void setDoiTacs(Set<DoiTac> doiTacs) {
        this.doiTacs = doiTacs;
    }

    public LoaiNhaHang() {
    }

    public LoaiNhaHang(Long malnh, String tenlnh, String imgmota) {
        this.malnh = malnh;
        this.tenlnh = tenlnh;
        this.imgmota = imgmota;
    }
    
    public Long getMalnh() {
        return malnh;
    }
    public void setMalnh(Long malnh) {
        this.malnh = malnh;
    }
    public String getTenlnh() {
        return tenlnh;
    }
    public void setTenlnh(String tenlnh) {
        this.tenlnh = tenlnh;
    }
    public String getImgmota() {
        return imgmota;
    }
    public void setImgmota(String imgmota) {
        this.imgmota = imgmota;
    }
    
}
