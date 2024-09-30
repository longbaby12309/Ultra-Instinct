package com.org.ultrainstinct.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * SanPham class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SanPham implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sanPhamNo;

    private String maSanPham;

    private String tenSanPham;

    private float giaNiemYet;

    private int soLuongTon;

    private String hinh;

    private String loaiSanPham;

}
