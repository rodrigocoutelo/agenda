package br.com.coutelo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class Dashboard
 */
@WebServlet(urlPatterns = {"/Dashboard"})
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserController aController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
    	if (aController == null) {
    		aController = new UserControllerImpl();
    	}
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String errorMsg = "";
		User userLogged = (User) session.getAttribute("user");
		String initial = request.getParameter("initial");
		if (initial == "" || initial == null) {
			initial = "A";
		}
		
		if (userLogged == null ) {
			response.sendRedirect("App");
		}
		
		try {
			List<User> usersList = aController.findAllUsers(initial.charAt(0));
			request.setAttribute("usersList", usersList);
		} catch (Exception e) {
			errorMsg = e.getMessage();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("views/dashboard.jsp");  
        rd.include(request, response);  
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
