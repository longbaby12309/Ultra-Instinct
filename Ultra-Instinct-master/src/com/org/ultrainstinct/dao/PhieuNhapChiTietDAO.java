/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.org.ultrainstinct.dao;

import com.org.ultrainstinct.model.PhieuNhapChiTiet;
import java.util.List;



/**
 *
 * @author Admin
 */
public interface PhieuNhapChiTietDAO extends CRUDDao<PhieuNhapChiTiet, Long>{

    @Override
    public void deleteById(Long id);

    @Override
    public List<PhieuNhapChiTiet> findAll();

    @Override
    public PhieuNhapChiTiet findById(Long id);

    @Override
    public void update(PhieuNhapChiTiet entity, Long id);

    @Override
    public void save(PhieuNhapChiTiet entity);

    
    
}
