package giaodien;

import java.util.List;
import javax.swing.JTextField;
import thucthe.KhachHang;
import truycap.TruyCapKhachHang;

public class TrangKhachHang extends TrangCoSo<KhachHang> {
    private final JTextField oHoTen = themTruong("Họ tên");
    private final JTextField oSdt = themTruong("Số điện thoại");
    private final JTextField oCccd = themTruong("CCCD");
    private final JTextField oDiaChi = themTruong("Địa chỉ");

    public TrangKhachHang() {
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã KH", "Họ tên", "Số điện thoại", "CCCD", "Địa chỉ"};
    }

    @Override
    protected List<KhachHang> taiDuLieu() throws Exception {
        return TruyCapKhachHang.layTatCa();
    }

    @Override
    protected Object[] hang(KhachHang d) {
        return new Object[]{d.maKH, d.hoTen, d.sdt, d.cccd, d.diaChi};
    }

    @Override
    protected void doVaoForm(KhachHang d) {
        oHoTen.setText(d.hoTen);
        oSdt.setText(d.sdt);
        oCccd.setText(d.cccd);
        oDiaChi.setText(d.diaChi);
    }

    @Override
    protected KhachHang layTuForm(KhachHang goc) throws Exception {
        KhachHang k = new KhachHang();
        if (goc != null) {
            k.maKH = goc.maKH;
        }
        k.hoTen = batBuoc(oHoTen.getText(), "họ tên");
        k.sdt = batBuoc(oSdt.getText(), "số điện thoại");
        k.cccd = chuoiHoacNull(oCccd.getText());
        k.diaChi = chuoiHoacNull(oDiaChi.getText());
        return k;
    }

    @Override
    protected void xoaForm() {
        oHoTen.setText("");
        oSdt.setText("");
        oCccd.setText("");
        oDiaChi.setText("");
    }

    @Override
    protected void thucHienThem(KhachHang d) throws Exception {
        TruyCapKhachHang.them(d);
    }

    @Override
    protected void thucHienSua(KhachHang d) throws Exception {
        TruyCapKhachHang.sua(d);
    }

    @Override
    protected void thucHienXoa(KhachHang d) throws Exception {
        TruyCapKhachHang.xoa(d.maKH);
    }
}
