package com.org.ultrainstinct.ui;

import com.org.ultrainstinct.dao.HoaDonDAO;
import com.org.ultrainstinct.dao.impl.HoaDonDAOImpl;
import com.org.ultrainstinct.dao.impl.SanPhamDAOImpl;
import com.org.ultrainstinct.model.HoaDonChiTiet;
import com.org.ultrainstinct.model.SanPham;
import java.awt.Color;
import java.sql.SQLException;
import java.util.List;
import raven.chart.geo.utils.GeoData;

public class HomeForm extends javax.swing.JPanel {
    private HoaDonDAOImpl hoaDonDAO;
    private SanPhamDAOImpl sanPhamDAO;

    public HomeForm() {
        initComponents();
        hoaDonDAO = new HoaDonDAOImpl();
        sanPhamDAO = new SanPhamDAOImpl();
        geoChart.load(GeoData.Resolution.LOW);
        geoChart.getGeoChart().zoom(1.8);

        try {
            List<SanPham> topSellingProducts = hoaDonDAO.findTopSellingProducts(10);
            for (SanPham product : topSellingProducts) {
                String productName = getProductName(product.getMaSanPham());
                int totalSold = product.getSoLuongTon(); // Using soLuongTon to store totalSold for charting
                Color color = generateRandomColor(); // Generate a random color for each product

                geoChart.putData(productName, totalSold, color);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        geoChart.setGeoChartDataView(geoChartDataView);
    }

    private String getProductName(String maSanPham) {
        SanPham product = sanPhamDAO.findById(maSanPham);
        if (product != null) {
            return product.getTenSanPham(); // Assuming `tenSanPham` is the product name field
        }
        return "Unknown Product"; // Fallback if product name is not found
    }

    private Color generateRandomColor() {
        return new Color((int)(Math.random() * 0x1000000));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        geoChart = new raven.chart.geo.GeoChart();
        geoChartDataView = new raven.chart.geo.GeoChartDataView();

        setBackground(new java.awt.Color(245, 245, 245));

        geoChart.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(geoChart, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                    .addComponent(geoChartDataView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(geoChart, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(geoChartDataView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(152, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private raven.chart.geo.GeoChart geoChart;
    private raven.chart.geo.GeoChartDataView geoChartDataView;
    // End of variables declaration//GEN-END:variables
}
