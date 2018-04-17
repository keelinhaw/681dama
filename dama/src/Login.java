//todo//input validation on the username

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Randomizer;
import org.owasp.esapi.User;
import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.errors.AuthenticationException;
import org.owasp.esapi.errors.ValidationException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
        static Logger log = Logger.getLogger(Login.class);
        static Connection con = null;
        static int password_max_length = 16;
        static int username_max_length = 20;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        

        


//        RequestDispatcher dispatcher;

        try {
            
//todo//                            ESAPI.httpUtilities().assertSecureRequest(request);


                        String cleaned_username = ESAPI.validator().getValidInput("LoginPage_UsernameField",username,
                                  "Username", // regex spec
                                  username_max_length, // max lengyh
                                  false, // no nulls
                                  true); // canonicalize
 //todo//                           
 /*                           String cleaned_password = ESAPI.validator().getValidInput("LoginPage_PasswordField",password,
                                  "ComplexPassword", // regex spec
                                  password_max_length, // max lengyh
                                  false, // no nulls
                                                                      true); // canonicalize            
 */           
 
//        HttpSession session = request.getSession(true); 
        ESAPI.httpUtilities().setCurrentHTTP(request, response);
 /*                       User user = ESAPI.authenticator().login(request, response);
                        user.addSession(session);
*/                        
			if(Validate.checkUser(cleaned_username, password)) {


                        HttpSession session = ESAPI.httpUtilities().changeSessionIdentifier(request);
//                        HttpSession session = request.getSession(true);
		        session.setAttribute("username", username);
                        
                        //Opponent player = new Opponent();
		        //player.newOpponent(username);
                        
//				response.sendRedirect("/681dama/landing.jsp");
                                ESAPI.httpUtilities().sendRedirect(response, "/681dama/landing.jsp");
//                                ESAPI.httpUtilities().sendForward(request, response, "WEB-INF/landing.jsp");
//                                dispatcher = request.getRequestDispatcher("./landing.jsp");
//				dispatcher.forward(request, response);
			}
			else {
				response.sendRedirect("./failure.html");
//				dispatcher = request.getRequestDispatcher("WEB-INF/failure.html");
//				dispatcher.forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
                    log.debug("Login Error" + e);
		}
                catch (ValidationException | AuthenticationException e){
                    log.debug("Login Error " + e);
                    response.sendRedirect("./failure.html");
 //                   dispatcher = request.getRequestDispatcher("WEB-INF/failure.html");
 //                   dispatcher.forward(request, response);
                }
                catch ( AccessControlException e ){
                    log.debug("Login Error " + e);
                    response.sendRedirect("./failure.html");
                }

        
		doGet(request, response);
	}

}
