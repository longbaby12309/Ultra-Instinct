/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.model;

/**
 *
 * @author Admin
 */
public class XuatSanPham {
    private PhieuNhapChiTiet pnct;
    private SanPham sp;
    private NhapKho nk;

    @Override
    public String toString() {
        return "XuatSanPham{" + "pnct=" + pnct + ", sp=" + sp + '}';
    }
    
    

    public XuatSanPham() {
    }

    public XuatSanPham(PhieuNhapChiTiet pnct, SanPham sp) {
        this.pnct = pnct;
        this.sp = sp;
    }
    
    
    
    public XuatSanPham(PhieuNhapChiTiet pnct, SanPham sp, NhapKho nk) {
        this.pnct = pnct;
        this.sp = sp;
        this.nk = nk;
    }

    public NhapKho getNk() {
        return nk;
    }

    public void setNk(NhapKho nk) {
        this.nk = nk;
    }
    
    

    public PhieuNhapChiTiet getPnct() {
        return pnct;
    }

    public void setPnct(PhieuNhapChiTiet pnct) {
        this.pnct = pnct;
    }

    public SanPham getSp() {
        return sp;
    }

    public void setSp(SanPham sp) {
        this.sp = sp;
    }
    
    
    
}
