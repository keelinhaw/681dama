//TODO list added by Chadia
//done// should present user-friendly message when password1 and password2 are not equal
//done// should present user-friendly message when username already exist in DB (should not wait for SQL exception)
//done// should add logging entry for user created
//done// should remove hard-coded DB credentials from this file and from Validate.java. Instead read these creds from a server config file.
// should add input validation (whitelist w/ regular expressions) for the email
//done// should not be storing passwords in clear form. Instead store only salted hash.
// should encrypt connections to the DB (use TLS encryption)
// question: should one email be allowed to register multiple usernames?
// consider: validating the exitance of the email (ie. send confirmation link to prove ownership of the email being registered)
// consider enforcing a password length and complexity for the user's password
// consider adding captcha for preventing bots from abusing this form (least important task for now)

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 * Servlet implementation class NewUser
 */
@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
        static Logger log = Logger.getLogger(NewUser.class);
        static Connection con = null;
        static int password_max_length = 16;
        static int email_max_length = 42;
        static int username_max_length = 20;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
                

        
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String confirmationPassword = request.getParameter("password2");
 
        

	        try {
                    
                            String cleaned_email = ESAPI.validator().getValidInput("SignupPage_EmailField",email,
                                  "Email", // regex spec
                                  email_max_length, // max lengyh
                                  false, // no nulls
                                  true); // canonicalize
        
                            String cleaned_username = ESAPI.validator().getValidInput("SignupPage_UsernameField",username,
                                  "Username", // regex spec
                                  username_max_length, // max lengyh
                                  false, // no nulls
                                  true); // canonicalize
                            
                            String cleaned_password1 = ESAPI.validator().getValidInput("SignupPage_PasswordField",password1,
                                  "ComplexPassword", // regex spec
                                  password_max_length, // max lengyh
                                  false, // no nulls
                                                                      true); // canonicalize


                    
                    if (cleaned_password1.equals(confirmationPassword)) {


               
                        con = ConnectionManager.getConnection();
/*                                SecureRandom salt = new SecureRandom();
                                byte bytes[] = new byte[20];
                                salt.nextBytes(bytes);
*/

                        String computed_hash = PasswordUtil.hashPassword(cleaned_password1);
                                
		    	String checkUser = "SELECT username from dama_user where username=?";
                        PreparedStatement ck = con.prepareStatement(checkUser);
                        ck.clearParameters();
                        ck.setString(1, cleaned_username);
                         log.debug("Executing query to check if user already exist"); 
                        ResultSet foundUser = ck.executeQuery();
                        boolean exists = foundUser.next();
                        
                        if(!exists){

                                
		    		String query = "INSERT INTO dama_user VALUES (?,?,?)";

		    		PreparedStatement ps = con.prepareStatement(query);
		    		ps.clearParameters();
                                ps.setString(1, cleaned_username);
		    		ps.setString(2, cleaned_email);
		    		ps.setString(3, computed_hash);
		    		ps.executeUpdate();

                                log.debug("Created new User");
                                con.close();
                                response.sendRedirect("./success.html");
                        }
                        else{
                                con.close();
                                response.sendRedirect("./UserExists.html");
                            
                        }
                        
             }
        else {
        		response.sendRedirect("./ConfirmPassword.html");
        }           
                                
	    		} 

	        catch (SQLException e) {
                                log.error("Encounter SQL Exception in NewUser : " + e );
                                response.sendRedirect("./failure.html");
	        }
                catch (IntrusionException e) {
                                log.error("Encounter Intrusion Attempt in NewUser : " + e );
                                //if valid user, then lock the user's account
                                
                                try {
                                    String valid_username = ESAPI.validator().getValidInput("SignupPage_UsernameField",username,
                                  "Username", // regex spec
                                  username_max_length, // max lengyh
                                  false, // no nulls
                                  true); // canonicalize
                                    
                                    String locked_user = ESAPI.encoder().encodeForXML(valid_username);
                                    
 //todo//                                   // lock the user account
                                log.error("Deactivating user account due to Intrusion Exception : " + locked_user );
                        
                                } catch (IntrusionException | ValidationException ex) {
                                    //no need for further action
                                }
                                
                                response.sendRedirect("./failure.html");
	        }
                catch (ValidationException e){
                    log.debug("Validation Error in NewUser" + e);
                    response.sendRedirect("./incorrectInput.html");
                }

        
	}
}
