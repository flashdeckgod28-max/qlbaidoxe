package giaodien;

public class MucChon {
    public final int ma;
    public final String ten;

    public MucChon(int ma, String ten) {
        this.ma = ma;
        this.ten = ten;
    }

    @Override
    public String toString() {
        return ten;
    }
}
