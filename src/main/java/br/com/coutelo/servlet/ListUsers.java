package br.com.coutelo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.coutelo.controller.UserController;
import br.com.coutelo.controller.UserControllerImpl;
import br.com.coutelo.model.User;

/**
 * Servlet implementation class ListUsers
 */
@WebServlet(urlPatterns ={"/ListUsers"})
public class ListUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserController aController;   
   
	public ListUsers() {
        super();
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
		
		try {
			List<User> users = aController.findAllUsers();
			
			request.setAttribute("users", users);
        	
        	RequestDispatcher rd = request.getRequestDispatcher("views/users.jsp");  
            rd.include(request, response);  
			
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("App");  
            rd.forward(request, response);  
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			List<User> users = aController.findAllUsers();
			
			request.setAttribute("users", users);
        	
        	RequestDispatcher rd = request.getRequestDispatcher("views/users.jsp");  
            rd.include(request, response);  
			
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("App");  
            rd.forward(request, response);  
		}
		
	}

}
