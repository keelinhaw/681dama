package com.Dama;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
                String cleaned_gameid; // canonicalize
                    cleaned_gameid = ESAPI.validator().getValidInput("ReturnToGameInput_fromLandingPage_GameIdField",gameid,
                            "GameID", // regex spec
                            max_length, // max lengyh
                            false, // no nulls
                            true);
                
                Game gameBean = game.getGame(Long.parseLong(cleaned_gameid));
		session.setAttribute("gameBean", gameBean);
                response.sendRedirect("./NewGame.jsp");
                
            } catch (ValidationException ex) {
                Logger.getLogger(ReturnToGame.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("./failure.html");
            } catch (IntrusionException ex) {
                Logger.getLogger(ReturnToGame.class.getName()).log(Level.SEVERE, null, ex);
//                Potential Intrusion Attempt
                //if valid user, then lock the user's account. If user loggedin invalidate session
                session.invalidate();
                response.sendRedirect("./failure.html");
            }
                                                         
	}

}
