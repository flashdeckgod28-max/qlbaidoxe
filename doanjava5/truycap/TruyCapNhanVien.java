package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.NhanVien;

public class TruyCapNhanVien {
    private static NhanVien anhXa(java.sql.ResultSet r) throws SQLException {
        NhanVien n = new NhanVien();
        n.maNV = r.getInt("MaNV");
        n.hoTen = r.getString("HoTen");
        n.sdt = r.getString("SDT");
        n.chucVu = r.getString("ChucVu");
        n.taiKhoan = r.getString("TaiKhoan");
        n.matKhauHash = r.getString("MatKhauHash");
        return n;
    }

    public static List<NhanVien> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM NhanVien ORDER BY MaNV", TruyCapNhanVien::anhXa);
    }

    public static NhanVien dangNhap(String taiKhoan, String matKhau, String matKhauBam) throws SQLException {
        List<NhanVien> ds = KetNoiCSDL.truyVan(
                "SELECT * FROM NhanVien WHERE TaiKhoan = ? AND (MatKhauHash = ? OR MatKhauHash = ?)",
                TruyCapNhanVien::anhXa, taiKhoan, matKhau, matKhauBam);
        return ds.isEmpty() ? null : ds.get(0);
    }

    public static void them(NhanVien n) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO NhanVien(HoTen, SDT, ChucVu, TaiKhoan, MatKhauHash) VALUES (?, ?, ?, ?, ?)",
                n.hoTen, n.sdt, n.chucVu, n.taiKhoan, n.matKhauHash);
    }

    public static void sua(NhanVien n) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE NhanVien SET HoTen = ?, SDT = ?, ChucVu = ?, TaiKhoan = ?, MatKhauHash = ? WHERE MaNV = ?",
                n.hoTen, n.sdt, n.chucVu, n.taiKhoan, n.matKhauHash, n.maNV);
    }

    public static void xoa(int maNV) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM NhanVien WHERE MaNV = ?", maNV);
    }
}
