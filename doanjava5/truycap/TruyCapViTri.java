package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.ViTri;

public class TruyCapViTri {
    public static List<ViTri> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM ViTri ORDER BY MaViTri", r -> {
            ViTri v = new ViTri();
            v.maViTri = r.getInt("MaViTri");
            v.tenViTri = r.getString("TenViTri");
            v.khuVuc = r.getString("KhuVuc");
            v.tang = r.getInt("Tang");
            v.maLoai = r.getInt("MaLoai");
            v.trangThai = r.getString("TrangThai");
            return v;
        });
    }

    public static void them(ViTri v) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO ViTri(TenViTri, KhuVuc, Tang, MaLoai, TrangThai) VALUES (?, ?, ?, ?, ?)",
                v.tenViTri, v.khuVuc, v.tang, v.maLoai, v.trangThai);
    }

    public static void sua(ViTri v) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE ViTri SET TenViTri = ?, KhuVuc = ?, Tang = ?, MaLoai = ?, TrangThai = ? WHERE MaViTri = ?",
                v.tenViTri, v.khuVuc, v.tang, v.maLoai, v.trangThai, v.maViTri);
    }

    public static void xoa(int maViTri) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM ViTri WHERE MaViTri = ?", maViTri);
    }
}
