package com.org.ultrainstinct.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * HoaDon class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HoaDon implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long hoaDonNo;

    private String maHoaDon;

    private String maKhachHang;

    private String maNhanVien;

    private Date ngayLap;

    private boolean trangThai;
  
    
}
