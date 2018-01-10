package service;

import java.util.List;

public interface OrderService {
    int findTotalOrder(String id);

    List findOrder(String id, int start, int pageSize);
}
