package com.example.baimoi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="PHANQUYEN")
public class Phanquyen {
    
    @Id
    @Column(name = "MaPQ")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long mapq;
    @Column(name = "TenPQ" )
    private String tenpq;

    public Phanquyen(){

    }
    
    public Phanquyen(Long mapq, String tenpq) {
        this.mapq = mapq;
        this.tenpq = tenpq;
    }

    public Long getMapq() {
        return mapq;
    }

    public void setMapq(Long mapq) {
        this.mapq = mapq;
    }

    public String getTenpq() {
        return tenpq;
    }

    public void setTenpq(String tenpq) {
        this.tenpq = tenpq;
    }

    
}

