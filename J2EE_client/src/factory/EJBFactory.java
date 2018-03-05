package factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class EJBFactory {
    public static Object getEJB(String beanName, String interfaceName) {
        Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            Context context = new InitialContext(jndiProperties);
            final String fullName = "ejb://J2EE_server_ejb exploded//" + beanName + "!" + interfaceName;
            System.out.println("EJB: " + fullName);

            return context.lookup(fullName);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
