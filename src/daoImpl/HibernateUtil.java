package daoImpl;

import model.Order;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    public HibernateUtil() {
    }

    private SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Order.class);
        // 编程配置映射，否则org.hibernate.MappingException: Unknown entity

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();

        return config.buildSessionFactory(serviceRegistry);
    }

    public Session getSession() {

        return getSessionFactory().getCurrentSession();
    }


}