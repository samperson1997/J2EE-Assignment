package daoImpl;

import dao.UserDao;
import model.User;

import javax.persistence.*;

public class UserDaoBean implements UserDao {

    @PersistenceUnit(name = "nju")
    private EntityManagerFactory factory;

    @PersistenceContext
    private EntityManager em;

    private static UserDaoBean userDao = new UserDaoBean();

    public UserDaoBean() {
        factory = Persistence.createEntityManagerFactory("nju");
        em = factory.createEntityManager();
    }

    public static UserDaoBean getInstance() {
        return userDao;
    }

    @Override
    public String getPassword(String userId) {

        try {
            User user = em.find(User.class, Integer.valueOf(userId));
            return user.getPassword();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
