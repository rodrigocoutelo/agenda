package controller.servlets;

import java.io.IOException;

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
 * Servlet implementation class SignUp
 */
@WebServlet(urlPatterns = {"/SignUp"}) 
public class SignUp extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserController controller;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
    		controller = new UserControllerImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		String errorMsg = "";
		String userId = request.getParameter("userId");
		if (userId != null && userId !="") {
			try {
				user = controller.findUserById(Integer.valueOf(userId));
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
		String userId = request.getParameter("userId");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
        String password = request.getParameter("passW");
        String passwordCheck = request.getParameter("passWCheck");
        String role = "U";
        
        User user = null;
        try {
        	if (userId == null || userId.equals("null")) {
        		user = controller.create( firstname, lastname, email, password, passwordCheck, role);
        	} else {
        		user = controller.update(Integer.valueOf(userId),firstname, lastname, email, password, passwordCheck, role);
        	}
        	session.setAttribute("userId", user.getId());
        	session.setAttribute("logger", true);
        	request.setAttribute("user", user);
        	session.setAttribute("user", user);
        	response.sendRedirect("Dashboard");
            
		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			request.setAttribute("user", user);
			RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");  
	        rd.include(request, response);  
		}	
	}

}
