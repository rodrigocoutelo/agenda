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
@WebServlet(urlPatterns = {"/Update"}) 
public class UpdateUser extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserController controller;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
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
	//	HttpSession session = request.getSession();
//		User user = null;
//		String errorMsg = "";
//		String userId = request.getParameter("userId");
//		if (userId != null && userId !="") {
//			try {
//				user = controller.findUserById(Integer.valueOf(userId));
//			} catch (Exception e) {
//				errorMsg = e.getMessage();
//			}
//		}
//		request.setAttribute("errorMsg", errorMsg);
//		request.setAttribute("user", user);
//		RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");  
//        rd.include(request, response);  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

}
