package com.Dama;
import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class JoinGame
 */
@WebServlet("/JoinGame")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(JoinGame.class);
       
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
	    		GetPropertyValues properties = new GetPropertyValues();
	    		String dburl = properties.getPropValues("dburl");
	    		String dbuser = properties.getPropValues("dbuser");
	    		String dbpassword = properties.getPropValues("dbpassword");
	        Class.forName("org.postgresql.Driver");
	        Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
	        
	        
	        //update games table with player2
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    		String newGame = "UPDATE games SET player2=?,playerturn=?,last_move=?,status=? WHERE id=?";
	    		PreparedStatement ps = con.prepareStatement(newGame);
	    		ps.clearParameters();
	    		ps.setString(1, player2);
	    		ps.setString(2, player2);
	    		ps.setTimestamp(3, timestamp);
	    		ps.setString(4, status);
	    		ps.setLong(5, Long.parseLong(gameid));
	    		ps.executeUpdate();
		}
        catch (Exception e) {
	        	log.error("Exception in JoinGame : " + e );
        }
		LoadGame game = new LoadGame();
		Game gameBean = game.getGame(Long.parseLong(gameid));
		session.setAttribute("gameBean", gameBean);
        response.sendRedirect("./NewGame.jsp");                        
		//doGet(request, response);
	}

}
