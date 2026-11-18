package giaodien;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import ketnoi.KetNoiCSDL;

public class TienIch {
    public static List<MucChon> danhSachChon(String sql) throws SQLException {
        return KetNoiCSDL.truyVan(sql, r -> new MucChon(r.getInt(1), r.getString(2)));
    }

    public static Integer maDaChon(JComboBox<MucChon> o) {
        Object d = o.getSelectedItem();
        if (!(d instanceof MucChon)) {
            return null;
        }
        int ma = ((MucChon) d).ma;
        return ma == 0 ? null : ma;
    }

    public static int maBatBuoc(JComboBox<MucChon> o, String ten) throws Exception {
        Integer ma = maDaChon(o);
        if (ma == null) {
            throw new Exception("Vui lòng chọn " + ten + ".");
        }
        return ma;
    }

    public static void chon(JComboBox<MucChon> o, Integer ma) {
        if (ma != null) {
            for (int i = 0; i < o.getItemCount(); i++) {
                if (o.getItemAt(i).ma == ma) {
                    o.setSelectedIndex(i);
                    return;
                }
            }
        }
        if (o.getItemCount() > 0) {
            o.setSelectedIndex(0);
        }
    }

    public static void napChon(JComboBox<MucChon> o, List<MucChon> ds, boolean choPhepRong) {
        Integer cu = maDaChon(o);
        o.removeAllItems();
        if (choPhepRong) {
            o.addItem(new MucChon(0, "(Không có)"));
        }
        for (MucChon m : ds) {
            o.addItem(m);
        }
        chon(o, cu);
    }

    public static Timestamp gio(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            return null;
        }
        if (s.length() == 16) {
            s = s + ":00";
        }
        return Timestamp.valueOf(s);
    }

    public static java.sql.Date ngay(String s) {
        s = s.trim();
        return s.isEmpty() ? null : java.sql.Date.valueOf(s);
    }

    public static String hienGio(Timestamp t) {
        return t == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t);
    }

    public static String bam(String s) throws Exception {
        byte[] b = MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte x : b) {
            sb.append(String.format("%02x", x));
        }
        return sb.toString();
    }

    public static DefaultTableModel taiBang(String sql, Object... tham) throws SQLException {
        try (Connection c = KetNoiCSDL.layKetNoi(); PreparedStatement ps = c.prepareStatement(sql)) {
            KetNoiCSDL.ganThamSo(ps, tham);
            try (ResultSet r = ps.executeQuery()) {
                ResultSetMetaData md = r.getMetaData();
                int n = md.getColumnCount();
                DefaultTableModel mo = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int h, int c) {
                        return false;
                    }
                };
                for (int i = 1; i <= n; i++) {
                    mo.addColumn(md.getColumnLabel(i));
                }
                while (r.next()) {
                    Object[] h = new Object[n];
                    for (int i = 0; i < n; i++) {
                        h[i] = r.getObject(i + 1);
                    }
                    mo.addRow(h);
                }
                return mo;
            }
        }
    }
}
