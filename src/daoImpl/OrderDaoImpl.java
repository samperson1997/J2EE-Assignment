package daoImpl;

import dao.DaoHelper;
import dao.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    private OrderDaoImpl() {

    }

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }

    @Override
    public List find(String column, String value) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList list = new ArrayList();
        try {
            stmt = con.prepareStatement("SELECT * FROM stock");
            result = stmt.executeQuery();
            while (result.next()) {
                // ……
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoHelper.closeConnection(con);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        return list;
    }

    @Override
    public List find(String name) {
        return null;
    }

    @Override
    public List find() {
        return null;
    }
}
