/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.helper;

import com.org.ultrainstinct.model.NhanVien;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author Minh Ngoc
 */
public class ShareHelper {

    /**
     * Ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
     */
    public static final Image APP_ICON;

    static {
        // Load the application icon
        String file = "/com/polypro/icon/fpt.png";
        APP_ICON = new ImageIcon(ShareHelper.class.getResource(file)).getImage();
    }

    /**
     * Copies the logo file to the logo directory.
     *
     * @param file the image file
     * @return whether the copy was successful
     */
    public static boolean saveLogo(File file) {
        File dir = new File("D:\\FPT_POLYTECHNIC\\SUMMER2024\\DuAnMau\\DuAnMau_PS37203\\src\\com\\polypro\\icon\\");
    // Create directory if it does not exist
    if (!dir.exists()) {
        dir.mkdirs();
    }
    File newFile = new File(dir, file.getName());
    try {
        // Copy to the logos directory (overwrite if exists)
        Path source = Paths.get(file.getAbsolutePath());
        Path destination = Paths.get(newFile.getAbsolutePath());
        System.out.println("Copying file from " + source + " to " + destination);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        return true;
    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
    }

    /**
     * Reads the logo image.
     *
     * @param fileName the logo file name
     * @return the read image
     */
    public static ImageIcon readLogo(String fileName) {
       File path = new File("D:\\FPT_POLYTECHNIC\\SUMMER2024\\DuAnMau\\DuAnMau_PS37203\\src\\com\\polypro\\icon\\", fileName);
    System.out.println("Reading logo from " + path.getAbsolutePath());
    if (path.exists()) {
        return new ImageIcon(path.getAbsolutePath());
    } else {
        System.out.println("File does not exist: " + path.getAbsolutePath());
        return null;
    }
    }

    /**
     * This object contains the information of the logged-in user.
     */
    public static NhanVien USER = null;

    /**
     * Clears the user information when logging out.
     */
    public static void logoff() {
        ShareHelper.USER = null;
    }

    /**
     * Checks if the user is logged in.
     *
     * @return whether the user is logged in
     */
    public static boolean authenticated() {
        return ShareHelper.USER != null;
    }
}
