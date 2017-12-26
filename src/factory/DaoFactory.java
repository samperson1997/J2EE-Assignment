package factory;

import dao.OrderDao;
import dao.UserDao;
import daoImpl.OrderDaoImpl;
import daoImpl.UserDaoImpl;

public class DaoFactory {

    public static OrderDao getOrderDao() {
        return OrderDaoImpl.getInstance();
    }

    public static UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

}
