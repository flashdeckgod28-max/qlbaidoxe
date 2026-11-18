package giaodien;

import java.util.List;
import javax.swing.JTextField;
import thucthe.LoaiXe;
import truycap.TruyCapLoaiXe;

public class TrangLoaiXe extends TrangCoSo<LoaiXe> {
    private final JTextField oTenLoai = themTruong("Tên loại xe");

    public TrangLoaiXe() {
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã loại", "Tên loại xe"};
    }

    @Override
    protected List<LoaiXe> taiDuLieu() throws Exception {
        return TruyCapLoaiXe.layTatCa();
    }

    @Override
    protected Object[] hang(LoaiXe d) {
        return new Object[]{d.maLoai, d.tenLoai};
    }

    @Override
    protected void doVaoForm(LoaiXe d) {
        oTenLoai.setText(d.tenLoai);
    }

    @Override
    protected LoaiXe layTuForm(LoaiXe goc) throws Exception {
        LoaiXe l = new LoaiXe();
        if (goc != null) {
            l.maLoai = goc.maLoai;
        }
        l.tenLoai = batBuoc(oTenLoai.getText(), "tên loại xe");
        return l;
    }

    @Override
    protected void xoaForm() {
        oTenLoai.setText("");
    }

    @Override
    protected void thucHienThem(LoaiXe d) throws Exception {
        TruyCapLoaiXe.them(d);
    }

    @Override
    protected void thucHienSua(LoaiXe d) throws Exception {
        TruyCapLoaiXe.sua(d);
    }

    @Override
    protected void thucHienXoa(LoaiXe d) throws Exception {
        TruyCapLoaiXe.xoa(d.maLoai);
    }
}
