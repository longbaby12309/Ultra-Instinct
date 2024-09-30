package com.org.ultrainstinct.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * KhachHang class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class KhachHang implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long khachHangNo;

    private String maKhachHang;

    private String tenKH;

    private String soDienThoai;

    private String email;
    
    private Date ngaySinh;
    
    private boolean gioiTinh;

    private Date ngayDangKy;

    private boolean trangThaiKH;

    private boolean trangThaiXoa;

    private String nguoiTao;

    private String ghiChu;
    
    private Float tongBan;
    
     private List<DiaChi> diaChiList; // Add this line

    public List<DiaChi> getDiaChiList() {
        return diaChiList;
    }

    public void setDiaChiList(List<DiaChi> diaChiList) {
        this.diaChiList = diaChiList;
    }
}
