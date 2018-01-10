package daoImpl;

import dao.UserDao;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao = new UserDaoImpl();
    private HibernateUtil hibernateUtil;

    public UserDaoImpl() {
        hibernateUtil = new HibernateUtil();
    }

    public static UserDaoImpl getInstance() {
        return userDao;
    }

    @Override
    public String getPassword(String id) {
        Session session = hibernateUtil.getSession();

        Transaction tx = session.beginTransaction();

        String hql = "from model.User as us where us.userId = " + id;
        Query query = session.createQuery(hql);
        List<User> users = query.list();

        tx.commit();

        return users.get(0).getPassword();
    }
}
