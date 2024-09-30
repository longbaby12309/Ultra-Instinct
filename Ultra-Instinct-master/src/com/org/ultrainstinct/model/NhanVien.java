package com.org.ultrainstinct.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * NhanVien class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NhanVien implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long nhanVienNo;

    private String maNhanVien;

    private String tenNhanVien;

    private String hoNhanVien;

    private String matKhau;

    private String soDienThoai;

    private String email;

    private String chucVu;

    private boolean trangThaiXoa;

    private String nguoiTao;

    private Date thoiGianTao;
}
