package com.Dama;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LoadGame {
	Game game;
	static Logger log = Logger.getLogger(LoadGame.class);

	public LoadGame() {
	
	}
	public Long newGame(String player) {
		
        String status = "new";
		long gameid = -1L;

        try {
        		//Create new Game entry in the database
	    		GetPropertyValues properties = new GetPropertyValues();
	    		String dburl = properties.getPropValues("dburl");
	    		String dbuser = properties.getPropValues("dbuser");
	    		String dbpassword = properties.getPropValues("dbpassword");
	    	    Class.forName("org.postgresql.Driver");
	    	    Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
	    		String newGame = "INSERT INTO games (player1, status) VALUES (?,?)";
	    		PreparedStatement ps = con.prepareStatement(newGame, Statement.RETURN_GENERATED_KEYS);
			ps.clearParameters();
			ps.setString(1, player);
			ps.setString(2, status);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
			    gameid = rs.getLong(1);
			}
			ps.clearParameters();
			String query = "INSERT INTO game_board (id,b1,b2,b3,b4,b5,b6,b7,b8,c1,c2,c3,c4,c5,c6,c7,c8,f1,f2,f3,f4,f5,f6,f7,f8,g1,g2,g3,g4,g5,g6,g7,g8) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//String query = "INSERT INTO piece (id, player, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	        ps = con.prepareStatement(query);
	        
	        //Create game_board
	    		ps.setLong(1, gameid);
	    		ps.setString(2, "O");
	    		ps.setString(3, "O");
	    		ps.setString(4, "O");
	    		ps.setString(5, "O");
	    		ps.setString(6, "O");
	    		ps.setString(7, "O");
	    		ps.setString(8, "O");
	    		ps.setString(9, "O");
	    		ps.setString(10, "O");
	    		ps.setString(11, "O");
	    		ps.setString(12, "O");
	    		ps.setString(13, "O");
	    		ps.setString(14, "O");
	    		ps.setString(15, "O");
	    		ps.setString(16, "O");
	    		ps.setString(17, "O");
	    		ps.setString(18, "X");
	    		ps.setString(19, "X");
	    		ps.setString(20, "X");
	    		ps.setString(21, "X");
	    		ps.setString(22, "X");
	    		ps.setString(23, "X");
	    		ps.setString(24, "X");
	    		ps.setString(25, "X");
	    		ps.setString(26, "X");
	    		ps.setString(27, "X");
	    		ps.setString(28, "X");
	    		ps.setString(29, "X");
	    		ps.setString(30, "X");
	    		ps.setString(31, "X");
	    		ps.setString(32, "X");
	    		ps.setString(33, "X");
	    		ps.addBatch();
	        int[] results = ps.executeBatch();
	        con.close();
        }
		catch (Exception e) {
			log.error("Exception in Game : " + e );
		}
        return gameid;
	}
	public Game getGame(Long gameid) {
		
		String player1 = "";
		String player2 = "";
		String playerturn = "";
		String errorString = "";
		String gamestatus = "";
		String win = "";
		String loss = "";
		Game gameBean = new Game();
		try {
	    		GetPropertyValues properties = new GetPropertyValues();
	    		String dburl = properties.getPropValues("dburl");
	    		String dbuser = properties.getPropValues("dbuser");
	    		String dbpassword = properties.getPropValues("dbpassword");
	    	    Class.forName("org.postgresql.Driver");
	    	    Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);
		    String query = "SELECT player1, player2, playerturn, error_message, status, win, loss FROM games WHERE id=?";
		    PreparedStatement ps = con.prepareStatement(query);
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setLong(1, gameid);
			ResultSet game = ps.executeQuery();
		    if (game.next()) {
		    		player1 = game.getString("player1");
		        player2 = game.getString("player2");
		        playerturn = game.getString("playerturn");
		        errorString = game.getString("error_message");
		        gamestatus = game.getString("status");
		        win = game.getString("win");
		        loss = game.getString("loss");
		    }
			gameBean.setGameid(gameid);
			gameBean.setPlayer1(player1);
			gameBean.setPlayer2(player2);
			gameBean.setPlayerturn(playerturn);
			gameBean.setErrorstring(errorString);
			gameBean.setGamestatus(gamestatus);
			gameBean.setWin(win);
			gameBean.setLoss(loss);
			gameBean.setA1(" ");
			gameBean.setA2(" ");
			gameBean.setA3(" ");
			gameBean.setA4(" ");
			gameBean.setA5(" ");
			gameBean.setA6(" ");
			gameBean.setA7(" ");
			gameBean.setA8(" ");
			gameBean.setB1(" ");
			gameBean.setB2(" ");
			gameBean.setB3(" ");
			gameBean.setB4(" ");
			gameBean.setB5(" ");
			gameBean.setB6(" ");
			gameBean.setB7(" ");
			gameBean.setB8(" ");
			gameBean.setC1(" ");
			gameBean.setC2(" ");
			gameBean.setC3(" ");
			gameBean.setC4(" ");
			gameBean.setC5(" ");
			gameBean.setC6(" ");
			gameBean.setC7(" ");
			gameBean.setC8(" ");
			gameBean.setD1(" ");
			gameBean.setD2(" ");
			gameBean.setD3(" ");
			gameBean.setD4(" ");
			gameBean.setD5(" ");
			gameBean.setD6(" ");
			gameBean.setD7(" ");
			gameBean.setD8(" ");
			gameBean.setE1(" ");
			gameBean.setE2(" ");
			gameBean.setE3(" ");
			gameBean.setE4(" ");
			gameBean.setE5(" ");
			gameBean.setE6(" ");
			gameBean.setE7(" ");
			gameBean.setE8(" ");
			gameBean.setF1(" ");
			gameBean.setF2(" ");
			gameBean.setF3(" ");
			gameBean.setF4(" ");
			gameBean.setF5(" ");
			gameBean.setF6(" ");
			gameBean.setF7(" ");
			gameBean.setF8(" ");
			gameBean.setG1(" ");
			gameBean.setG2(" ");
			gameBean.setG3(" ");
			gameBean.setG4(" ");
			gameBean.setG5(" ");
			gameBean.setG6(" ");
			gameBean.setG7(" ");
			gameBean.setG8(" ");
			gameBean.setH1(" ");
			gameBean.setH2(" ");
			gameBean.setH3(" ");
			gameBean.setH4(" ");
			gameBean.setH5(" ");
			gameBean.setH6(" ");
			gameBean.setH7(" ");
			gameBean.setH8(" ");
			
		    query = "SELECT * FROM game_board WHERE id=?";
			ps = con.prepareStatement(query);
			ps.clearParameters();
			ps.setLong(1, gameid);
			ResultSet board = ps.executeQuery();
		    if (board.next()) {
		    		//if the location is not empty
		    		if(board.getString("a1") != null) {
		    			//set the piece location
		    			Method setPiece  = Game.class.getDeclaredMethod("setA1", String.class);
		    			setPiece.invoke(gameBean, board.getString("a1"));
		    		}
		    		if(board.getString("a2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA2", String.class);
		    			setPiece.invoke(gameBean, board.getString("a2"));
		    		}
		    		if(board.getString("a3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA3", String.class);
		    			setPiece.invoke(gameBean, board.getString("a3"));
		    		}
		    		if(board.getString("a4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA4", String.class);
		    			setPiece.invoke(gameBean, board.getString("a4"));
		    		}
		    		if(board.getString("a5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA5", String.class);
		    			setPiece.invoke(gameBean, board.getString("a5"));
		    		}
		    		if(board.getString("a6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA6", String.class);
		    			setPiece.invoke(gameBean, board.getString("a6"));
		    		}
		    		if(board.getString("a7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA7", String.class);
		    			setPiece.invoke(gameBean, board.getString("a7"));
		    		}
		    		if(board.getString("a8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setA8", String.class);
		    			setPiece.invoke(gameBean, board.getString("a8"));
		    		}
		    		if(board.getString("b1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB1", String.class);
		    			setPiece.invoke(gameBean, board.getString("b1"));
		    		}
		    		if(board.getString("b2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB2", String.class);
		    			setPiece.invoke(gameBean, board.getString("b2"));
		    		}
		    		if(board.getString("b3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB3", String.class);
		    			setPiece.invoke(gameBean, board.getString("b3"));
		    		}
		    		if(board.getString("b4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB4", String.class);
		    			setPiece.invoke(gameBean, board.getString("b4"));
		    		}
		    		if(board.getString("b5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB5", String.class);
		    			setPiece.invoke(gameBean, board.getString("b5"));
		    		}
		    		if(board.getString("b6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB6", String.class);
		    			setPiece.invoke(gameBean, board.getString("b6"));
		    		}
		    		if(board.getString("b7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB7", String.class);
		    			setPiece.invoke(gameBean, board.getString("b7"));
		    		}
		    		if(board.getString("b8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setB8", String.class);
		    			setPiece.invoke(gameBean, board.getString("b8"));
		    		}
		    		if(board.getString("c1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC1", String.class);
		    			setPiece.invoke(gameBean, board.getString("c1"));
		    		}
		    		if(board.getString("c2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC2", String.class);
		    			setPiece.invoke(gameBean, board.getString("c2"));
		    		}
		    		if(board.getString("c3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC3", String.class);
		    			setPiece.invoke(gameBean, board.getString("c3"));
		    		}
		    		if(board.getString("c4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC4", String.class);
		    			setPiece.invoke(gameBean, board.getString("c4"));
		    		}
		    		if(board.getString("c5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC5", String.class);
		    			setPiece.invoke(gameBean, board.getString("c5"));
		    		}
		    		if(board.getString("c6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC6", String.class);
		    			setPiece.invoke(gameBean, board.getString("c6"));
		    		}
		    		if(board.getString("c7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC7", String.class);
		    			setPiece.invoke(gameBean, board.getString("c7"));
		    		}
		    		if(board.getString("c8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setC8", String.class);
		    			setPiece.invoke(gameBean, board.getString("c8"));
		    		}
		    		if(board.getString("d1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD1", String.class);
		    			setPiece.invoke(gameBean, board.getString("d1"));
		    		}
		    		if(board.getString("d2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD2", String.class);
		    			setPiece.invoke(gameBean, board.getString("d2"));
		    		}
		    		if(board.getString("d3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD3", String.class);
		    			setPiece.invoke(gameBean, board.getString("d3"));
		    		}
		    		if(board.getString("d4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD4", String.class);
		    			setPiece.invoke(gameBean, board.getString("d4"));
		    		}
		    		if(board.getString("d5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD5", String.class);
		    			setPiece.invoke(gameBean, board.getString("d5"));
		    		}
		    		if(board.getString("d6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD6", String.class);
		    			setPiece.invoke(gameBean, board.getString("d6"));
		    		}
		    		if(board.getString("d7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD7", String.class);
		    			setPiece.invoke(gameBean, board.getString("d7"));
		    		}
		    		if(board.getString("d8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setD8", String.class);
		    			setPiece.invoke(gameBean, board.getString("d8"));
		    		}
		    		if(board.getString("e1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE1", String.class);
		    			setPiece.invoke(gameBean, board.getString("e1"));
		    		}
		    		if(board.getString("e2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE2", String.class);
		    			setPiece.invoke(gameBean, board.getString("e2"));
		    		}
		    		if(board.getString("e3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE3", String.class);
		    			setPiece.invoke(gameBean, board.getString("e3"));
		    		}
		    		if(board.getString("e4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE4", String.class);
		    			setPiece.invoke(gameBean, board.getString("e4"));
		    		}
		    		if(board.getString("e5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE5", String.class);
		    			setPiece.invoke(gameBean, board.getString("e5"));
		    		}
		    		if(board.getString("e6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE6", String.class);
		    			setPiece.invoke(gameBean, board.getString("e6"));
		    		}
		    		if(board.getString("e7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE7", String.class);
		    			setPiece.invoke(gameBean, board.getString("e7"));
		    		}
		    		if(board.getString("e8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setE8", String.class);
		    			setPiece.invoke(gameBean, board.getString("e8"));
		    		}
		    		if(board.getString("f1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF1", String.class);
		    			setPiece.invoke(gameBean, board.getString("f1"));
		    		}
		    		if(board.getString("f2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF2", String.class);
		    			setPiece.invoke(gameBean, board.getString("f2"));
		    		}
		    		if(board.getString("f3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF3", String.class);
		    			setPiece.invoke(gameBean, board.getString("f3"));
		    		}
		    		if(board.getString("f4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF4", String.class);
		    			setPiece.invoke(gameBean, board.getString("f4"));
		    		}
		    		if(board.getString("f5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF5", String.class);
		    			setPiece.invoke(gameBean, board.getString("f5"));
		    		}
		    		if(board.getString("f6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF6", String.class);
		    			setPiece.invoke(gameBean, board.getString("f6"));
		    		}
		    		if(board.getString("f7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF7", String.class);
		    			setPiece.invoke(gameBean, board.getString("f7"));
		    		}
		    		if(board.getString("f8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setF8", String.class);
		    			setPiece.invoke(gameBean, board.getString("f8"));
		    		}
		    		if(board.getString("g1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG1", String.class);
		    			setPiece.invoke(gameBean, board.getString("g1"));
		    		}
		    		if(board.getString("g2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG2", String.class);
		    			setPiece.invoke(gameBean, board.getString("g2"));
		    		}
		    		if(board.getString("g3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG3", String.class);
		    			setPiece.invoke(gameBean, board.getString("g3"));
		    		}
		    		if(board.getString("g4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG4", String.class);
		    			setPiece.invoke(gameBean, board.getString("g4"));
		    		}
		    		if(board.getString("g5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG5", String.class);
		    			setPiece.invoke(gameBean, board.getString("g5"));
		    		}
		    		if(board.getString("g6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG6", String.class);
		    			setPiece.invoke(gameBean, board.getString("g6"));
		    		}
		    		if(board.getString("g7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG7", String.class);
		    			setPiece.invoke(gameBean, board.getString("g7"));
		    		}
		    		if(board.getString("g8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setG8", String.class);
		    			setPiece.invoke(gameBean, board.getString("g8"));
		    		}
		    		if(board.getString("h1") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH1", String.class);
		    			setPiece.invoke(gameBean, board.getString("h1"));
		    		}
		    		if(board.getString("h2") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH2", String.class);
		    			setPiece.invoke(gameBean, board.getString("h2"));
		    		}
		    		if(board.getString("h3") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH3", String.class);
		    			setPiece.invoke(gameBean, board.getString("h3"));
		    		}
		    		if(board.getString("h4") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH4", String.class);
		    			setPiece.invoke(gameBean, board.getString("h4"));
		    		}
		    		if(board.getString("h5") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH5", String.class);
		    			setPiece.invoke(gameBean, board.getString("h5"));
		    		}
		    		if(board.getString("h6") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH6", String.class);
		    			setPiece.invoke(gameBean, board.getString("h6"));
		    		}
		    		if(board.getString("h7") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH7", String.class);
		    			setPiece.invoke(gameBean, board.getString("h7"));
		    		}
		    		if(board.getString("h8") != null) {
		    			Method setPiece  = Game.class.getDeclaredMethod("setH8", String.class);
		    			setPiece.invoke(gameBean, board.getString("h8"));
		    		}
		    }
		    con.close();
		}
		catch (Exception e) {
			log.error("Exception in Game : " + e );
		}
		return gameBean;
	} 

}
