package giaodien;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import thucthe.ThanhToan;
import truycap.TruyCapThanhToan;

public class TrangThanhToan extends TrangCoSo<ThanhToan> {
    private final JTextField oMaLuot = themTruong("Mã lượt gửi");
    private final JTextField oMaVe = themTruong("Mã vé tháng");
    private final JTextField oSoTien = themTruong("Số tiền");
    private final JComboBox<String> cboPhuongThuc = themChonChuoi("Phương thức", "Tiền mặt", "Chuyển khoản", "Ví điện tử");
    private final JComboBox<MucChon> cboNhanVien = themChon("Nhân viên thu");

    public TrangThanhToan() {
        khoiTao();
        TienIch.chon(cboNhanVien, PhienDangNhap.nhanVien == null ? null : PhienDangNhap.nhanVien.maNV);
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã TT", "Mã lượt", "Mã vé", "Số tiền", "Phương thức", "Thời gian", "Mã NV"};
    }

    @Override
    protected List<ThanhToan> taiDuLieu() throws Exception {
        TienIch.napChon(cboNhanVien, TienIch.danhSachChon("SELECT MaNV, HoTen FROM NhanVien ORDER BY MaNV"), false);
        return TruyCapThanhToan.layTatCa();
    }

    @Override
    protected Object[] hang(ThanhToan d) {
        return new Object[]{d.maTT, d.maLuot, d.maVe, d.soTien, d.phuongThuc, TienIch.hienGio(d.thoiGian), d.maNV};
    }

    @Override
    protected void doVaoForm(ThanhToan d) {
        oMaLuot.setText(d.maLuot == null ? "" : String.valueOf(d.maLuot));
        oMaVe.setText(d.maVe == null ? "" : String.valueOf(d.maVe));
        oSoTien.setText(String.valueOf(d.soTien));
        cboPhuongThuc.setSelectedItem(d.phuongThuc);
        TienIch.chon(cboNhanVien, d.maNV);
    }

    @Override
    protected ThanhToan layTuForm(ThanhToan goc) throws Exception {
        ThanhToan t = new ThanhToan();
        if (goc != null) {
            t.maTT = goc.maTT;
        }
        t.maLuot = soNguyenHoacNull(oMaLuot.getText());
        t.maVe = soNguyenHoacNull(oMaVe.getText());
        if ((t.maLuot == null) == (t.maVe == null)) {
            throw new Exception("Chỉ nhập một trong hai: mã lượt gửi hoặc mã vé tháng.");
        }
        t.soTien = Long.parseLong(batBuoc(oSoTien.getText(), "số tiền"));
        t.phuongThuc = (String) cboPhuongThuc.getSelectedItem();
        t.maNV = TienIch.maBatBuoc(cboNhanVien, "nhân viên");
        return t;
    }

    @Override
    protected void xoaForm() {
        oMaLuot.setText("");
        oMaVe.setText("");
        oSoTien.setText("");
        cboPhuongThuc.setSelectedIndex(0);
    }

    @Override
    protected void thucHienThem(ThanhToan d) throws Exception {
        TruyCapThanhToan.them(d);
    }

    @Override
    protected void thucHienSua(ThanhToan d) throws Exception {
        TruyCapThanhToan.sua(d);
    }

    @Override
    protected void thucHienXoa(ThanhToan d) throws Exception {
        TruyCapThanhToan.xoa(d.maTT);
    }
}
