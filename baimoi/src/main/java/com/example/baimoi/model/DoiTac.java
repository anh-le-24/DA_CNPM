package com.example.baimoi.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DOITAC")
public class DoiTac {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY )
    private Long madt;
    private Long mand;
    private String tennhahang;
    private String sonhanh;
    private String duongnh;
    private String quannh;
    private String thanhphonh;
    private String mota;
    private String linklienket;
    private String linkggmap;
    private String hoadontb;
    private Time giomo;
    private Time giodong;
    private int sochongoi;
    private String imgmota;
    private Long madv;
    private Date ngaydk;
    private Long mabv;

    // Kết nối với bảng dịch vụ
    @ManyToOne
    @JoinColumn(name = "madv", referencedColumnName = "madv",insertable=false, updatable=false)
    private DichVuCC dichVuCC;
    
    // kết nối với bảng đơn đặt bàn
    @JsonIgnore
    @OneToMany(mappedBy = "doiTac", fetch = FetchType.LAZY)
    private Set<DonDatBan> donDatBans;

    // kết nối với bảng Com bo mon an
    @OneToMany(mappedBy = "doiTac", fetch = FetchType.EAGER)
    private Set<ComBoMonAn> comBoMonAns;

    // kết nối với bảng Chi nhanh
    @OneToMany(mappedBy = "doiTac", fetch = FetchType.EAGER)
    private Set<ChiNhanh> chiNhanhs;

    // Kết nối với bảng người dùng
    @OneToOne
    @JoinColumn(name = "mand", referencedColumnName = "mand", insertable = false, updatable = false)
    private NguoiDung nguoiDung;

    // Kết nối với bảng Bài viết
    @OneToOne
    @JoinColumn(name = "mabv", referencedColumnName = "mabv", insertable = false, updatable = false)
    private BaiVietDT baiVietDT;
 
    // kết nối với bảng ImgDoiTac
    @OneToMany(mappedBy = "doiTac", fetch = FetchType.LAZY)
    private Set<ImgDoiTac> imgDoiTacs;
         
    
    // Kết nối với bảng loại nhà hàng
     @ManyToMany( fetch = FetchType.EAGER)
     @JoinTable(
         name = "LOAI_NH_DOITAC",
         joinColumns = @JoinColumn(name = "madt"),
         inverseJoinColumns = @JoinColumn(name = "malnh")
    )
    private Set<LoaiNhaHang> loaiNhaHangs;

    public DoiTac() {
    }

    public DoiTac(Long madt, Long mand, String tennhahang, String sonhanh, String duongnh, String quannh,
            String thanhphonh, String mota, String linklienket, String linkggmap, String hoadontb, Time giomo,
            Time giodong, int sochongoi,String imgmota, Long madv, Date ngaydk, Long mabv) {
        this.madt = madt;
        this.mand = mand;
        this.tennhahang = tennhahang;
        this.sonhanh = sonhanh;
        this.duongnh = duongnh;
        this.quannh = quannh;
        this.thanhphonh = thanhphonh;
        this.mota = mota;
        this.linklienket = linklienket;
        this.linkggmap = linkggmap;
        this.hoadontb = hoadontb;
        this.giomo = giomo;
        this.giodong = giodong;
        this.sochongoi = sochongoi;
        this.madv = madv;
        this.ngaydk = ngaydk;
        this.imgmota=imgmota;
        this.mabv = mabv;
    }

    public Long getMadt() {
        return madt;
    }

    public void setMadt(Long madt) {
        this.madt = madt;
    }

    public Long getMand() {
        return mand;
    }

    public void setMand(Long mand) {
        this.mand = mand;
    }

    public String getTennhahang() {
        return tennhahang;
    }

    public void setTennhahang(String tennhahang) {
        this.tennhahang = tennhahang;
    }

    public String getSonhahang() {
        return sonhanh;
    }

    public void setSonhahang(String sonhanh) {
        this.sonhanh = sonhanh;
    }

    public String getDuongnh() {
        return duongnh;
    }

    public void setDuongnh(String duongnh) {
        this.duongnh = duongnh;
    }

    public String getQuannh() {
        return quannh;
    }

    public void setQuannh(String quannh) {
        this.quannh = quannh;
    }

    public String getThanhphonh() {
        return thanhphonh;
    }

    public void setThanhphonh(String thanhphonh) {
        this.thanhphonh = thanhphonh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getLinklienket() {
        return linklienket;
    }

    public void setLinklienket(String linklienket) {
        this.linklienket = linklienket;
    }

    public String getLinkggmap() {
        return linkggmap;
    }

    public void setLinkggmap(String linkggmap) {
        this.linkggmap = linkggmap;
    }

    public String getHoadontb() {
        return hoadontb;
    }

    public void setHoadontb(String hoadontb) {
        this.hoadontb = hoadontb;
    }

    public Time getGiomo() {
        return giomo;
    }

    public void setGiomo(Time giomo) {
        this.giomo = giomo;
    }

    public Time getGiodong() {
        return giodong;
    }

    public void setGiodong(Time giodong) {
        this.giodong = giodong;
    }

    public int getSochongoi() {
        return sochongoi;
    }

    public void setSochongoi(int sochongoi) {
        this.sochongoi = sochongoi;
    }

    public Long getMadv() {
        return madv;
    }

    public void setMadv(Long madv) {
        this.madv = madv;
    }

    public Date getNgaydk() {
        return ngaydk;
    }

    public void setNgaydk(Date ngaydk) {
        this.ngaydk = ngaydk;
    }
    public String getFullAddress() {
        return sonhanh + ", " + duongnh + ", " + quannh + ", " + thanhphonh;
    }

    public String getSonhanh() {
        return sonhanh;
    }

    public void setSonhanh(String sonhanh) {
        this.sonhanh = sonhanh;
    }

    public DichVuCC getDichVuCC() {
        return dichVuCC;
    }

    public void setDichVuCC(DichVuCC dichVuCC) {
        this.dichVuCC = dichVuCC;
    }

    public Set<LoaiNhaHang> getLoaiNhaHangs() {
        return loaiNhaHangs;
    }

    public void setLoaiNhaHangs(Set<LoaiNhaHang> loaiNhaHangs) {
        this.loaiNhaHangs = loaiNhaHangs;
    }


    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public String getImgmota() {
        return imgmota;
    }

    public void setImgmota(String imgmota) {
        this.imgmota = imgmota;
    }

    public void addLoaiNhaHang(LoaiNhaHang loaiNhaHang) {
        this.loaiNhaHangs.add(loaiNhaHang);
        loaiNhaHang.getDoiTacs().add(this); // Đảm bảo cập nhật cả hai chiều quan hệ
    }

    public void removeLoaiNhaHang(LoaiNhaHang loaiNhaHang) {
        this.loaiNhaHangs.remove(loaiNhaHang);
        loaiNhaHang.getDoiTacs().remove(this); // Đảm bảo cập nhật cả hai chiều quan hệ
    }

    public Set<DonDatBan> getDonDatBans() {
        return donDatBans;
    }

    public void setDonDatBans(Set<DonDatBan> donDatBans) {
        this.donDatBans = donDatBans;
    }

    public Set<ComBoMonAn> getComBoMonAns() {
        return comBoMonAns;
    }

    public void setComBoMonAns(Set<ComBoMonAn> comBoMonAns) {
        this.comBoMonAns = comBoMonAns;
    }

    public Set<ChiNhanh> getChiNhanhs() {
        return chiNhanhs;
    }

    public void setChiNhanhs(Set<ChiNhanh> chiNhanhs) {
        this.chiNhanhs = chiNhanhs;
    }

    public BaiVietDT getBaiVietDT() {
        return baiVietDT;
    }

    public void setBaiVietDT(BaiVietDT baiVietDT) {
        this.baiVietDT = baiVietDT;
    }

    public Long getMabv() {
        return mabv;
    }

    public void setMabv(Long mabv) {
        this.mabv = mabv;
    }

    public Set<ImgDoiTac> getImgDoiTacs() {
        return imgDoiTacs;
    }

    public void setImgDoiTacs(Set<ImgDoiTac> imgDoiTacs) {
        this.imgDoiTacs = imgDoiTacs;
    }
    
}

