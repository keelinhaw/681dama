package com.Dama;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 * Servlet implementation class ReturnToGame
 */
@WebServlet("/ReturnToGame")
public class ReturnToGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
        static int max_length = 14;
        private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ReturnToGame.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnToGame() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		LoadGame game = new LoadGame();
		String gameid = request.getParameter("gameid");
                
                
            try {
                String cleaned_gameid = ESAPI.validator().getValidInput("SignupPage_GameIdField",gameid,
                        "GameID", // regex spec
                        max_length, // max lengyh
                        false, // no nulls
                        true); // canonicalize
                
                Game gameBean = game.getGame(Long.parseLong(gameid));
		session.setAttribute("gameBean", gameBean);
                response.sendRedirect("./NewGame.jsp");
                
            } catch (ValidationException ex) {
                Logger.getLogger(ReturnToGame.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("./failure.html");
            } catch (IntrusionException ex) {
                Logger.getLogger(ReturnToGame.class.getName()).log(Level.SEVERE, null, ex);

                log.error("Encounter Intrusion Attempt in NewUser : " + ex );
                //if valid user, then lock the user's account. If user loggedin invalidate session
                session.invalidate();
                response.sendRedirect("./failure.html");
            }
                                  
                        
	}

}
