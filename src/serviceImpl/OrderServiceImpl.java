package serviceImpl;

import dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    private static OrderService orderService = new OrderServiceImpl();

    public static OrderService getInstance() {
        return orderService;
    }

    @Override
    public int findTotalOrder(String id) {
        return orderDao.findTotalOrder(id);
    }

    @Override
    public List findOrder(String id, int start, int pageSize) {
        return orderDao.findOrder(id, start, pageSize);
    }
}
