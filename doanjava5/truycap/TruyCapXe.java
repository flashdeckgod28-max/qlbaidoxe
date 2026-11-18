package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.Xe;

public class TruyCapXe {
    public static List<Xe> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM Xe ORDER BY MaXe", r -> {
            Xe x = new Xe();
            x.maXe = r.getInt("MaXe");
            x.bienSo = r.getString("BienSo");
            x.maLoai = r.getInt("MaLoai");
            x.maKH = KetNoiCSDL.soNguyenHoacNull(r, "MaKH");
            x.mauSac = r.getString("MauSac");
            x.hangXe = r.getString("HangXe");
            return x;
        });
    }

    public static void them(Xe x) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO Xe(BienSo, MaLoai, MaKH, MauSac, HangXe) VALUES (?, ?, ?, ?, ?)",
                x.bienSo, x.maLoai, x.maKH, x.mauSac, x.hangXe);
    }

    public static void sua(Xe x) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE Xe SET BienSo = ?, MaLoai = ?, MaKH = ?, MauSac = ?, HangXe = ? WHERE MaXe = ?",
                x.bienSo, x.maLoai, x.maKH, x.mauSac, x.hangXe, x.maXe);
    }

    public static void xoa(int maXe) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM Xe WHERE MaXe = ?", maXe);
    }
}
