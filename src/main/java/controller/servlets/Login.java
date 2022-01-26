package controller.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns ={"/Login"})
public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserController controller;
       
    public Login() {
        super();
        
       
    }
    
    public void init() throws ServletException {
    		controller = new UserControllerImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
        String password = request.getParameter("passW");
        
        
        try {
        	User user = controller.login(email, password);
        	if (user !=null) {
        		session.setAttribute("logged", true);
        		session.setAttribute("user", user);
        		List<User> usersList = controller.findAllUsers();
        		request.setAttribute("usersList", usersList);
        		response.sendRedirect("Dashboard");
        	} else {
        		throw new Exception("Usuário não encontrato!");
        	}
        	
		//	response.sendRedirect("Dashboard");
            
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			response.sendRedirect("App");
//			RequestDispatcher rd = request.getRequestDispatcher("App");  
//            rd.forward(request, response);  
		}
        
        
        
	}

}
