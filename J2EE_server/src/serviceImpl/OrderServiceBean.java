package serviceImpl;

import dao.OrderDao;
import factory.DaoFactory;
import service.OrderService;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class OrderServiceBean implements OrderService {

    private OrderDao orderDao = DaoFactory.getOrderDao();

    @Override
    public int findTotalOrder(String id) {
        return orderDao.findTotalOrder(id);
    }

    @Override
    public List findOrder(String id, int start, int pageSize, int rowCount) {
        return orderDao.findOrder(id, start, pageSize, rowCount);
    }
}
