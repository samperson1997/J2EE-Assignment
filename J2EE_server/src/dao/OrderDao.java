package dao;

import java.util.List;

public interface OrderDao {

    int findTotalOrder(String id);

    List findOrder(String id, int start, int pageSize, int rowCount);
}
