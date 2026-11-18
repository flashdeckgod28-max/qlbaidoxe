package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.CaTruc;

public class TruyCapCaTruc {
    public static List<CaTruc> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM CaTruc ORDER BY MaCa DESC", r -> {
            CaTruc c = new CaTruc();
            c.maCa = r.getInt("MaCa");
            c.maNV = r.getInt("MaNV");
            c.gioBatDau = r.getTimestamp("GioBatDau");
            c.gioKetThuc = r.getTimestamp("GioKetThuc");
            return c;
        });
    }

    public static void them(CaTruc c) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO CaTruc(MaNV, GioBatDau, GioKetThuc) VALUES (?, ?, ?)",
                c.maNV, c.gioBatDau, c.gioKetThuc);
    }

    public static void sua(CaTruc c) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE CaTruc SET MaNV = ?, GioBatDau = ?, GioKetThuc = ? WHERE MaCa = ?",
                c.maNV, c.gioBatDau, c.gioKetThuc, c.maCa);
    }

    public static void xoa(int maCa) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM CaTruc WHERE MaCa = ?", maCa);
    }
}
