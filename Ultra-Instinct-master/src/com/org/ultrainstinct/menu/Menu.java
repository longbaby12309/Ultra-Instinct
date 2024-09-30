package com.org.ultraInstinct.menu;

import com.org.ultrainstinct.dto.UserSession;
import com.org.ultrainstinct.ui.DangNhapJDialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class Menu extends JComponent {
    public MenuEvent getEvent() {
        return event;
    }

    public void setEvent(MenuEvent event) {
        this.event = event;
    }

    private MenuEvent event;
    private MigLayout layout;
    private String[][] menuItems = new String[][]{
        {"Bán hàng"},
        {"Giao dịch"},
        {"Hàng hóa", "Quản lý sản phẩm", "Quản lý kho"},
        {"Báo cáo", "Báo cáo doanh thu", "Chart doanh thu", "Chart khách hàng và đơn hàng"},
        {"Khách hàng"},
        {"Nhân viên"},
        {"Hỏi đáp"},
        {"Cài đặt", "Thông tin người dùng", "Đổi mật khẩu", "Thông báo", "Điều khoản & bảo mật"}
    };

    public Menu() {
        init();
    }

    private void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        // Init MenuItem
        for (int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);
        }
        addUserInfoPanel();
    }

    private Icon getIcon(int index) {
        URL url = getClass().getResource("/com/org/ultraInstinct/menu/" + index + ".png");
        if (url != null) {
            return new ImageIcon(url);
        } else {
            return null;
        }
    }

    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if (icon != null) {
            item.setIcon(icon);
        }
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (length > 1) {
                    if (!item.isSelected()) {
                        item.setSelected(true);
                        addSubMenu(item, index, length, getComponentZOrder(item));
                    } else {
                        // Hide menu
                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                } else {
                    if (event != null) {
                        event.selected(index, 0);
                    }
                }
            }
        });
        add(item);
        revalidate();
        repaint();
    }
    
    void addSubMenu(MenuItem item, int index, int length, int indexZorder) {
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        panel.setName(index + "");
        panel.setBackground(new Color(18, 99, 63));
        for (int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    if (event != null) {
                        event.selected(index, subItem.getIndex());
                    }
                }
            });
            subItem.initSubMenu(i, length);
            panel.add(subItem);
        }
        add(panel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        MenuAnimation.showMenu(panel, item, layout, true);
    }

    private void hideMenu(MenuItem item, int index) {
        for (Component com : getComponents()) {
            if (com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")) {
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }
    }

    private void addUserInfoPanel() {
        JPanel userInfoPanel = new JPanel(new MigLayout("fillx, inset 2", "fill"));
        userInfoPanel.setBackground(new Color(21, 110, 71));

        // Load user image
        JLabel userImage = new JLabel();
        URL imageUrl = getClass().getResource("/com/org/ultraInstinct/icon/user.png");
        if (imageUrl != null) {
            ImageIcon imageIcon = new ImageIcon(imageUrl);
            Image image = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            userImage.setIcon(new ImageIcon(image));
        }

        // User name
        JLabel userName = new JLabel();
        userName.setForeground(Color.WHITE);

        // Lấy thông tin người dùng từ UserSession
        UserSession userSession = UserSession.getUser();
        if (userSession != null && userSession.isLogin()) {
            userName.setText(userSession.getFullName()); // Hiển thị họ tên
        }

        // Create logout button
        JButton logoutButton = new JButton();
        URL imageUrl1 = getClass().getResource("/com/org/ultraInstinct/icon/logout.png");
        if (imageUrl1 != null) {
            ImageIcon imageIcon1 = new ImageIcon(imageUrl1);
            Image image1 = imageIcon1.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            logoutButton.setIcon(new ImageIcon(image1));
        }
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// Xóa thông tin phiên đăng nhập
                UserSession.clear();

                // Đóng Main frame và hiển thị lại màn hình đăng nhập
                SwingUtilities.invokeLater(() -> {
                    DangNhapJDialog loginDialog = new DangNhapJDialog(null, true);
                    loginDialog.setVisible(true);
                });

                // Đóng cửa sổ Main hiện tại
                java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(Menu.this);
                if (win != null) {
                    win.dispose();
                }
            }
        });

        userInfoPanel.add(userImage, "gapright 10");
        userInfoPanel.add(userName);
        userInfoPanel.add(logoutButton, "gapleft 10");

        add(userInfoPanel, "south");
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setColor(new Color(21, 110, 71));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }
}
