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
    private int pageCount;// 总共的页数，该变量是计算出来的

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

        if (session == null) {
            String loginValue = String.valueOf(session.getAttribute("login"));
            boolean isLoginAction = (null != loginValue);

            System.out.println(loginValue + " session null");
            if (!isLoginAction) { // User is logging in
                response.sendRedirect(request.getContextPath() + "/Login");
            }
        } else {
            String loginValue = String.valueOf(session.getAttribute("login"));
            System.out.println(loginValue + " session");

            request.setAttribute("login", loginValue);
            getOrderList(request, response);
            displayOrderPage(request, response);
            displayLogoutPage(request, response);

        }

    }

    private void getOrderList(HttpServletRequest request, HttpServletResponse response) {

        Connection connection = null;
        PreparedStatement prestmt;
        ResultSet preresult;
        PreparedStatement stmt;
        ResultSet result;
        List<Order> list = new ArrayList<>();

        // 三个变量先初始化
        int pageSize = 5;// 指定每一页显示5条记录
        int rowCount = 1;// 总共有多少条记录，该变量需要读取数据库得到
        // 定义第四个变量，即当前要显示的页数，初始化为1

        // 这个当前页是用户决定的，所以由用户请求参数来确定
        // 这个参数是下文中<a>链接中的href传递过来的
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
                rowCount++;//得到rowCount，总共多少条记录
                // 得到总共有多少页：总共有rowCount条记录，每一页显示pageSize条记录
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

        // 显示上一页
        if (pageNow != 1) {
            out.println("<a href='" + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow="
                    + (pageNow - 1)) + "'>last page</a>");
        }

        // 显示下一页
        if (pageNow != pageCount) {
            out.println("<a href='" + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow="
                    + (pageNow + 1)) + "'>next page</a>");
        }
        // 显示分页信息
        out.println("&nbsp;&nbsp;current page: " + pageNow + "/total page: " + pageCount);
    }

}
