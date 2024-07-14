package com.example.baimoi.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="THANHTOAN")
public class Thanhtoan {
    
    @Id
    @Column(name="MaTT")
    private int matt;

    @Column(name="MaDT")
    private int madt;

    @Column(name="SoTienTT")
    private Double sotientt;

    @Column(name="NgayTT")
    private Date ngaytt;

    public Thanhtoan(){

    }

    public Thanhtoan(int matt, int madt, Double sotientt, Date ngaytt) {
        this.matt = matt;
        this.madt = madt;
        this.sotientt = sotientt;
        this.ngaytt = ngaytt;
    }

    public int getMatt() {
        return matt;
    }

    public void setMatt(int matt) {
        this.matt = matt;
    }

    public int getMadt() {
        return madt;
    }

    public void setMadt(int madt) {
        this.madt = madt;
    }

    public Double getSotientt() {
        return sotientt;
    }

    public void setSotientt(Double sotientt) {
        this.sotientt = sotientt;
    }

    public Date getNgaytt() {
        return ngaytt;
    }

    public void setNgaytt(Date ngaytt) {
        this.ngaytt = ngaytt;
    }
    
    
}
