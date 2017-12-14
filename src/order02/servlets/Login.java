package order02.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String login = "";
        HttpSession session = request.getSession(false);

        // Logout action removes session, but the cookie remains
        if (null != request.getParameter("Logout")) {
            if (null != session) {
                session.invalidate();
            }
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

        int userId = Integer.parseInt(request.getParameter("login"));
//        request.getParameter("password")
        HttpSession session = request.getSession(false);
        if (null != session) {
            session.invalidate();
        }
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
