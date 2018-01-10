package daoImpl;

import dao.OrderDao;
import model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static OrderDaoImpl orderDao = new OrderDaoImpl();
    private HibernateUtil hibernateUtil;

    public OrderDaoImpl() {
        hibernateUtil = new HibernateUtil();
    }

    public static OrderDaoImpl getInstance() {
        return orderDao;
    }

    @Override
    public int findTotalOrder(String id) {
        Session session = hibernateUtil.getSession();

        Transaction tx = session.beginTransaction();

        String hql = "from model.Order as o where o.userId = " + id;
        Query query = session.createQuery(hql);
        List<Order> list = query.list();

        tx.commit();
        return list.size();
    }

    @Override
    public List findOrder(String id, int start, int pageSize) {
        Session session = hibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        String hql = "SELECT * FROM user_orders WHERE user_id = " + id + " LIMIT " + start + ", " + pageSize;
        Query query = session.createNativeQuery(hql, Order.class);
        List<Order> list = query.getResultList()
                ;

        tx.commit();

        return list;
    }
}
