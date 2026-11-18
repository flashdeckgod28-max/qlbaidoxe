package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.VeThang;

public class TruyCapVeThang {
    public static List<VeThang> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM VeThang ORDER BY MaVe DESC", r -> {
            VeThang v = new VeThang();
            v.maVe = r.getInt("MaVe");
            v.maKH = r.getInt("MaKH");
            v.maXe = r.getInt("MaXe");
            v.ngayBatDau = r.getDate("NgayBatDau");
            v.ngayHetHan = r.getDate("NgayHetHan");
            v.giaVe = r.getLong("GiaVe");
            v.trangThai = r.getString("TrangThai");
            return v;
        });
    }

    public static void them(VeThang v) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO VeThang(MaKH, MaXe, NgayBatDau, NgayHetHan, GiaVe, TrangThai) VALUES (?, ?, ?, ?, ?, ?)",
                v.maKH, v.maXe, v.ngayBatDau, v.ngayHetHan, v.giaVe, v.trangThai);
    }

    public static void sua(VeThang v) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE VeThang SET MaKH = ?, MaXe = ?, NgayBatDau = ?, NgayHetHan = ?, GiaVe = ?, TrangThai = ? WHERE MaVe = ?",
                v.maKH, v.maXe, v.ngayBatDau, v.ngayHetHan, v.giaVe, v.trangThai, v.maVe);
    }

    public static void xoa(int maVe) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM VeThang WHERE MaVe = ?", maVe);
    }

    public static long dangKy(int maKH, int maXe, int soThang, int maNV, String phuongThuc) throws SQLException {
        return KetNoiCSDL.truyVan("EXEC sp_DangKyVeThang ?, ?, ?, ?, ?",
                r -> r.getLong("TongTien"), maKH, maXe, soThang, maNV, phuongThuc).get(0);
    }

    public static void capNhatHetHan() throws SQLException {
        KetNoiCSDL.thucThi("EXEC sp_CapNhatVeHetHan");
    }
}
