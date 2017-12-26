package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DaoHelper {
    Connection getConnection();

    void closeConnection(Connection con);

    void closePreparedStatement(PreparedStatement stmt);

    void closeResult(ResultSet result);
}
