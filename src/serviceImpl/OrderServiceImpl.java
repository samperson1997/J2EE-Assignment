package serviceImpl;

import dao.OrderDao;
import daoImpl.OrderDaoImpl;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public int findTotalOrder(String id) {
        return orderDao.findTotalOrder(id);
    }

    @Override
    public List findOrder(String id, int start) {
        return orderDao.findOrder(id, start);
    }
}
