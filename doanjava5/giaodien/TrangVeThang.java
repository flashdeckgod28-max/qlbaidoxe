package giaodien;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import thucthe.VeThang;
import truycap.TruyCapVeThang;

public class TrangVeThang extends TrangCoSo<VeThang> {
    private final JComboBox<MucChon> cboKhachHang = themChon("Khách hàng");
    private final JComboBox<MucChon> cboXe = themChon("Xe");
    private final JTextField oNgayBatDau = themTruong("Ngày bắt đầu (yyyy-MM-dd)");
    private final JTextField oNgayHetHan = themTruong("Ngày hết hạn (yyyy-MM-dd)");
    private final JTextField oGiaVe = themTruong("Giá vé");
    private final JComboBox<String> cboTrangThai = themChonChuoi("Trạng thái", "Còn hạn", "Hết hạn", "Đã huỷ");

    public TrangVeThang() {
        khoiTao();
        themNut("Đăng ký vé tháng", this::dangKyVeThang);
        themNut("Cập nhật vé hết hạn", this::capNhatHetHan);
    }

    private void dangKyVeThang() {
        try {
            int maKH = TienIch.maBatBuoc(cboKhachHang, "khách hàng");
            int maXe = TienIch.maBatBuoc(cboXe, "xe");
            String s = JOptionPane.showInputDialog(this, "Số tháng đăng ký:", "1");
            if (s == null) {
                return;
            }
            int soThang = Integer.parseInt(s.trim());
            long tong = TruyCapVeThang.dangKy(maKH, maXe, soThang, PhienDangNhap.nhanVien.maNV, "Tiền mặt");
            thongBao("Đăng ký thành công. Tổng tiền: " + tong);
            taiLai();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void capNhatHetHan() {
        try {
            TruyCapVeThang.capNhatHetHan();
            taiLai();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã vé", "Mã KH", "Mã xe", "Ngày bắt đầu", "Ngày hết hạn", "Giá vé", "Trạng thái"};
    }

    @Override
    protected List<VeThang> taiDuLieu() throws Exception {
        TienIch.napChon(cboKhachHang, TienIch.danhSachChon("SELECT MaKH, HoTen FROM KhachHang ORDER BY MaKH"), false);
        TienIch.napChon(cboXe, TienIch.danhSachChon("SELECT MaXe, BienSo FROM Xe ORDER BY MaXe"), false);
        return TruyCapVeThang.layTatCa();
    }

    @Override
    protected Object[] hang(VeThang d) {
        return new Object[]{d.maVe, d.maKH, d.maXe, d.ngayBatDau, d.ngayHetHan, d.giaVe, d.trangThai};
    }

    @Override
    protected void doVaoForm(VeThang d) {
        TienIch.chon(cboKhachHang, d.maKH);
        TienIch.chon(cboXe, d.maXe);
        oNgayBatDau.setText(String.valueOf(d.ngayBatDau));
        oNgayHetHan.setText(String.valueOf(d.ngayHetHan));
        oGiaVe.setText(String.valueOf(d.giaVe));
        cboTrangThai.setSelectedItem(d.trangThai);
    }

    @Override
    protected VeThang layTuForm(VeThang goc) throws Exception {
        VeThang v = new VeThang();
        if (goc != null) {
            v.maVe = goc.maVe;
        }
        v.maKH = TienIch.maBatBuoc(cboKhachHang, "khách hàng");
        v.maXe = TienIch.maBatBuoc(cboXe, "xe");
        v.ngayBatDau = TienIch.ngay(batBuoc(oNgayBatDau.getText(), "ngày bắt đầu"));
        v.ngayHetHan = TienIch.ngay(batBuoc(oNgayHetHan.getText(), "ngày hết hạn"));
        v.giaVe = Long.parseLong(batBuoc(oGiaVe.getText(), "giá vé"));
        v.trangThai = (String) cboTrangThai.getSelectedItem();
        return v;
    }

    @Override
    protected void xoaForm() {
        oNgayBatDau.setText("");
        oNgayHetHan.setText("");
        oGiaVe.setText("");
        cboTrangThai.setSelectedIndex(0);
    }

    @Override
    protected void thucHienThem(VeThang d) throws Exception {
        TruyCapVeThang.them(d);
    }

    @Override
    protected void thucHienSua(VeThang d) throws Exception {
        TruyCapVeThang.sua(d);
    }

    @Override
    protected void thucHienXoa(VeThang d) throws Exception {
        TruyCapVeThang.xoa(d.maVe);
    }
}
