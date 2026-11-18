package giaodien;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public abstract class TrangCoSo<T> extends JPanel {
    protected JTable bang;
    protected DefaultTableModel moHinh;
    protected final JPanel khungNhap = new JPanel(new GridLayout(0, 4, 10, 8));
    protected final JPanel khungNut = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
    protected List<T> danhSach = new ArrayList<>();

    protected abstract String[] cacCot();

    protected abstract List<T> taiDuLieu() throws Exception;

    protected abstract Object[] hang(T d);

    protected abstract void doVaoForm(T d);

    protected abstract T layTuForm(T goc) throws Exception;

    protected abstract void xoaForm();

    protected abstract void thucHienThem(T d) throws Exception;

    protected abstract void thucHienSua(T d) throws Exception;

    protected abstract void thucHienXoa(T d) throws Exception;

    protected void khoiTao() {
        setLayout(new BorderLayout(8, 8));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        moHinh = new DefaultTableModel(cacCot(), 0) {
            @Override
            public boolean isCellEditable(int h, int c) {
                return false;
            }
        };
        bang = new JTable(moHinh);
        bang.setRowHeight(24);
        bang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int h = bang.getSelectedRow();
                if (h >= 0 && h < danhSach.size()) {
                    doVaoForm(danhSach.get(h));
                }
            }
        });

        themNut("Thêm", this::xuLyThem);
        themNut("Sửa", this::xuLySua);
        themNut("Xóa", this::xuLyXoa);
        themNut("Làm mới", () -> {
            xoaForm();
            taiLai();
        });

        JPanel bac = new JPanel(new BorderLayout());
        bac.setBorder(BorderFactory.createTitledBorder("Thông tin"));
        bac.add(khungNhap, BorderLayout.CENTER);

        add(bac, BorderLayout.NORTH);
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(khungNut, BorderLayout.SOUTH);
        taiLai();
    }

    protected void taiLai() {
        try {
            danhSach = taiDuLieu();
            moHinh.setRowCount(0);
            for (T d : danhSach) {
                moHinh.addRow(hang(d));
            }
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void xuLyThem() {
        try {
            thucHienThem(layTuForm(null));
            taiLai();
            xoaForm();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void xuLySua() {
        int h = bang.getSelectedRow();
        if (h < 0) {
            thongBao("Hãy chọn một dòng trong bảng.");
            return;
        }
        try {
            thucHienSua(layTuForm(danhSach.get(h)));
            taiLai();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    private void xuLyXoa() {
        int h = bang.getSelectedRow();
        if (h < 0) {
            thongBao("Hãy chọn một dòng trong bảng.");
            return;
        }
        int kq = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng đã chọn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (kq != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            thucHienXoa(danhSach.get(h));
            taiLai();
            xoaForm();
        } catch (Exception e) {
            baoLoi(e);
        }
    }

    protected void themThanhPhan(String nhan, JComponent c) {
        khungNhap.add(new JLabel(nhan));
        khungNhap.add(c);
    }

    protected JTextField themTruong(String nhan) {
        JTextField o = new JTextField();
        themThanhPhan(nhan, o);
        return o;
    }

    protected JComboBox<MucChon> themChon(String nhan) {
        JComboBox<MucChon> o = new JComboBox<>();
        themThanhPhan(nhan, o);
        return o;
    }

    protected JComboBox<String> themChonChuoi(String nhan, String... giaTri) {
        JComboBox<String> o = new JComboBox<>(giaTri);
        themThanhPhan(nhan, o);
        return o;
    }

    protected void themNut(String ten, Runnable hanhDong) {
        JButton n = new JButton(ten);
        n.addActionListener(e -> hanhDong.run());
        khungNut.add(n);
    }

    protected void thongBao(String noiDung) {
        JOptionPane.showMessageDialog(this, noiDung, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void baoLoi(Exception e) {
        String noiDung = e.getMessage();
        if (e instanceof IllegalArgumentException) {
            noiDung = "Dữ liệu nhập không hợp lệ. Kiểm tra số, ngày (yyyy-MM-dd) và giờ (yyyy-MM-dd HH:mm).";
        }
        JOptionPane.showMessageDialog(this, noiDung, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    protected static String batBuoc(String s, String ten) throws Exception {
        s = s.trim();
        if (s.isEmpty()) {
            throw new Exception("Vui lòng nhập " + ten + ".");
        }
        return s;
    }

    protected static String chuoiHoacNull(String s) {
        s = s.trim();
        return s.isEmpty() ? null : s;
    }

    protected static Integer soNguyenHoacNull(String s) {
        s = s.trim();
        return s.isEmpty() ? null : Integer.valueOf(s);
    }
}
