package factory;

import dao.OrderDao;
import daoImpl.OrderDaoImpl;

public class DaoFactory {
    public static OrderDao getStockDao() {
        return OrderDaoImpl.getInstance();
    }

}
