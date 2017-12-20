package order02.servlets;

import order02.model.Order;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/ShowOrderServlet")
public class ShowOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;
    private int pageNow = 1;
    private int pageCount;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrderServlet() {
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
            System.out.println("got context");
            System.out.println("About to get ds---ShowOrders");
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(true);
        System.out.println(session.getAttribute("login") + " request");

        String loginValue = String.valueOf(session.getAttribute("login"));
        System.out.println(loginValue + " session");

        request.setAttribute("login", loginValue);
        getOrderList(request, response);
        displayOrderPage(request, response);
        displayLogoutPage(request, response);
    }

    private void getOrderList(HttpServletRequest request, HttpServletResponse response) {

        Connection connection = null;
        PreparedStatement prestmt;
        ResultSet preresult;
        PreparedStatement stmt;
        ResultSet result;
        List<Order> list = new ArrayList<>();

        int pageSize = 5;
        int rowCount = 1;

        String temp_pageNow = request.getParameter("pageNow");
        if (temp_pageNow != null) {
            pageNow = Integer.parseInt(temp_pageNow);
        }

        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            prestmt = connection.prepareStatement("SELECT * FROM user_orders WHERE user_id = ?");
            prestmt.setString(1, String.valueOf(request.getAttribute("login")));
            preresult = prestmt.executeQuery();
            while (preresult.next()) {
                rowCount++;
            }
            pageCount = (rowCount - 1) / pageSize + 1;

            stmt = connection.prepareStatement("SELECT * FROM user_orders WHERE user_id = ? LIMIT ?, 5");
            stmt.setString(1, String.valueOf(request.getAttribute("login")));
            stmt.setInt(2, pageSize * pageNow - pageSize);

            result = stmt.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("order_id"));
                order.setDate(result.getDate("order_date"));
                order.setArticleName(result.getString("article_name"));
                order.setArticleNum(result.getDouble("article_num"));
                order.setPrice(result.getDouble("price"));
                order.setIsAvailable(result.getInt("is_available"));

                list.add(order);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("list", list);

    }

    private void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        out.println("<form method='GET' action='" + response.encodeURL(request.getContextPath() + "/Login") + "'>");
        out.println("</p>");
        out.println("<input type='submit' name='Logout' value='Logout'>");
        out.println("</form>");
        out.println("</body></html>");

    }

    private void displayOrderPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List list = (ArrayList) request.getAttribute("list");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<img src='" + request.getContextPath() + "/image/top.jpg' style=\"width: 30%; float: left;" +
                "margin: 2% 5%\">");

        out.println("<p>Welcome, <i>Customer " + String.valueOf(request.getAttribute("login")) + "</i> !</p>");
        out.println("<p><b>Order List: </b></p>");
        out.println("<table style=\"text-align: center\"><tr><th>DATE</th><th>ARTICLE</th>" +
                "<th>NUMBER</th><th>PRICE</th><th>STATE</th></tr>");

        boolean hasUnavailable = false;
        for (Order order : (List<Order>) list) {
            if (order.getIsAvailable() == 0) {
                hasUnavailable = true;
            }
            out.println("<tr><td>" + order.getDate() + "</td><td>");
            out.println(order.getArticleName() + "</td><td>");
            out.println(order.getArticleNum() + "</td><td>");
            out.println(order.getPrice() + "</td><td>");
            out.println((order.getIsAvailable() == 0 ? "<i style=\"color: red\">out of stock</i>" : "normal") + "</td></tr>");
        }

        if (hasUnavailable) {
            out.println("<i style=\"color: red\">ATTENTION: Some articles in your Order List are out of stock!</i>");
        }
        out.println("</table>");

        if (pageNow != 1) {
            out.println("<a href='" + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow="
                    + (pageNow - 1)) + "'>last page</a>");
        }

        if (pageNow != pageCount) {
            out.println("<a href='" + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow="
                    + (pageNow + 1)) + "'>next page</a>");
        }

        out.println("&nbsp;&nbsp;current page: " + pageNow + "/total page: " + pageCount + "</br>");
        out.println("当前在线总人数, 已登录人数, 游客人数");
    }

}
