<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="javax.servlet.http.HttpSession,com.Dama.Game,com.Dama.LoadGame,java.sql.Connection,java.sql.DriverManager,java.sql.PreparedStatement,java.sql.ResultSet,com.Dama.GetPropertyValues" %>
    
<%
	Game gameBean2 = (Game) session.getAttribute("gameBean");
	Long id = gameBean2.getGameid();
	//database connection details
	GetPropertyValues properties = new GetPropertyValues();
	String dburl = properties.getPropValues("dburl");
	String dbuser = properties.getPropValues("dbuser");
	String dbpassword = properties.getPropValues("dbpassword");
  	Connection con = DriverManager.getConnection (dburl,dbuser,dbpassword);

	String findmygames = "SELECT timestamp, player, oldlocale, newlocale FROM game_moves WHERE id=?";
	PreparedStatement ps = con.prepareStatement(findmygames);
	ps.setLong(1, id);
	ResultSet gamemoves = ps.executeQuery();
	//Print table header
	out.println("<table><tr><th>Timestamp</th><th>Player</th><th>Old Locale</th><th>New Locale</th></tr>");
	while (gamemoves.next()) {
		out.println("<tr>");
		out.println("<td>" + gamemoves.getString("timestamp") + "</td>");
		out.println("<td>" + gamemoves.getString("player") + "</td>");
		out.println("<td>" + gamemoves.getString("oldlocale") + "</td>");
		out.println("<td>" + gamemoves.getString("newlocale") + "</td>");
		out.println("</tr>");
	}
	out.println("<table>");
	con.close();
%>