package servlets;

import factory.ServiceFactory;
import model.OrderListBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/ShowOrderServlet")
public class ShowOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private int pageNow = 1;
    private int pageCount;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowOrderServlet() {
        super();
    }

    public void init() {
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

        HttpSession session = request.getSession(false);
        System.out.println(session.getAttribute("login") + " request");

        String loginValue = String.valueOf(session.getAttribute("login"));
        System.out.println(loginValue + " session");

        request.setAttribute("login", loginValue);
        getOrderList(request, response);

        PrintWriter out = response.getWriter();
        if (pageNow != 1) {
            out.println("<a href='" + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow="
                    + (pageNow - 1)) + "'>last page</a>");
        }

        if (pageNow != pageCount) {
            out.println("<a href='" + response.encodeURL(request.getContextPath() + "/ShowOrderServlet?pageNow="
                    + (pageNow + 1)) + "'>next page</a>");
        }

        out.println("&nbsp;&nbsp;current page: " + pageNow + "/total page: " + pageCount + "</br>");
    }

    private void getOrderList(HttpServletRequest request, HttpServletResponse response) {

        int pageSize = 5;
        String temp_pageNow = request.getParameter("pageNow");
        if (temp_pageNow != null) {
            pageNow = Integer.parseInt(temp_pageNow);
        }

        int rowCount = ServiceFactory.getOrderService().findTotalOrder(String.valueOf(request.getAttribute("login")));
        pageCount = (rowCount - 1) / pageSize + 1;

        List list = ServiceFactory.getOrderService().findOrder(String.valueOf(request.getAttribute("login")),
                pageSize * pageNow - pageSize);

        OrderListBean orderListBean = new OrderListBean();
        orderListBean.setOrderList(list);
        HttpSession session = request.getSession(false);
        session.setAttribute("orderList", orderListBean);
        ServletContext context = getServletContext();
        try {
            context.getRequestDispatcher("/order/listOrder.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
