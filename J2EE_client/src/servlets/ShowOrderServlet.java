package servlets;

import factory.EJBFactory;
import model.OrderListBean;
import service.OrderService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class OrderListServlet
 */
@WebServlet("/ShowOrderServlet")
public class ShowOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrderService orderService = (OrderService) EJBFactory.getEJB("OrderServiceBean", "service.OrderService");

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

        if (session == null || session.getAttribute("login") == null) {
            ServletContext context = getServletContext();
            try {
                context.getRequestDispatcher("/Login").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(session.getAttribute("login") + " request");
            String loginValue = String.valueOf(session.getAttribute("login"));
            System.out.println(loginValue + " session");
            request.setAttribute("login", loginValue);
            getOrderList(request, response);
        }
    }

    private void getOrderList(HttpServletRequest request, HttpServletResponse response) {

        int pageNow = 1;
        int pageCount;
        int pageSize = 5;
        String temp_pageNow = request.getParameter("pageNow");
        if (temp_pageNow != null) {
            pageNow = Integer.parseInt(temp_pageNow);
        }
        int rowCount = orderService.findTotalOrder(String.valueOf(request.getAttribute("login")));
        pageCount = (rowCount - 1) / pageSize + 1;

        List list = orderService.findOrder(String.valueOf(request.getAttribute("login")),
                pageSize * pageNow - pageSize, pageSize, rowCount);

        OrderListBean orderListBean = new OrderListBean();
        orderListBean.setOrderList(list);
        HttpSession session = request.getSession(false);
        session.setAttribute("orderList", orderListBean);
        session.setAttribute("pageNow", pageNow);
        session.setAttribute("pageCount", pageCount);

        ServletContext context = getServletContext();
        try {
            context.getRequestDispatcher("/order/listOrder.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
