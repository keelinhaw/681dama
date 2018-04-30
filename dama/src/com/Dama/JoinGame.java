package com.Dama;
import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 * Servlet implementation class JoinGame
 */
@WebServlet("/JoinGame")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(JoinGame.class);
        static int max_length = 14;
        static Connection con = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinGame() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
                                log.debug("Attempt to access JoinGame page with GET ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String gameid = request.getParameter("gameid");
        String player2 = (String) session.getAttribute("username");
        String status = "started";       

		try {
	    		con = ConnectionManager.getConnection();
	        
	        
	        //update games table with player2
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    		String newGame = "UPDATE games SET player2=?,playerturn=?,last_move=?,status=? WHERE id=?";
	    		PreparedStatement ps;
                        ps = con.prepareStatement(newGame);
	    		ps.clearParameters();
	    		ps.setString(1, player2);
	    		ps.setString(2, player2);
	    		ps.setTimestamp(3, timestamp);
	    		ps.setString(4, status);
	    		ps.setLong(5, Long.parseLong(gameid));
	    		ps.executeUpdate();
		}
        catch ( NumberFormatException | SQLException e) {
                        java.util.logging.Logger.getLogger(JoinGame.class.getName()).log(Level.SEVERE, null, e);
                        response.sendRedirect("./failure.html");
        }
                
        try {
                String cleaned_gameid; // canonicalize
                    cleaned_gameid = ESAPI.validator().getValidInput("JoinGameInput_fromLandingPage_GameIdField",gameid,
                            "GameID", // regex spec
                            max_length, // max lengyh
                            false, // no nulls
                            true);
                
		LoadGame game = new LoadGame();
		Game gameBean = game.getGame(Long.parseLong(cleaned_gameid));
		session.setAttribute("gameBean", gameBean);
                response.sendRedirect("./NewGame.jsp");                        
		//doGet(request, response);
        } catch (ValidationException ex) {
                java.util.logging.Logger.getLogger(JoinGame.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("./failure.html");
        } catch (IntrusionException ex) {
                java.util.logging.Logger.getLogger(JoinGame.class.getName()).log(Level.SEVERE, null, ex);
//                Potential Intrusion Attempt
                //if valid user, then lock the user's account. If user loggedin invalidate session
                session.invalidate();
                response.sendRedirect("./failure.html");
        }
	
    }

}
