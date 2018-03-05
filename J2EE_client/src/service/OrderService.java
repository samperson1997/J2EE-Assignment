package service;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface OrderService {
    int findTotalOrder(String id);

    List findOrder(String id, int start, int pageSize, int rowCount);
}
