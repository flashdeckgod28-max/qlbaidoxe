package giaodien;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import thucthe.ViTri;
import truycap.TruyCapViTri;

public class TrangViTri extends TrangCoSo<ViTri> {
    private final JTextField oTenViTri = themTruong("Tên vị trí");
    private final JTextField oKhuVuc = themTruong("Khu vực");
    private final JTextField oTang = themTruong("Tầng");
    private final JComboBox<MucChon> cboLoai = themChon("Loại xe");
    private final JComboBox<String> cboTrangThai = themChonChuoi("Trạng thái", "Trống", "Có xe", "Bảo trì");

    public TrangViTri() {
        oTang.setText("1");
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã vị trí", "Tên vị trí", "Khu vực", "Tầng", "Mã loại", "Trạng thái"};
    }

    @Override
    protected List<ViTri> taiDuLieu() throws Exception {
        TienIch.napChon(cboLoai, TienIch.danhSachChon("SELECT MaLoai, TenLoai FROM LoaiXe ORDER BY MaLoai"), false);
        return TruyCapViTri.layTatCa();
    }

    @Override
    protected Object[] hang(ViTri d) {
        return new Object[]{d.maViTri, d.tenViTri, d.khuVuc, d.tang, d.maLoai, d.trangThai};
    }

    @Override
    protected void doVaoForm(ViTri d) {
        oTenViTri.setText(d.tenViTri);
        oKhuVuc.setText(d.khuVuc);
        oTang.setText(String.valueOf(d.tang));
        TienIch.chon(cboLoai, d.maLoai);
        cboTrangThai.setSelectedItem(d.trangThai);
    }

    @Override
    protected ViTri layTuForm(ViTri goc) throws Exception {
        ViTri v = new ViTri();
        if (goc != null) {
            v.maViTri = goc.maViTri;
        }
        v.tenViTri = batBuoc(oTenViTri.getText(), "tên vị trí");
        v.khuVuc = batBuoc(oKhuVuc.getText(), "khu vực");
        v.tang = Integer.parseInt(batBuoc(oTang.getText(), "tầng"));
        v.maLoai = TienIch.maBatBuoc(cboLoai, "loại xe");
        v.trangThai = (String) cboTrangThai.getSelectedItem();
        return v;
    }

    @Override
    protected void xoaForm() {
        oTenViTri.setText("");
        oKhuVuc.setText("");
        oTang.setText("1");
        cboTrangThai.setSelectedIndex(0);
    }

    @Override
    protected void thucHienThem(ViTri d) throws Exception {
        TruyCapViTri.them(d);
    }

    @Override
    protected void thucHienSua(ViTri d) throws Exception {
        TruyCapViTri.sua(d);
    }

    @Override
    protected void thucHienXoa(ViTri d) throws Exception {
        TruyCapViTri.xoa(d.maViTri);
    }
}
