package giaodien;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TrangThongKe extends JPanel {
    private static final String[] TIEU_DE = {"Xe đang trong bãi", "Chỗ trống theo khu vực", "Doanh thu theo ngày", "Vé tháng sắp hết hạn (7 ngày)"};
    private static final String[] CAU_LENH = {
        "SELECT MaLuot AS [Mã lượt], BienSo AS [Biển số], TenLoai AS [Loại xe], TenViTri AS [Vị trí], KhuVuc AS [Khu vực], GioVao AS [Giờ vào], SoPhutDaGui AS [Số phút đã gửi], LoaiVe AS [Loại vé] FROM vw_XeDangTrongBai",
        "SELECT KhuVuc AS [Khu vực], TenLoai AS [Loại xe], TongCho AS [Tổng chỗ], ChoTrong AS [Chỗ trống] FROM vw_ChoTrong",
        "SELECT Ngay AS [Ngày], TongDoanhThu AS [Tổng doanh thu], DoanhThuLuot AS [Doanh thu lượt], DoanhThuVeThang AS [Doanh thu vé tháng] FROM vw_DoanhThuNgay ORDER BY Ngay DESC",
        "SELECT MaVe AS [Mã vé], HoTen AS [Họ tên], SDT AS [Số điện thoại], BienSo AS [Biển số], NgayHetHan AS [Ngày hết hạn], ConLaiNgay AS [Còn lại (ngày)] FROM vw_VeSapHetHan"
    };
    private final JComboBox<String> cboBaoCao = new JComboBox<>(TIEU_DE);
    private final JTable bang = new JTable();

    public TrangThongKe() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        bang.setRowHeight(24);

        JPanel bac = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bac.add(new JLabel("Báo cáo"));
        bac.add(cboBaoCao);
        JButton nutXem = new JButton("Xem");
        nutXem.addActionListener(e -> xem());
        bac.add(nutXem);

        add(bac, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        cboBaoCao.addActionListener(e -> xem());
        xem();
    }

    private void xem() {
        try {
            bang.setModel(TienIch.taiBang(CAU_LENH[cboBaoCao.getSelectedIndex()]));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
