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
 * Servlet implementation class Login
 */
@WebServlet(urlPatterns ={"/Login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserController aController;
       
    public Login() {
        super();
        
       
    }
    
    public void init() throws ServletException {
    	if (aController == null) {
    		aController = new UserControllerImpl();
    	}
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String email = request.getParameter("email");
        String password = request.getParameter("passW");
        
        
        try {
        	Long userId = aController.login(email, password);
        	User user = aController.findUserById(userId);
        	session.setAttribute("logged", true);
        	session.setAttribute("user", user);
        	List<User> usersList = aController.findAllUsers();
        	session.setAttribute("usersList", usersList);
        	
			response.sendRedirect("Dashboard");
//			RequestDispatcher rd = request.getRequestDispatcher("Dashboard");  
//            rd.forward(request, response);  
            
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("App");  
            rd.forward(request, response);  
		}
        
        
        
	}

}
