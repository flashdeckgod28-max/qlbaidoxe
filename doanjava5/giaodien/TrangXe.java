package giaodien;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import thucthe.Xe;
import truycap.TruyCapXe;

public class TrangXe extends TrangCoSo<Xe> {
    private final JTextField oBienSo = themTruong("Biển số");
    private final JComboBox<MucChon> cboLoai = themChon("Loại xe");
    private final JComboBox<MucChon> cboKhachHang = themChon("Khách hàng");
    private final JTextField oMauSac = themTruong("Màu sắc");
    private final JTextField oHangXe = themTruong("Hãng xe");

    public TrangXe() {
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã xe", "Biển số", "Mã loại", "Mã KH", "Màu sắc", "Hãng xe"};
    }

    @Override
    protected List<Xe> taiDuLieu() throws Exception {
        TienIch.napChon(cboLoai, TienIch.danhSachChon("SELECT MaLoai, TenLoai FROM LoaiXe ORDER BY MaLoai"), false);
        TienIch.napChon(cboKhachHang, TienIch.danhSachChon("SELECT MaKH, HoTen FROM KhachHang ORDER BY MaKH"), true);
        return TruyCapXe.layTatCa();
    }

    @Override
    protected Object[] hang(Xe d) {
        return new Object[]{d.maXe, d.bienSo, d.maLoai, d.maKH, d.mauSac, d.hangXe};
    }

    @Override
    protected void doVaoForm(Xe d) {
        oBienSo.setText(d.bienSo);
        TienIch.chon(cboLoai, d.maLoai);
        TienIch.chon(cboKhachHang, d.maKH);
        oMauSac.setText(d.mauSac);
        oHangXe.setText(d.hangXe);
    }

    @Override
    protected Xe layTuForm(Xe goc) throws Exception {
        Xe x = new Xe();
        if (goc != null) {
            x.maXe = goc.maXe;
        }
        x.bienSo = batBuoc(oBienSo.getText(), "biển số");
        x.maLoai = TienIch.maBatBuoc(cboLoai, "loại xe");
        x.maKH = TienIch.maDaChon(cboKhachHang);
        x.mauSac = chuoiHoacNull(oMauSac.getText());
        x.hangXe = chuoiHoacNull(oHangXe.getText());
        return x;
    }

    @Override
    protected void xoaForm() {
        oBienSo.setText("");
        oMauSac.setText("");
        oHangXe.setText("");
        TienIch.chon(cboKhachHang, null);
    }

    @Override
    protected void thucHienThem(Xe d) throws Exception {
        TruyCapXe.them(d);
    }

    @Override
    protected void thucHienSua(Xe d) throws Exception {
        TruyCapXe.sua(d);
    }

    @Override
    protected void thucHienXoa(Xe d) throws Exception {
        TruyCapXe.xoa(d.maXe);
    }
}
