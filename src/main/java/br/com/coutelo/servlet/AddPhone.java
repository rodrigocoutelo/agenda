package br.com.coutelo.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.coutelo.controller.UserController;
import br.com.coutelo.controller.UserControllerImpl;
import br.com.coutelo.model.User;

/**
 * Servlet implementation class AddPhone
 */
@WebServlet(urlPatterns = { "/AddPhone" })
public class AddPhone extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserController aController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddPhone() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		if (aController == null) {
			aController = new UserControllerImpl();
		}

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
		Long userId = Long.valueOf(request.getParameter("userId"));
		int ddd = Integer.valueOf(request.getParameter("ddd"));
		int number = Integer.valueOf(request.getParameter("number"));
		String phoneType = (String) request.getParameter("phoneType");
		User userLogged = (User) session.getAttribute("user");
		
			if (userLogged == null ) {
				response.sendRedirect("App");
			} else {
				
			try {
				aController.addUserPhone(userId, ddd, number, phoneType);
				response.sendRedirect("Dashboard");
				
			} catch (Exception e) {
				errorMsg = e.getMessage();
				session.setAttribute("errorMsg", errorMsg);
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
		Long userId = Long.valueOf(request.getParameter("userId"));
		int ddd = Integer.valueOf(request.getParameter("ddd"));
		int number = Integer.valueOf(request.getParameter("number"));
		String phoneType = (String) request.getParameter("phoneType");
		User userLogged = (User) session.getAttribute("user");
		
		if (userLogged == null ) {
			response.sendRedirect("App");
		} else {
			
			try {
				aController.removeUserPhone(userId, ddd, number, phoneType);
			//	response.sendRedirect("Dashboard");
				
			} catch (Exception e) {
				errorMsg = e.getMessage();
				session.setAttribute("errorMsg", errorMsg);
			//	response.sendRedirect("Dashboard");
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPut(request, response);
		
		HttpSession session = request.getSession();
		String errorMsg = "";
		Long userId = Long.valueOf(request.getParameter("userId"));
		int ddd = Integer.valueOf(request.getParameter("ddd"));
		int number = Integer.valueOf(request.getParameter("number"));
		String phoneType = (String) request.getParameter("phoneType");
		
		int oldDDD = Integer.valueOf(request.getParameter("oldDDD"));
		int oldNumber = Integer.valueOf(request.getParameter("oldNumber"));
		String oldPhoneType = (String) request.getParameter("oldPhoneType");
		
		
		User userLogged = (User) session.getAttribute("user");
		
		if (userLogged == null ) {
			response.sendRedirect("App");
		} else {
			
			try {
				aController.removeUserPhone(userId, oldDDD, oldNumber, oldPhoneType);
				aController.addUserPhone(userId, ddd, number, phoneType);
			//	response.sendRedirect("Dashboard");
				
			} catch (Exception e) {
				errorMsg = e.getMessage();
				session.setAttribute("errorMsg", errorMsg);
			//	response.sendRedirect("Dashboard");
			}
		}
		
	}
	
	
	
	

}
