package factory;

import service.OrderService;
import service.UserService;
import serviceImpl.OrderServiceImpl;
import serviceImpl.UserServiceImpl;

public class ServiceFactory {

    public static OrderService getOrderService() {
        return OrderServiceImpl.getInstance();
    }

    public static UserService getUserService() {
        return UserServiceImpl.getInstance();
    }
}
