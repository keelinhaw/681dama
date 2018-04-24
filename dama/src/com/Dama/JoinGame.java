package com.Dama;
import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class JoinGame
 */
@WebServlet("/JoinGame")
public class JoinGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	    		String newGame = "UPDATE games SET player1_score=?,player2=?,player2_score=?,playerturn=?,status=? WHERE id=?";
	    		PreparedStatement ps = con.prepareStatement(newGame);
	    		ps.clearParameters();
	    		ps.setInt(1, 0);
	    		ps.setString(2, player2);
	    		ps.setInt(3, 0);
	    		ps.setString(4, player2);
	    		ps.setString(5, status);
	    		ps.setLong(6, Long.parseLong(gameid));
	    		ps.executeUpdate();
		}
        catch (Exception e) {
    			e.printStackTrace();
        }
		LoadGame game = new LoadGame();
		Game gameBean = game.getGame(Long.parseLong(gameid));
		session.setAttribute("gameBean", gameBean);
        response.sendRedirect("./NewGame.jsp");                        
		//doGet(request, response);
	}

}
