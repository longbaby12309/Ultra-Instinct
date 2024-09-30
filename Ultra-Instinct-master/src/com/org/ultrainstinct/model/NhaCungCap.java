package com.org.ultrainstinct.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * NhaCungCap class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NhaCungCap implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long nhaCungCapNo;

    private String maNhaCungCap;

    private String tenNCC;

    private String soDienThoai;

    private String email;

}
