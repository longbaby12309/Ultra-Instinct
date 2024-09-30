package com.org.ultrainstinct.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * DiaChi class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class DiaChi implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long diaChiNo;

    private String maDiaChi;

    private String diaChi;

    private String diaChiMacDinh;

    private boolean trangThaiXoa;

    private String maKhachHang;
}
