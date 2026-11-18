package ketnoi;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface AnhXaDong<T> {
    T anhXa(ResultSet r) throws SQLException;
}
