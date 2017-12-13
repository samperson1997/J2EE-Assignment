package order02.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register.user")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();		
		RequestDispatcher dispatcher
		 =request.getRequestDispatcher("/user/register.html"); 
		if (dispatcher!= null) 
			dispatcher.include(request,response);
			/* dispatcher.forward(request,response);
		 */
		//response.sendRedirect(request.getContextPath() + "/user/register.html");	


		HttpSession session = request.getSession(true);
		pw.println("<h1>SessionServlet Output</h1>");
		Integer ival = (Integer) session.getAttribute("sessiontest.counter");
		if (ival == null)
			ival = new Integer(1);
		else
			ival = new Integer(ival.intValue() + 1);
		session.setAttribute("sessiontest.counter", ival);
		pw.println("You have hit this page <b>" + ival + "</b> times.<p>");
		pw.println("Click <a href=" + response.encodeURL("/onlineStockWeb01/register.user") + ">here</a>");
		pw.println(" to ensure that session tracking is working even if cookies aren't supported. ");
		pw.println("<h3>Session Data:</h3>");
		pw.println("Session ID in Request: " + request.getRequestedSessionId());
		pw.println("<br>Session ID in Request from Cookie: " + request.isRequestedSessionIdFromCookie());
		pw.println("<br>Session ID in Request from URL: " + request.isRequestedSessionIdFromURL());
		pw.println("New Session: " + session.isNew());
		pw.println("<br>Session ID: " + session.getId());
		pw.println("<br>Creation Time: " + session.getCreationTime());
		pw.println("<br>Last Accessed Time: " + session.getLastAccessedTime());

		pw.println("</body></html>");
		


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("userid");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		if (id == null)
			id = "unknown";
		if (name == null)
			name = "unknown";
		if (phone == null)
			phone = "unknown";
		if (email == null)
			email = "unknown";
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<title>register user</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<table width='650' border='0' >");
		pw.println("<tr>");
		pw.println(
				"<td width='650' height='80' background='" + request.getContextPath() + "/image/top.jpg'>&nbsp;</td>");
		pw.println("</tr>");
		pw.println("</table>");
		pw.println("user with Userid " + id + " name " + name + " Phone " + phone + " Email " + email + " was added.");
		pw.println("</p>");

		HttpSession session = request.getSession(true);
		pw.println("<h1>SessionServlet Output</h1>");
		Integer ival = (Integer) session.getAttribute("sessiontest.counter");
		if (ival == null)
			ival = new Integer(1);
		else
			ival = new Integer(ival.intValue() + 1);
		session.setAttribute("sessiontest.counter", ival);
		pw.println("You have hit this page <b>" + ival + "</b> times.<p>");
		pw.println("Click <a href=" + response.encodeURL("/onlineStockWeb01/register.user") + ">here</a>");
		pw.println(" to ensure that session tracking is working even if cookies aren't supported. ");
		pw.println("<h3>Session Data:</h3>");
		pw.println("Session ID in Request: " + request.getRequestedSessionId());
		pw.println("<br>Session ID in Request from Cookie: " + request.isRequestedSessionIdFromCookie());
		pw.println("<br>Session ID in Request from URL: " + request.isRequestedSessionIdFromURL());
		pw.println("New Session: " + session.isNew());
		pw.println("<br>Session ID: " + session.getId());
		pw.println("<br>Creation Time: " + session.getCreationTime());
		pw.println("<br>Last Accessed Time: " + session.getLastAccessedTime());

		pw.println("</body></html>");
	}

}
