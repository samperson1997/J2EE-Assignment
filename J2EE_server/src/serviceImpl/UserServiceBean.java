package serviceImpl;

import dao.UserDao;
import factory.DaoFactory;
import service.UserService;

import javax.ejb.Stateless;

@Stateless
public class UserServiceBean implements UserService {

    private UserDao userDao = DaoFactory.getUserDao();

    @Override
    public String getPassword(String id) {
        return userDao.getPassword(id);
    }
}
