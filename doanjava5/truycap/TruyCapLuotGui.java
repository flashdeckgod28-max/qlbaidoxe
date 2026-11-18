package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.LuotGui;

public class TruyCapLuotGui {
    public static List<LuotGui> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan(
                "SELECT l.*, x.BienSo, v.TenViTri FROM LuotGui l "
                + "JOIN Xe x ON x.MaXe = l.MaXe JOIN ViTri v ON v.MaViTri = l.MaViTri ORDER BY l.MaLuot DESC", r -> {
            LuotGui l = new LuotGui();
            l.maLuot = r.getInt("MaLuot");
            l.maXe = r.getInt("MaXe");
            l.maViTri = r.getInt("MaViTri");
            l.maVe = KetNoiCSDL.soNguyenHoacNull(r, "MaVe");
            l.gioVao = r.getTimestamp("GioVao");
            l.gioRa = r.getTimestamp("GioRa");
            l.maNVVao = r.getInt("NV_Vao");
            l.maNVRa = KetNoiCSDL.soNguyenHoacNull(r, "NV_Ra");
            l.trangThai = r.getString("TrangThai");
            l.ghiChu = r.getString("GhiChu");
            l.bienSo = r.getString("BienSo");
            l.tenViTri = r.getString("TenViTri");
            return l;
        });
    }

    public static int xeVao(String bienSo, int maLoai, int maNV) throws SQLException {
        return KetNoiCSDL.truyVan("EXEC sp_XeVao ?, ?, ?", r -> r.getInt("MaLuot"), bienSo, maLoai, maNV).get(0);
    }

    public static long xeRa(int maLuot, int maNV, String phuongThuc) throws SQLException {
        return KetNoiCSDL.truyVan("EXEC sp_XeRa ?, ?, ?", r -> r.getLong("SoTien"), maLuot, maNV, phuongThuc).get(0);
    }

    public static void capNhatGhiChu(int maLuot, String ghiChu) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE LuotGui SET GhiChu = ? WHERE MaLuot = ?", ghiChu, maLuot);
    }
}
