package daoImpl;

import dao.OrderDao;
import model.Order;

import javax.persistence.*;
import java.util.List;

public class OrderDaoBean implements OrderDao {

    @PersistenceUnit(name = "nju")
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;

    private static OrderDaoBean orderDao = new OrderDaoBean();

    public OrderDaoBean() {
        factory = Persistence.createEntityManagerFactory("nju");
        em = factory.createEntityManager();
    }

    public static OrderDaoBean getInstance() {
        return orderDao;
    }

    @Override
    public int findTotalOrder(String userId) {
        try {
            TypedQuery<Order> query = em.createQuery("FROM model.Order o WHERE o.userId = userId", Order.class);
            List<Order> list = query.getResultList();

            em.clear();
            return list.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List findOrder(String id, int start, int pageSize, int rowCount) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM user_orders WHERE user_id = "
                    + id + " LIMIT " + start + ", " + pageSize, Order.class);
            List<Order> list = query.getResultList();

            em.clear();

            return list;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
