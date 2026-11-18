package giaodien;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import thucthe.LuotGui;
import truycap.TruyCapLuotGui;

public class TrangLuotGui extends JPanel {
    private final JTextField oBienSo = new JTextField(12);
    private final JComboBox<MucChon> cboLoai = new JComboBox<>();
    private final JComboBox<String> cboPhuongThuc = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "Ví điện tử"});
    private final JCheckBox chkDangGui = new JCheckBox("Chỉ xe đang gửi", true);
    private final DefaultTableModel moHinh = new DefaultTableModel(
            new String[]{"Mã lượt", "Biển số", "Vị trí", "Mã vé", "Giờ vào", "Giờ ra", "NV vào", "NV ra", "Trạng thái", "Ghi chú"}, 0) {
        @Override
        public boolean isCellEditable(int h, int c) {
            return false;
        }
    };
    private final JTable bang = new JTable(moHinh);
    private List<LuotGui> hienThi = new ArrayList<>();

    public TrangLuotGui() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        bang.setRowHeight(24);
        bang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel bac = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        bac.setBorder(BorderFactory.createTitledBorder("Xe vào / Xe ra"));
        bac.add(new JLabel("Biển số"));
        bac.add(oBienSo);
        bac.add(new JLabel("Loại xe"));
        bac.add(cboLoai);
        JButton nutVao = new JButton("Xe vào");
        nutVao.addActionListener(e -> xeVao());
        bac.add(nutVao);
        bac.add(new JLabel("Thanh toán"));
        bac.add(cboPhuongThuc);
        JButton nutRa = new JButton("Xe ra (dòng đã chọn)");
        nutRa.addActionListener(e -> xeRa());
        bac.add(nutRa);

        JPanel nam = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        chkDangGui.addActionListener(e -> taiLai());
        nam.add(chkDangGui);
        JButton nutGhiChu = new JButton("Cập nhật ghi chú");
        nutGhiChu.addActionListener(e -> capNhatGhiChu());
        nam.add(nutGhiChu);
        JButton nutLamMoi = new JButton("Làm mới");
        nutLamMoi.addActionListener(e -> taiLai());
        nam.add(nutLamMoi);

        add(bac, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(nam, BorderLayout.SOUTH);
        taiLai();
    }

    private void taiLai() {
        try {
            TienIch.napChon(cboLoai, TienIch.danhSachChon("SELECT MaLoai, TenLoai FROM LoaiXe ORDER BY MaLoai"), false);
            hienThi = new ArrayList<>();
            moHinh.setRowCount(0);
            for (LuotGui l : TruyCapLuotGui.layTatCa()) {
                if (chkDangGui.isSelected() && !"Đang gửi".equals(l.trangThai)) {
                    continue;
                }
                hienThi.add(l);
                moHinh.addRow(new Object[]{l.maLuot, l.bienSo, l.tenViTri, l.maVe, TienIch.hienGio(l.gioVao),
                    TienIch.hienGio(l.gioRa), l.maNVVao, l.maNVRa, l.trangThai, l.ghiChu});
            }
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void xeVao() {
        try {
            String bienSo = oBienSo.getText().trim();
            if (bienSo.isEmpty()) {
                throw new Exception("Vui lòng nhập biển số.");
            }
            int maLoai = TienIch.maBatBuoc(cboLoai, "loại xe");
            int maLuot = TruyCapLuotGui.xeVao(bienSo, maLoai, PhienDangNhap.nhanVien.maNV);
            JOptionPane.showMessageDialog(this, "Đã ghi nhận lượt gửi số " + maLuot + ".");
            oBienSo.setText("");
            taiLai();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void xeRa() {
        int h = bang.getSelectedRow();
        if (h < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn lượt gửi cần cho xe ra.");
            return;
        }
        try {
            LuotGui l = hienThi.get(h);
            long soTien = TruyCapLuotGui.xeRa(l.maLuot, PhienDangNhap.nhanVien.maNV, (String) cboPhuongThuc.getSelectedItem());
            String noiDung = l.maVe == null ? "Xe ra thành công. Số tiền: " + soTien + " đồng." : "Xe ra thành công (vé tháng, không thu thêm).";
            JOptionPane.showMessageDialog(this, noiDung);
            taiLai();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void capNhatGhiChu() {
        int h = bang.getSelectedRow();
        if (h < 0) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một lượt gửi.");
            return;
        }
        try {
            LuotGui l = hienThi.get(h);
            String s = JOptionPane.showInputDialog(this, "Ghi chú:", l.ghiChu == null ? "" : l.ghiChu);
            if (s == null) {
                return;
            }
            TruyCapLuotGui.capNhatGhiChu(l.maLuot, s.trim().isEmpty() ? null : s.trim());
            taiLai();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void baoLoi(Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
