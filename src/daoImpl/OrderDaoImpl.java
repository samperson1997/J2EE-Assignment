package daoImpl;

import dao.DaoHelper;
import dao.OrderDao;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();
    private static DaoHelper daoHelper = DaoHelperImpl.getBaseDaoInstance();

    public OrderDaoImpl() {

    }

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }

    @Override
    public int findTotalOrder(String id) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        int total = 1;
        try {
            stmt = con.prepareStatement("SELECT * FROM user_orders WHERE user_id = ?");
            stmt.setString(1, id);
            result = stmt.executeQuery();
            while (result.next()) {
                total++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoHelper.closeConnection(con);
            daoHelper.closePreparedStatement(stmt);
            daoHelper.closeResult(result);
        }
        return total;
    }

    @Override
    public List findOrder(String id, int start) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        List<Order> list = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM user_orders WHERE user_id = ? LIMIT ?, 5");
            stmt.setString(1, id);
            stmt.setInt(2, start);

            result = stmt.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("order_id"));
                order.setDate(result.getDate("order_date"));
                order.setArticleName(result.getString("article_name"));
                order.setArticleNum(result.getDouble("article_num"));
                order.setPrice(result.getDouble("price"));
                order.setIsAvailable(result.getInt("is_available"));

                list.add(order);
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
}
