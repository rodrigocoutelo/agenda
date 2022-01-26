package controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.UserController;
import controller.UserControllerImpl;
import models.User;

/**
 * Servlet implementation class AddPhone
 */
@WebServlet(urlPatterns = { "/AddPhone" })
public class AddPhone extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserController controller;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPhone() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
			controller = new UserControllerImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String errorMsg = "";
		User userLogged = (User) session.getAttribute("user");
		
			if (userLogged == null ) {
				response.sendRedirect("App");
			} else {
			try {
				Integer userId = Integer.valueOf(request.getParameter("userId"));
				int ddd = Integer.valueOf(request.getParameter("ddd"));
				int number = Integer.valueOf(request.getParameter("number"));
				String phoneType = (String) request.getParameter("phoneType");
				controller.addUserPhone(userId, ddd, number, phoneType);
				response.sendRedirect("Dashboard");
				
			} catch (Exception e) {
				errorMsg = e.getLocalizedMessage() + e.getMessage();
				request.setAttribute("errorMsg", errorMsg);
				response.sendRedirect("Dashboard");
			}
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(request, response);
		
		HttpSession session = request.getSession();
		String errorMsg = "";
		Integer phoneId = Integer.valueOf(request.getParameter("phoneId"));
		User userLogged = (User) session.getAttribute("user");
		
		if (userLogged == null ) {
			response.sendRedirect("App");
		} else {
			
			try {
				controller.removeUserPhone(phoneId);
				
			} catch (Exception e) {
				errorMsg = e.getMessage();
				session.setAttribute("errorMsg", errorMsg);
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPut(request, response);
		
		HttpSession session = request.getSession();
		String errorMsg = "";
		Integer userId = Integer.valueOf(request.getParameter("userId"));
		Integer phoneId = Integer.valueOf(request.getParameter("phoneId"));
		int ddd = Integer.valueOf(request.getParameter("ddd"));
		int number = Integer.valueOf(request.getParameter("number"));
		String phoneType = (String) request.getParameter("phoneType");
		
		User userLogged = (User) session.getAttribute("user");
		
		if (userLogged == null ) {
			response.sendRedirect("App");
		} else {
			
			try {
				controller.removeUserPhone(phoneId);
				controller.addUserPhone(userId, ddd, number, phoneType);
				
			} catch (Exception e) {
				errorMsg = e.getMessage();
				session.setAttribute("errorMsg", errorMsg);
			}
		}
		
	}
	
	
	
	

}
