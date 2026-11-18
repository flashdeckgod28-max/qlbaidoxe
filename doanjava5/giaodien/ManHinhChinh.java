package giaodien;

import javax.swing.*;

public class ManHinhChinh extends JFrame {
    public ManHinhChinh() {
        setTitle("Quản lý bãi gửi xe - " + PhienDangNhap.nhanVien.hoTen + " (" + PhienDangNhap.nhanVien.chucVu + ")");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JTabbedPane thanhTab = new JTabbedPane();
        thanhTab.addTab("Lượt gửi xe", new TrangLuotGui());
        thanhTab.addTab("Thanh toán", new TrangThanhToan());
        thanhTab.addTab("Vé tháng", new TrangVeThang());
        thanhTab.addTab("Khách hàng", new TrangKhachHang());
        thanhTab.addTab("Xe", new TrangXe());
        thanhTab.addTab("Loại xe", new TrangLoaiXe());
        thanhTab.addTab("Vị trí", new TrangViTri());
        thanhTab.addTab("Bảng giá", new TrangBangGia());
        thanhTab.addTab("Nhân viên", new TrangNhanVien());
        thanhTab.addTab("Ca trực", new TrangCaTruc());
        thanhTab.addTab("Sự cố", new TrangSuCo());
        thanhTab.addTab("Thống kê", new TrangThongKe());
        setContentPane(thanhTab);

        JMenuBar thanhMenu = new JMenuBar();
        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem mucDangXuat = new JMenuItem("Đăng xuất");
        mucDangXuat.addActionListener(e -> {
            PhienDangNhap.nhanVien = null;
            new ManHinhDangNhap().setVisible(true);
            dispose();
        });
        JMenuItem mucThoat = new JMenuItem("Thoát");
        mucThoat.addActionListener(e -> System.exit(0));
        menuHeThong.add(mucDangXuat);
        menuHeThong.add(mucThoat);
        thanhMenu.add(menuHeThong);
        setJMenuBar(thanhMenu);
    }
}
