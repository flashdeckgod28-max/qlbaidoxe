package giaodien;

import java.sql.Timestamp;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import thucthe.CaTruc;
import truycap.TruyCapCaTruc;

public class TrangCaTruc extends TrangCoSo<CaTruc> {
    private final JComboBox<MucChon> cboNhanVien = themChon("Nhân viên");
    private final JTextField oBatDau = themTruong("Giờ bắt đầu (yyyy-MM-dd HH:mm)");
    private final JTextField oKetThuc = themTruong("Giờ kết thúc (yyyy-MM-dd HH:mm)");

    public TrangCaTruc() {
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã ca", "Mã NV", "Giờ bắt đầu", "Giờ kết thúc"};
    }

    @Override
    protected List<CaTruc> taiDuLieu() throws Exception {
        TienIch.napChon(cboNhanVien, TienIch.danhSachChon("SELECT MaNV, HoTen FROM NhanVien ORDER BY MaNV"), false);
        return TruyCapCaTruc.layTatCa();
    }

    @Override
    protected Object[] hang(CaTruc d) {
        return new Object[]{d.maCa, d.maNV, TienIch.hienGio(d.gioBatDau), TienIch.hienGio(d.gioKetThuc)};
    }

    @Override
    protected void doVaoForm(CaTruc d) {
        TienIch.chon(cboNhanVien, d.maNV);
        oBatDau.setText(TienIch.hienGio(d.gioBatDau));
        oKetThuc.setText(TienIch.hienGio(d.gioKetThuc));
    }

    @Override
    protected CaTruc layTuForm(CaTruc goc) throws Exception {
        CaTruc c = new CaTruc();
        if (goc != null) {
            c.maCa = goc.maCa;
        }
        c.maNV = TienIch.maBatBuoc(cboNhanVien, "nhân viên");
        c.gioBatDau = oBatDau.getText().trim().isEmpty()
                ? new Timestamp(System.currentTimeMillis()) : TienIch.gio(oBatDau.getText());
        c.gioKetThuc = TienIch.gio(oKetThuc.getText());
        return c;
    }

    @Override
    protected void xoaForm() {
        oBatDau.setText("");
        oKetThuc.setText("");
    }

    @Override
    protected void thucHienThem(CaTruc d) throws Exception {
        TruyCapCaTruc.them(d);
    }

    @Override
    protected void thucHienSua(CaTruc d) throws Exception {
        TruyCapCaTruc.sua(d);
    }

    @Override
    protected void thucHienXoa(CaTruc d) throws Exception {
        TruyCapCaTruc.xoa(d.maCa);
    }
}
