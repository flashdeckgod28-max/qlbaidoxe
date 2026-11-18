package giaodien;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import thucthe.SuCo;
import truycap.TruyCapSuCo;

public class TrangSuCo extends TrangCoSo<SuCo> {
    private final JComboBox<MucChon> cboLuot = themChon("Lượt gửi");
    private final JTextField oLoaiSuCo = themTruong("Loại sự cố");
    private final JTextField oMoTa = themTruong("Mô tả");
    private final JTextField oHuongXuLy = themTruong("Hướng xử lý");

    public TrangSuCo() {
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã SC", "Mã lượt", "Loại sự cố", "Mô tả", "Thời gian", "Hướng xử lý"};
    }

    @Override
    protected List<SuCo> taiDuLieu() throws Exception {
        TienIch.napChon(cboLuot, TienIch.danhSachChon(
                "SELECT l.MaLuot, CONCAT(N'Lượt ', l.MaLuot, N' - ', x.BienSo) FROM LuotGui l JOIN Xe x ON x.MaXe = l.MaXe ORDER BY l.MaLuot DESC"), false);
        return TruyCapSuCo.layTatCa();
    }

    @Override
    protected Object[] hang(SuCo d) {
        return new Object[]{d.maSC, d.maLuot, d.loaiSuCo, d.moTa, TienIch.hienGio(d.thoiGian), d.huongXuLy};
    }

    @Override
    protected void doVaoForm(SuCo d) {
        TienIch.chon(cboLuot, d.maLuot);
        oLoaiSuCo.setText(d.loaiSuCo);
        oMoTa.setText(d.moTa);
        oHuongXuLy.setText(d.huongXuLy);
    }

    @Override
    protected SuCo layTuForm(SuCo goc) throws Exception {
        SuCo s = new SuCo();
        if (goc != null) {
            s.maSC = goc.maSC;
        }
        s.maLuot = TienIch.maBatBuoc(cboLuot, "lượt gửi");
        s.loaiSuCo = batBuoc(oLoaiSuCo.getText(), "loại sự cố");
        s.moTa = chuoiHoacNull(oMoTa.getText());
        s.huongXuLy = chuoiHoacNull(oHuongXuLy.getText());
        return s;
    }

    @Override
    protected void xoaForm() {
        oLoaiSuCo.setText("");
        oMoTa.setText("");
        oHuongXuLy.setText("");
    }

    @Override
    protected void thucHienThem(SuCo d) throws Exception {
        TruyCapSuCo.them(d);
    }

    @Override
    protected void thucHienSua(SuCo d) throws Exception {
        TruyCapSuCo.sua(d);
    }

    @Override
    protected void thucHienXoa(SuCo d) throws Exception {
        TruyCapSuCo.xoa(d.maSC);
    }
}
