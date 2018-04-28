package com.Dama;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReturnToGame
 */
@WebServlet("/ReturnToGame")
public class ReturnToGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		Game gameBean = game.getGame(Long.parseLong(gameid));
		session.setAttribute("gameBean", gameBean);
        response.sendRedirect("./NewGame.jsp");                        
	}

}
