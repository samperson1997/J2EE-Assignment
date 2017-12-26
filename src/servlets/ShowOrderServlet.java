package servlets;

import dao.OrderDao;
import daoImpl.OrderDaoImpl;
import model.Order;
import service.OrderService;
import serviceImpl.OrderServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/ShowOrderServlet")
public class ShowOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource datasource = null;
    private int pageNow = 1;
    private int pageCount;
    private OrderService orderService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrderServlet() {
        super();
    }

    public void init() {
        orderService = new OrderServiceImpl();

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

        int pageSize = 5;
        String temp_pageNow = request.getParameter("pageNow");
        if (temp_pageNow != null) {
            pageNow = Integer.parseInt(temp_pageNow);
        }

        int rowCount = orderService.findTotalOrder(String.valueOf(request.getAttribute("login")));
        pageCount = (rowCount - 1) / pageSize + 1;

        List<Order> list = orderService.findOrder(String.valueOf(request.getAttribute("login")),
                pageSize * pageNow - pageSize);

        request.setAttribute("list", list);

    }

    private void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
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

        ServletContext Context = getServletContext();

        int visitorNum = (Integer) Context.getAttribute("VisitorCount");
        int loginNum = (Integer) Context.getAttribute("OnlineCount");
        int totalNum = visitorNum + loginNum;
        out.println("当前在线总人数: " + totalNum + "人, 已登录人数: " + loginNum + "人, 游客人数: " +
                visitorNum + "人");
    }

}
