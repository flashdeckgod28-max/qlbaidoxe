package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.ThanhToan;

public class TruyCapThanhToan {
    public static List<ThanhToan> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM ThanhToan ORDER BY MaTT DESC", r -> {
            ThanhToan t = new ThanhToan();
            t.maTT = r.getInt("MaTT");
            t.maLuot = KetNoiCSDL.soNguyenHoacNull(r, "MaLuot");
            t.maVe = KetNoiCSDL.soNguyenHoacNull(r, "MaVe");
            t.soTien = r.getLong("SoTien");
            t.phuongThuc = r.getString("PhuongThuc");
            t.thoiGian = r.getTimestamp("ThoiGian");
            t.maNV = r.getInt("MaNV");
            return t;
        });
    }

    public static void them(ThanhToan t) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO ThanhToan(MaLuot, MaVe, SoTien, PhuongThuc, MaNV) VALUES (?, ?, ?, ?, ?)",
                t.maLuot, t.maVe, t.soTien, t.phuongThuc, t.maNV);
    }

    public static void sua(ThanhToan t) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE ThanhToan SET MaLuot = ?, MaVe = ?, SoTien = ?, PhuongThuc = ?, MaNV = ? WHERE MaTT = ?",
                t.maLuot, t.maVe, t.soTien, t.phuongThuc, t.maNV, t.maTT);
    }

    public static void xoa(int maTT) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM ThanhToan WHERE MaTT = ?", maTT);
    }
}
