package com.example.baimoi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "IMGDOITAC")
public class ImgDoiTac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maimg;
    private String img;
    private Long madt;

    @ManyToOne
    @JoinColumn(name = "madt", referencedColumnName = "madt", insertable = false, updatable = false)
    private DoiTac doiTac;

    public ImgDoiTac() {
    }

    public ImgDoiTac(String img, Long madt, Long maimg) {
        this.img = img;
        this.madt = madt;
        this.maimg = maimg;
    }

    public Long getMaimg() {
        return maimg;
    }

    public void setMaimg(Long maimg) {
        this.maimg = maimg;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}
