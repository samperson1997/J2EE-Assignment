package daoImpl;

import dao.DaoHelper;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class DaoHelperImpl implements DaoHelper {
    private static DaoHelperImpl baseDao = new DaoHelperImpl();

    private InitialContext jndiContext = null;
    private Connection connection = null;
    private DataSource datasource = null;

    private DaoHelperImpl() {
        Properties properties = new Properties();
        properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
        properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");

        try {
            jndiContext = new InitialContext(properties);
            datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/ORDERS");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        System.out.println("got context");
        System.out.println("About to get ds---DaoHelper");
    }

    public static DaoHelperImpl getBaseDaoInstance() {
        return baseDao;
    }

    public Connection getConnection() {

        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }

    /*
     * Connection
     */
    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * PreparedStatement
     */
    public void closePreparedStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * ResultSet
     */
    public void closeResult(ResultSet result) {
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
