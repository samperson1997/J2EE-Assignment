package serviceImpl;

import factory.DaoFactory;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static OrderService orderService = new OrderServiceImpl();

    public static OrderService getInstance() {
        return orderService;
    }

    @Override
    public int findTotalOrder(String id) {
        return DaoFactory.getOrderDao().findTotalOrder(id);
    }

    @Override
    public List findOrder(String id, int start) {
        return DaoFactory.getOrderDao().findOrder(id, start);
    }
}
