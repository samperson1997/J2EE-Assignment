package factory;

import dao.OrderDao;
import dao.UserDao;
import daoImpl.OrderDaoBean;
import daoImpl.UserDaoBean;

public class DaoFactory {

    public static OrderDao getOrderDao() {
        return OrderDaoBean.getInstance();
    }

    public static UserDao getUserDao() {
        return UserDaoBean.getInstance();
    }

}
