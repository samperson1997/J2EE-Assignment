package daoImpl;

import dao.UserDao;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public String getPassword(String id) {
        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();

        String hql = "from model.User as us where us.userId = " + id;
        Query query = session.createQuery(hql);
        List<User> users = query.list();

        tx.commit();

        return users.get(0).getPassword();
    }
}
