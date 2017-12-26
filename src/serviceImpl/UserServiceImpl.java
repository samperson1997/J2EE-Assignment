package serviceImpl;

import factory.DaoFactory;
import service.UserService;

public class UserServiceImpl implements UserService {

    private static UserService userService = new UserServiceImpl();

    public static UserService getInstance() {
        return userService;
    }

    @Override
    public String getPassword(String id) {
        return DaoFactory.getUserDao().getPassword(id);
    }
}
