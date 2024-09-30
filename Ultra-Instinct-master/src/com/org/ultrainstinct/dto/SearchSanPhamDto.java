package com.org.ultrainstinct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * SearchSanPhamRequest relate to SanPham table in database.
 * </p>
 */
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class SearchSanPhamDto  {

    private String maSanPham;
    private String tenSanPham;
    private String loaiSanPham;
    private Long giaNiemYet;
    private Long soLuongTon;
    private String freeText;
}
