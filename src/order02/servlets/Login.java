package order02.servlets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    public void init() {
        InitialContext jndiContext;

        Properties properties = new Properties();
        properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
        properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        try {
            jndiContext = new InitialContext(properties);
            datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/ORDERS");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = "";
        HttpSession session = request.getSession(false);

        if (null != session) {
            session.invalidate();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        out.println(
                "<form method='POST'>");
        out.println(
                "login: <input type='text' name='login' value='" + login + "'>");
        out.println(
                "password: <input type='password' name='password' value=''>");
        out.println("<input type='submit' name='Submit' value='Submit'>");

        out.println("</form></body></html>");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // match user and password
        Connection connection;
        PreparedStatement stmt;
        ResultSet result;
        boolean isCorrectPassword = false;

        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }

        try {
            connection = datasource.getConnection();
            stmt = connection.prepareStatement("SELECT password FROM users WHERE user_id = ?");
            stmt.setString(1, String.valueOf(request.getParameter("login")));

            result = stmt.executeQuery();

            if (result.next() && result.getString(1).equals(request.getParameter("password"))) {
                System.out.println("correct password");
                isCorrectPassword = true;
            } else {
                System.out.println("wrong password");
                displayWrongPage(request, response);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isCorrectPassword) {
            int userId = Integer.parseInt(request.getParameter("login"));

            session = request.getSession(true);
            session.setAttribute("login", userId);
            Cookie cookie = new Cookie("LoginCookie", session.getId());
            cookie.setMaxAge(Integer.MAX_VALUE);
            System.out.println("Add cookie");
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath()
                    + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow=1"));
        }
    }

    private void displayWrongPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }

        out.println("<html><body><p>Wrong userid or password!</p>");
        out.println("<form method='GET' action='" + response.encodeURL(request.getContextPath() + "/Login") + "'>");
        out.println("</p>");
        out.println("<input type='submit' name='return' value='return'>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
