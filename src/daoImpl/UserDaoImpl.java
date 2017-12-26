package daoImpl;

import dao.DaoHelper;
import dao.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    public UserDaoImpl() {

    }

    public static UserDaoImpl getInstance() {
        return userDao;
    }

    @Override
    public String getPassword(String id) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        String res = null;
        try {
            stmt = con.prepareStatement("SELECT password FROM users WHERE user_id = ?");
            stmt.setString(1, id);
            result = stmt.executeQuery();
            result.next();
            res = result.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoHelper.closeConnection(con);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        return res;
    }
}
