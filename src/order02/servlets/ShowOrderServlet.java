package order02.servlets;

import order02.model.Order;

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

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(true);
        boolean cookieFound = false;
        System.out.println(req.getParameter("login") + " req");
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            // Look through all the cookies and see if the
            // cookie with the login info is there.
            for (Cookie cookie1 : cookies) {
                if (cookie1.getName().equals("LoginCookie")) {
                    cookieFound = true;
                    break;
                }
            }
        }

        if (session == null) {
            String loginValue = req.getParameter("login");
            boolean isLoginAction = (null != loginValue);

            System.out.println(loginValue + " session null");
            if (isLoginAction) { // User is logging in
                if (cookieFound) { // If the cookie exists update the value only
                    // if changed
                    if (!loginValue.equals(cookie.getValue())) {
                        cookie.setValue(loginValue);
                        resp.addCookie(cookie);
                    }
                } else {
                    // If the cookie does not exist, create it and set value
                    cookie = new Cookie("LoginCookie", loginValue);
                    cookie.setMaxAge(Integer.MAX_VALUE);
                    System.out.println("Add cookie");
                    resp.addCookie(cookie);
                }

                // create a session to show that we are logged in
                session = req.getSession(false);
                session.setAttribute("login", loginValue);

                req.setAttribute("login", loginValue);
                getStockList(req, resp);
                displayMyStocklistPage(req, resp);
                displayLogoutPage(req, resp);

            } else {
//                System.out.println(loginValue + " session null");
                // Display the login page. If the cookie exists, set login
                resp.sendRedirect(req.getContextPath() + "/Login");
            }
        } else {
            String loginValue = (String) session.getAttribute("login");
            System.out.println(loginValue + " session");

            req.setAttribute("login", loginValue);
            getStockList(req, resp);
            displayMyStocklistPage(req, resp);
            displayLogoutPage(req, resp);

        }

    }

    private void getStockList(HttpServletRequest req, HttpServletResponse res) {

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
        String temp_pageNow = req.getParameter("pageNow");
        if (temp_pageNow != null) {
            pageNow = Integer.parseInt(temp_pageNow);
        }

        try {
            connection = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            prestmt = connection.prepareStatement("SELECT * FROM user_orders WHERE user_id = 1");
//            prestmt.setString(1, req.getParameter("login"));
            preresult = prestmt.executeQuery();
            while (preresult.next()) {
                rowCount++;//得到rowCount，总共多少条记录
                // 得到总共有多少页：总共有rowCount条记录，每一页显示pageSize条记录
            }
            pageCount = (rowCount - 1) / pageSize + 1;

            stmt = connection.prepareStatement("SELECT * FROM user_orders WHERE user_id = 1 LIMIT ?, 5");
//            stmt.setString(1, req.getParameter("login"));

            stmt.setInt(1, pageSize * pageNow - pageSize);

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
                System.out.println(">>>>>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("list", list);

    }

    private void displayLogoutPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        out.println("<form method='GET' action='" + res.encodeURL(req.getContextPath() + "/Login") + "'>");
        out.println("</p>");
        out.println("<input type='submit' name='Logout' value='Logout'>");
        out.println("</form>");
        out.println("</body></html>");

    }

    private void displayMyStocklistPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
        List list = (ArrayList) req.getAttribute("list");

        PrintWriter out = res.getWriter();
        out.println("<html><body>");
        out.println("<img src='" + req.getContextPath() + "/image/top.jpg' style=\"width: 30%; float: left;" +
                "margin: 2% 5%\">");

        out.println("<p>Welcome, <i>Customer " + req.getParameter("login") + "</i> !</p>");
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

        // 显示上一页，注意href的写法
        if (pageNow != 1) {
            out.println("<a title='last page' href='/ShowOrderServlet?pageNow="
                    + (pageNow - 1) + "'>last page</a>");
        }

        // 显示下一页
        if (pageNow != pageCount) {
            out.println("<a title='next page' href='/ShowOrderServlet?pageNow="
                    + (pageNow + 1) + "'>next page</a>");
        }
        // 显示分页信息
        out.println("&nbsp;&nbsp;&nbsp;current page: " + pageNow + "/total page: " + pageCount);
    }

}
