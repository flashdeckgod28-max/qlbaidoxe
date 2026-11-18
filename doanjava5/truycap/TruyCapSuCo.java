package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.SuCo;

public class TruyCapSuCo {
    public static List<SuCo> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM SuCo ORDER BY MaSC DESC", r -> {
            SuCo s = new SuCo();
            s.maSC = r.getInt("MaSC");
            s.maLuot = r.getInt("MaLuot");
            s.loaiSuCo = r.getString("LoaiSuCo");
            s.moTa = r.getString("MoTa");
            s.thoiGian = r.getTimestamp("ThoiGian");
            s.huongXuLy = r.getString("HuongXuLy");
            return s;
        });
    }

    public static void them(SuCo s) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO SuCo(MaLuot, LoaiSuCo, MoTa, HuongXuLy) VALUES (?, ?, ?, ?)",
                s.maLuot, s.loaiSuCo, s.moTa, s.huongXuLy);
    }

    public static void sua(SuCo s) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE SuCo SET MaLuot = ?, LoaiSuCo = ?, MoTa = ?, HuongXuLy = ? WHERE MaSC = ?",
                s.maLuot, s.loaiSuCo, s.moTa, s.huongXuLy, s.maSC);
    }

    public static void xoa(int maSC) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM SuCo WHERE MaSC = ?", maSC);
    }
}
