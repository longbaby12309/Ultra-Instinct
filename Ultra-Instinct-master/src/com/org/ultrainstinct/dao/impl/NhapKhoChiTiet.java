/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.model.NhapKho;
import com.org.ultrainstinct.model.PhieuNhapChiTiet;

/**
 *
 * @author Admin
 */
public class NhapKhoChiTiet {
    private NhapKho nhapKho;
    private PhieuNhapChiTiet phieuNhapChiTiet;

    public NhapKhoChiTiet(NhapKho nhapKho, PhieuNhapChiTiet phieuNhapChiTiet) {
        this.nhapKho = nhapKho;
        this.phieuNhapChiTiet = phieuNhapChiTiet;
    }

    public NhapKho getNhapKho() {
        return nhapKho;
    }

    public void setNhapKho(NhapKho nhapKho) {
        this.nhapKho = nhapKho;
    }

    public PhieuNhapChiTiet getPhieuNhapChiTiet() {
        return phieuNhapChiTiet;
    }

    public void setPhieuNhapChiTiet(PhieuNhapChiTiet phieuNhapChiTiet) {
        this.phieuNhapChiTiet = phieuNhapChiTiet;
    }

    @Override
    public String toString() {
        return "NhapKhoChiTiet{" + "nhapKho=" + nhapKho + ", phieuNhapChiTiet=" + phieuNhapChiTiet + '}';
    }

    
}
