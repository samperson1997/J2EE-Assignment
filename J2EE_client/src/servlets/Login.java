package servlets;

import factory.EJBFactory;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    public void init() {
        userService = (UserService) EJBFactory.getEJB("UserServiceBean", "service.UserService");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (null != session) {
            session.invalidate();
        }

        // 用户访问就建立session
        System.out.println("login do get");
        session = request.getSession(true);
        session.removeAttribute("login");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/order/login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // match user and password
        boolean isCorrectPassword = false;
        String correctPassword = userService.getPassword(String.valueOf(request.getParameter("login")));

        if (null != correctPassword && correctPassword.equals(request.getParameter("password"))) {
            System.out.println("correct password");
            isCorrectPassword = true;
        } else {
            System.out.println("wrong password");
            displayWrongPage(request, response);
        }

        if (isCorrectPassword) {
            int userId = Integer.parseInt(request.getParameter("login"));
            HttpSession session = request.getSession(false);
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
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/order/wrongPage.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}
