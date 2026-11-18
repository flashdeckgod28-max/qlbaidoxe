package giaodien;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ChuongTrinhChinh {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo g : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(g.getName())) {
                    UIManager.setLookAndFeel(g.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new ManHinhDangNhap().setVisible(true));
    }
}
