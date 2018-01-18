package daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateUtil {

    @Autowired
    protected SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.openSession();
    }


}