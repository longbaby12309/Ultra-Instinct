package com.org.ultrainstinct.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * PhieuNhapChiTiet class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PhieuNhapChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long PNCTNo;

    private String maPNCT;

    private float giaNhap;

    private int soLuong;

    private String maSanPham;

    private String maNhaCungCap;

    private String maNhapKho;
    
    private boolean trangThai;
    
    private Date ngayNhap;

    public PhieuNhapChiTiet(String maPNCT, float giaNhap, int soLuong, String maSanPham, String maNhaCungCap, String maNhapKho, boolean trangThai, Date ngayNhap) {
        this.maPNCT = maPNCT;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        this.maSanPham = maSanPham;
        this.maNhaCungCap = maNhaCungCap;
        this.maNhapKho = maNhapKho;
        this.trangThai = trangThai;
        this.ngayNhap = ngayNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    
    
    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public Long getPNCTNo() {
        return PNCTNo;
    }

    public void setPNCTNo(Long PNCTNo) {
        this.PNCTNo = PNCTNo;
    }

    public String getMaPNCT() {
        return maPNCT;
    }

    public void setMaPNCT(String maPNCT) {
        this.maPNCT = maPNCT;
    }

    public float getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getMaNhapKho() {
        return maNhapKho;
    }

    public void setMaNhapKho(String maNhapKho) {
        this.maNhapKho = maNhapKho;
    }
    
    
}
