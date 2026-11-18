package truycap;

import java.sql.SQLException;
import java.util.List;
import ketnoi.KetNoiCSDL;
import thucthe.BangGia;

public class TruyCapBangGia {
    public static List<BangGia> layTatCa() throws SQLException {
        return KetNoiCSDL.truyVan("SELECT * FROM BangGia ORDER BY MaGia", r -> {
            BangGia b = new BangGia();
            b.maGia = r.getInt("MaGia");
            b.maLoai = r.getInt("MaLoai");
            b.hinhThuc = r.getString("HinhThuc");
            b.donGia = r.getLong("DonGia");
            b.ngayApDung = r.getDate("NgayApDung");
            b.ngayKetThuc = r.getDate("NgayKetThuc");
            return b;
        });
    }

    public static void them(BangGia b) throws SQLException {
        KetNoiCSDL.thucThi("INSERT INTO BangGia(MaLoai, HinhThuc, DonGia, NgayApDung, NgayKetThuc) VALUES (?, ?, ?, ?, ?)",
                b.maLoai, b.hinhThuc, b.donGia, b.ngayApDung, b.ngayKetThuc);
    }

    public static void sua(BangGia b) throws SQLException {
        KetNoiCSDL.thucThi("UPDATE BangGia SET MaLoai = ?, HinhThuc = ?, DonGia = ?, NgayApDung = ?, NgayKetThuc = ? WHERE MaGia = ?",
                b.maLoai, b.hinhThuc, b.donGia, b.ngayApDung, b.ngayKetThuc, b.maGia);
    }

    public static void xoa(int maGia) throws SQLException {
        KetNoiCSDL.thucThi("DELETE FROM BangGia WHERE MaGia = ?", maGia);
    }
}
