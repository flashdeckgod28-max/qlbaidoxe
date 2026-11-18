package giaodien;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import thucthe.NhanVien;
import truycap.TruyCapNhanVien;

public class ManHinhDangNhap extends JFrame {
    private final JTextField oTaiKhoan = new JTextField(18);
    private final JPasswordField oMatKhau = new JPasswordField(18);

    public ManHinhDangNhap() {
        setTitle("Quản lý bãi gửi xe - Đăng nhập");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel noiDung = new JPanel(new GridBagLayout());
        noiDung.setBorder(new EmptyBorder(20, 24, 20, 24));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel tieuDe = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        tieuDe.setFont(tieuDe.getFont().deriveFont(Font.BOLD, 18f));
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        noiDung.add(tieuDe, g);

        g.gridwidth = 1;
        g.gridy = 1;
        noiDung.add(new JLabel("Tài khoản"), g);
        g.gridx = 1;
        noiDung.add(oTaiKhoan, g);

        g.gridx = 0;
        g.gridy = 2;
        noiDung.add(new JLabel("Mật khẩu"), g);
        g.gridx = 1;
        noiDung.add(oMatKhau, g);

        JButton nutDangNhap = new JButton("Đăng nhập");
        nutDangNhap.addActionListener(e -> dangNhap());
        JButton nutThoat = new JButton("Thoát");
        nutThoat.addActionListener(e -> System.exit(0));
        JPanel khungNut = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        khungNut.add(nutDangNhap);
        khungNut.add(nutThoat);
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        noiDung.add(khungNut, g);

        setContentPane(noiDung);
        getRootPane().setDefaultButton(nutDangNhap);
        pack();
        setLocationRelativeTo(null);
    }

    private void dangNhap() {
        try {
            String taiKhoan = oTaiKhoan.getText().trim();
            String matKhau = new String(oMatKhau.getPassword());
            if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản và mật khẩu.");
                return;
            }
            NhanVien nv = TruyCapNhanVien.dangNhap(taiKhoan, matKhau, TienIch.bam(matKhau));
            if (nv == null) {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PhienDangNhap.nhanVien = nv;
            new ManHinhChinh().setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
