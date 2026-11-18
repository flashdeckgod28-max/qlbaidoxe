package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.KhachHang;

public class TruyCapKhachHang {
    public static List<KhachHang> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM KhachHang ORDER BY MaKH", r -> {
            KhachHang k = new KhachHang();
            k.maKH = r.getInt("MaKH");
            k.hoTen = r.getString("HoTen");
            k.sdt = r.getString("SDT");
            k.cccd = r.getString("CCCD");
            k.diaChi = r.getString("DiaChi");
            return k;
        });
    }

    public static void them(KhachHang k) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO KhachHang(HoTen, SDT, CCCD, DiaChi) VALUES (?, ?, ?, ?)",
                k.hoTen, k.sdt, k.cccd, k.diaChi);
    }

    public static void sua(KhachHang k) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE KhachHang SET HoTen = ?, SDT = ?, CCCD = ?, DiaChi = ? WHERE MaKH = ?",
                k.hoTen, k.sdt, k.cccd, k.diaChi, k.maKH);
    }

    public static void xoa(int maKH) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM KhachHang WHERE MaKH = ?", maKH);
    }
}
