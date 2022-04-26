package br.com.coutelo.servlet;

import java.io.IOException;

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
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = {"/SignUp"}) 
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserController aController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
	//	HttpSession session = request.getSession();
		User user = null;
		String errorMsg = "";
		String userId = request.getParameter("userId");
		if (userId != null && userId !="") {
			try {
				user = aController.findUserById(Long.valueOf(userId));
			} catch (Exception e) {
				errorMsg = e.getMessage();
			}
		}
		request.setAttribute("errorMsg", errorMsg);
		request.setAttribute("user", user);
		RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");  
        rd.include(request, response);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
        String password = request.getParameter("passW");
        String passwordCheck = request.getParameter("passWCheck");
        String role = "USER";
        
        
        try {
        	aController.create(name, email, password, passwordCheck, role);
        	Long userId = aController.login(email, password);
        	User user = aController.findUserById(userId);
        	session.setAttribute("userId", userId);
        	session.setAttribute("logger", true);
        	request.setAttribute("user", user);
        	session.setAttribute("user", user);
        	
        	response.sendRedirect("Dashboard");
        //	RequestDispatcher rd = request.getRequestDispatcher("Dashboard");  
          //  rd.forward(request, response); 
            
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("app.jsp");  
            rd.include(request, response);  
		}	
	}

}
