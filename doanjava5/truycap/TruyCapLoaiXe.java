package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.LoaiXe;

public class TruyCapLoaiXe {
    public static List<LoaiXe> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM LoaiXe ORDER BY MaLoai", r -> {
            LoaiXe l = new LoaiXe();
            l.maLoai = r.getInt("MaLoai");
            l.tenLoai = r.getString("TenLoai");
            return l;
        });
    }

    public static void them(LoaiXe l) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO LoaiXe(TenLoai) VALUES (?)", l.tenLoai);
    }

    public static void sua(LoaiXe l) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE LoaiXe SET TenLoai = ? WHERE MaLoai = ?", l.tenLoai, l.maLoai);
    }

    public static void xoa(int maLoai) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM LoaiXe WHERE MaLoai = ?", maLoai);
    }
}
