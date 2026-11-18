package giaodien;

import java.time.LocalDate;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import thucthe.BangGia;
import truycap.TruyCapBangGia;

public class TrangBangGia extends TrangCoSo<BangGia> {
    private final JComboBox<MucChon> cboLoai = themChon("Loại xe");
    private final JComboBox<String> cboHinhThuc = themChonChuoi("Hình thức", "Lượt", "Giờ", "Ngày", "Tháng");
    private final JTextField oDonGia = themTruong("Đơn giá");
    private final JTextField oNgayApDung = themTruong("Ngày áp dụng (yyyy-MM-dd)");
    private final JTextField oNgayKetThuc = themTruong("Ngày kết thúc (yyyy-MM-dd)");

    public TrangBangGia() {
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã giá", "Mã loại", "Hình thức", "Đơn giá", "Ngày áp dụng", "Ngày kết thúc"};
    }

    @Override
    protected List<BangGia> taiDuLieu() throws Exception {
        TienIch.napChon(cboLoai, TienIch.danhSachChon("SELECT MaLoai, TenLoai FROM LoaiXe ORDER BY MaLoai"), false);
        return TruyCapBangGia.layTatCa();
    }

    @Override
    protected Object[] hang(BangGia d) {
        return new Object[]{d.maGia, d.maLoai, d.hinhThuc, d.donGia, d.ngayApDung, d.ngayKetThuc};
    }

    @Override
    protected void doVaoForm(BangGia d) {
        TienIch.chon(cboLoai, d.maLoai);
        cboHinhThuc.setSelectedItem(d.hinhThuc);
        oDonGia.setText(String.valueOf(d.donGia));
        oNgayApDung.setText(d.ngayApDung == null ? "" : d.ngayApDung.toString());
        oNgayKetThuc.setText(d.ngayKetThuc == null ? "" : d.ngayKetThuc.toString());
    }

    @Override
    protected BangGia layTuForm(BangGia goc) throws Exception {
        BangGia b = new BangGia();
        if (goc != null) {
            b.maGia = goc.maGia;
        }
        b.maLoai = TienIch.maBatBuoc(cboLoai, "loại xe");
        b.hinhThuc = (String) cboHinhThuc.getSelectedItem();
        b.donGia = Long.parseLong(batBuoc(oDonGia.getText(), "đơn giá"));
        b.ngayApDung = oNgayApDung.getText().trim().isEmpty()
                ? java.sql.Date.valueOf(LocalDate.now()) : TienIch.ngay(oNgayApDung.getText());
        b.ngayKetThuc = TienIch.ngay(oNgayKetThuc.getText());
        return b;
    }

    @Override
    protected void xoaForm() {
        oDonGia.setText("");
        oNgayApDung.setText("");
        oNgayKetThuc.setText("");
        cboHinhThuc.setSelectedIndex(0);
    }

    @Override
    protected void thucHienThem(BangGia d) throws Exception {
        TruyCapBangGia.them(d);
    }

    @Override
    protected void thucHienSua(BangGia d) throws Exception {
        TruyCapBangGia.sua(d);
    }

    @Override
    protected void thucHienXoa(BangGia d) throws Exception {
        TruyCapBangGia.xoa(d.maGia);
    }
}
