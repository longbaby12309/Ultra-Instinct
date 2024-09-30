package com.org.ultrainstinct.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * HoaDonChiTiet class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HoaDonChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long HDCTNo;

    private String maHDCT;

    private float giaBan;

    private int soLuong;

    private String ghiChu;

    private String maHoaDon;

    private String maSanPham;
    
    private float thanhTien;

    public float getThanhTien() {
        return giaBan * soLuong;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    
}
