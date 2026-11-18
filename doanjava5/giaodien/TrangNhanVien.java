package giaodien;

import java.util.List;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import thucthe.NhanVien;
import truycap.TruyCapNhanVien;

public class TrangNhanVien extends TrangCoSo<NhanVien> {
    private final JTextField oHoTen = themTruong("Họ tên");
    private final JTextField oSdt = themTruong("Số điện thoại");
    private final JTextField oChucVu = themTruong("Chức vụ");
    private final JTextField oTaiKhoan = themTruong("Tài khoản");
    private final JPasswordField oMatKhau = new JPasswordField();

    public TrangNhanVien() {
        themThanhPhan("Mật khẩu (để trống nếu giữ nguyên)", oMatKhau);
        oChucVu.setText("Bảo vệ");
        khoiTao();
    }

    @Override
    protected String[] cacCot() {
        return new String[]{"Mã NV", "Họ tên", "Số điện thoại", "Chức vụ", "Tài khoản"};
    }

    @Override
    protected List<NhanVien> taiDuLieu() throws Exception {
        return TruyCapNhanVien.layTatCa();
    }

    @Override
    protected Object[] hang(NhanVien d) {
        return new Object[]{d.maNV, d.hoTen, d.sdt, d.chucVu, d.taiKhoan};
    }

    @Override
    protected void doVaoForm(NhanVien d) {
        oHoTen.setText(d.hoTen);
        oSdt.setText(d.sdt);
        oChucVu.setText(d.chucVu);
        oTaiKhoan.setText(d.taiKhoan);
        oMatKhau.setText("");
    }

    @Override
    protected NhanVien layTuForm(NhanVien goc) throws Exception {
        NhanVien n = new NhanVien();
        String matKhau = new String(oMatKhau.getPassword());
        if (goc != null) {
            n.maNV = goc.maNV;
        }
        n.hoTen = batBuoc(oHoTen.getText(), "họ tên");
        n.sdt = chuoiHoacNull(oSdt.getText());
        n.chucVu = batBuoc(oChucVu.getText(), "chức vụ");
        n.taiKhoan = batBuoc(oTaiKhoan.getText(), "tài khoản");
        if (matKhau.isEmpty()) {
            if (goc == null) {
                throw new Exception("Vui lòng nhập mật khẩu.");
            }
            n.matKhauHash = goc.matKhauHash;
        } else {
            n.matKhauHash = TienIch.bam(matKhau);
        }
        return n;
    }

    @Override
    protected void xoaForm() {
        oHoTen.setText("");
        oSdt.setText("");
        oChucVu.setText("Bảo vệ");
        oTaiKhoan.setText("");
        oMatKhau.setText("");
    }

    @Override
    protected void thucHienThem(NhanVien d) throws Exception {
        TruyCapNhanVien.them(d);
    }

    @Override
    protected void thucHienSua(NhanVien d) throws Exception {
        TruyCapNhanVien.sua(d);
    }

    @Override
    protected void thucHienXoa(NhanVien d) throws Exception {
        TruyCapNhanVien.xoa(d.maNV);
    }
}
