package ketnoi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KetNoiCSDL {

        private static final String URL =
                "jdbc:sqlserver://127.0.0.1:1433;databaseName=QuanLyBaiGuiXe;encrypt=true;trustServerCertificate=true;";
        private static final String TAI_KHOAN = "QuanLyBaiGuiXe";
        private static final String MAT_KHAU = "123456";

        public static Connection layKetNoi() throws SQLException {
            return DriverManager.getConnection(URL, TAI_KHOAN, MAT_KHAU);
        }

    public static void ganThamSo(PreparedStatement ps, Object[] tham) throws SQLException {
        for (int i = 0; i < tham.length; i++) {
            ps.setObject(i + 1, tham[i]);
        }
    }

    public static <T> List<T> truyVan(String sql, AnhXaDong<T> anhXa, Object... tham) throws SQLException {
        List<T> ketQua = new ArrayList<>();
        try (Connection c = layKetNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ganThamSo(ps, tham);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    ketQua.add(anhXa.anhXa(r));
                }
            }
        }
        return ketQua;
    }

    public static int thucThi(String sql, Object... tham) throws SQLException {
        try (Connection c = layKetNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            ganThamSo(ps, tham);
            return ps.executeUpdate();
        }
    }

    public static Integer soNguyenHoacNull(ResultSet r, String cot) throws SQLException {
        int v = r.getInt(cot);
        return r.wasNull() ? null : v;
    }
}
